package cn.koha.modelica.ffi.nfi;

import cn.koha.modelica.ffi.NativeSignature;
import cn.koha.modelica.ffi.NativeType;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;

import java.nio.file.Path;

public class MSVCRTLibrary extends NFINativeAccess{
    private final TruffleObject msvcrt;
    private final TruffleObject calloc;
    public MSVCRTLibrary(TruffleLanguage.Env env) {
        super(env);
        Path p = Path.of("msvcrt");
        this.msvcrt = loadLibrary(p);
        this.calloc = lookupAndBindSymbol(msvcrt, "calloc", NativeSignature.create(NativeType.POINTER, NativeType.INT, NativeType.INT));
    }
    public TruffleObject calloc(long num, long size) {
        try {
            TruffleObject address = (TruffleObject) uncachedInterop.execute(calloc, num, size);
            return address;
        } catch (UnsupportedTypeException | ArityException | UnsupportedMessageException e) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return null;
        }
    }
}
