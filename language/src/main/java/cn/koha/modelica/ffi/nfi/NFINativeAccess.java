package cn.koha.modelica.ffi.nfi;

import cn.koha.modelica.ffi.NativeAccess;
import cn.koha.modelica.ffi.NativeSignature;
import cn.koha.modelica.ffi.NativeType;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.*;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.nfi.api.SignatureLibrary;

import java.nio.file.Files;
import java.nio.file.Path;

public class NFINativeAccess implements NativeAccess {
    private final TruffleLanguage.Env env;
    public NFINativeAccess(TruffleLanguage.Env env) {
        this.env = env;
    }
    protected final InteropLibrary uncachedInterop = InteropLibrary.getUncached();
    protected final SignatureLibrary uncachedSignature = SignatureLibrary.getUncached();

    @Override
    public TruffleObject loadLibrary(Path libPath) {
        if((libPath.isAbsolute() || libPath.getNameCount() > 1) && !Files.exists(libPath)) return null;
        String nfiSource = String.format("load '%s'", libPath);
        return loadLibraryHelper(nfiSource);
    }
    protected TruffleObject loadLibraryHelper(String nfiSource) {
        Source source = Source.newBuilder("nfi", nfiSource, "loadLibrary").build();
        CallTarget target = env.parseInternal(source);
        try {
            return (TruffleObject) target.call();
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public void unloadLibrary(TruffleObject lib) {
    }

    @Override
    public TruffleObject lookupSymbol(TruffleObject library, String symbolName) {
        try {
            TruffleObject symbol = (TruffleObject) uncachedInterop.readMember(library, symbolName);
            if (InteropLibrary.getUncached().isNull(symbol)) {
                return null;
            }
            return symbol;
        } catch (UnsupportedMessageException e) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return null; // throw EspressoError.shouldNotReachHere(e);
        } catch (UnknownIdentifierException e) {
            return null;
        }
    }

    @Override
    public TruffleObject bindSymbol(TruffleObject symbol, NativeSignature signature) {
        if (uncachedInterop.isNull(symbol)) {
            return null; // LD_DEBUG=unused makes non-existing symbols to be NULL.
        }
        TruffleObject executable = (TruffleObject) uncachedSignature.bind(createNFISignature(signature, true), symbol);
        assert uncachedInterop.isExecutable(executable);
        return new NativeToJavaWrapper(executable, signature);
    }
    @ExportLibrary(InteropLibrary.class)
    static final class NativeToJavaWrapper implements TruffleObject {

        final TruffleObject delegate;
        final NativeSignature nativeSignature;

        NativeToJavaWrapper(TruffleObject executable, NativeSignature nativeSignature) {
            this.delegate = executable;
            this.nativeSignature = nativeSignature;
        }

        @SuppressWarnings("static-method")
        @ExportMessage
        boolean isExecutable() {
            return true;
        }

        @CompilerDirectives.TruffleBoundary
        Object doExecuteBoundary(Object[] arguments, InteropLibrary delegateInterop) throws ArityException {
            return doExecute(arguments, delegateInterop);
        }

        @ExplodeLoop
        Object doExecute(Object[] arguments, InteropLibrary delegateInterop) throws ArityException {
            final int paramCount = nativeSignature.getParameterCount() + nativeSignature.getVarArgsParameterCount();
            CompilerAsserts.partialEvaluationConstant(paramCount);
            if (arguments.length != paramCount) {
                CompilerDirectives.transferToInterpreter();
                throw ArityException.create(paramCount, paramCount, arguments.length);
            }
            try {
                Object[] convertedArgs = new Object[arguments.length];
                for (int i = 0; i < paramCount; i++) {
                    NativeType param = nativeSignature.parameterTypeAt(i);
                    CompilerAsserts.partialEvaluationConstant(param);
                    switch (param) {
                        case BOOLEAN:
                            convertedArgs[i] = ((boolean) arguments[i]) ? (byte) 1 : (byte) 0;
                            break;
                        case CHAR:
                            convertedArgs[i] = (short) (char) arguments[i];
                            break;
                        default:
                            convertedArgs[i] = arguments[i];
                    }
                }
                Object ret = delegateInterop.execute(delegate, convertedArgs);
                CompilerAsserts.partialEvaluationConstant(nativeSignature.getReturnType());
                switch (nativeSignature.getReturnType()) {
                    case BOOLEAN:
                        ret = (byte) ret != 0;
                        break;
                    case CHAR:
                        ret = (char) (short) ret;
                        break;
                    case VOID:
                        ret = 0;//StaticObject.NULL;
                        break;
                }
                return ret;
            } catch (UnsupportedMessageException e) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return null;
//                throw EspressoError.shouldNotReachHere("This can be caused by mixing native/ffi symbols with llvm signatures and vice-versa", e);
            } catch (UnsupportedTypeException e) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return null;
//                throw EspressoError.shouldNotReachHere(e);
            } catch (AbstractTruffleException | StackOverflowError | OutOfMemoryError e) {
                throw e;
            } catch (Throwable t) {
//                logger.log(Level.FINE, "Exception seen", t);
                throw t;
            }
        }
        @ReportPolymorphism
        @ExportMessage
        abstract static class Execute {

            static final int INLINE_CACHE_SIZE = 2;

            @Specialization(limit = "INLINE_CACHE_SIZE", guards = "receiver == cachedReceiver")
            @SuppressWarnings("unused")
            protected static Object doCached(NativeToJavaWrapper receiver, Object[] arguments,
                                             @Cached(value = "receiver", weak = true) NativeToJavaWrapper cachedReceiver,
                                             @CachedLibrary("cachedReceiver.delegate") InteropLibrary delegateInterop) throws ArityException {
                return cachedReceiver.doExecute(arguments, delegateInterop);
            }

            @Specialization(replaces = "doCached")
            protected static Object doGeneric(NativeToJavaWrapper receiver, Object[] arguments,
                                              @CachedLibrary("receiver.delegate") InteropLibrary delegateInterop) throws ArityException {
                return receiver.doExecuteBoundary(arguments, delegateInterop);
            }
        }
    }
    protected Object createNFISignature(NativeSignature nativeSignature, boolean fromJava) {
        Source source = Source.newBuilder("nfi",
                nfiStringSignature(nativeSignature, fromJava), "signature").build();
        CallTarget target = env.parseInternal(source);
        return target.call();
    }
    protected String nfiStringSignature(NativeSignature nativeSignature, @SuppressWarnings("unused") boolean fromJava) {
        StringBuilder sb = new StringBuilder(64);
        sb.append('(');
        boolean isFirst = true;
        for (int i = 0; i < nativeSignature.getParameterCount() + nativeSignature.getVarArgsParameterCount(); ++i) {
            if (!isFirst) {
                sb.append(',');
            }
            if (i == nativeSignature.getParameterCount()) {
                sb.append("...");
            }
            sb.append(nfiType(nativeSignature.parameterTypeAt(i)));
            isFirst = false;
        }
        sb.append(')');
        sb.append(':');
        sb.append(nfiType(nativeSignature.getReturnType()));
        return sb.toString();
    }
    protected static String nfiType(NativeType nativeType) {
        // @formatter:off
        switch (nativeType) {
            case VOID:    return "VOID";
            case BOOLEAN: // fall-through
            case BYTE:    return "SINT8";
            case CHAR:    // fall-through
            case SHORT:   return "SINT16";
            case INT:     return "SINT32";
            case LONG:    return "SINT64";
            case FLOAT:   return "FLOAT";
            case DOUBLE:  return "DOUBLE";
            case OBJECT:  return "SINT64"; // word-sized handle
            case POINTER: return "POINTER";
            default:
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new RuntimeException("Unexpected: " + nativeType);// EspressoError.shouldNotReachHere("Unexpected: " + nativeType);
        }
        // @formatter:on
    }
}
