package cn.koha.modelica.ffi.nfi;

import cn.koha.modelica.ffi.NativeSignature;
import cn.koha.modelica.ffi.NativeType;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.*;

import java.nio.file.Path;

public class Kernel32Library extends NFINativeAccess{
    private final TruffleObject kerner32;
    private final TruffleObject getCurrentProcessId;
    public Kernel32Library(TruffleLanguage.Env env) {
        super(env);
        this.kerner32 = loadLibrary(Path.of("Kernel32"));
        this.getCurrentProcessId = lookupAndBindSymbol(kerner32, "GetCurrentProcessId", NativeSignature.create(NativeType.INT));
    }
    public int getCurrentProcessId() {
        try {
            Object pid = uncachedInterop.execute(getCurrentProcessId);
            if(uncachedInterop.fitsInInt(pid)) {
                return uncachedInterop.asInt(pid);
            } else return -1;
        } catch (UnsupportedTypeException | ArityException | UnsupportedMessageException e) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return 0;
        }
    }
}
