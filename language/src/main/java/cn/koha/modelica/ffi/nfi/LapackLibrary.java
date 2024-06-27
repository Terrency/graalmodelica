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
import java.util.Collections;

public final class LapackLibrary extends NFINativeAccess {
    private final TruffleObject lapack;
//    private final TruffleObject dgelsy;
    private final TruffleObject dgesv;
    public LapackLibrary(TruffleLanguage.Env env){
        super(env);
        Path p = Path.of("libs");
        TruffleObject blas = loadLibrary(Collections.singletonList(p), "libblas", false);
        TruffleObject gcc = loadLibrary(Collections.singletonList(p), "libgcc_s_seh_64-1", false);
        TruffleObject fortran = loadLibrary(Collections.singletonList(p), "libfortran_64-3", false);
        this.lapack = loadLibrary(Collections.singletonList(p), "liblapack", true);
//        this.dgelsy = lookupAndBindSymbol(lapack, "dgelsy_", NativeSignature.create(NativeType.POINTER));
        this.dgesv = lookupAndBindSymbol(lapack, "LAPACK_dgesv", NativeSignature.create(NativeType.INT, NativeType.INT, NativeType.INT, NativeType.POINTER, NativeType.INT, NativeType.POINTER, NativeType.POINTER, NativeType.INT, NativeType.INT));
    }
    public int dgelsv(int n, int nrhs, double[] a, int lda, int[] ipiv, double[] b, int ldb, int info) {
        try {
            Object ret = uncachedInterop.execute(dgesv, n, nrhs, a, lda, ipiv, b, ldb, info);
            if(uncachedInterop.fitsInInt(ret)) {
                return uncachedInterop.asInt(ret);
            } else return -1;
        } catch (UnsupportedTypeException | ArityException | UnsupportedMessageException e) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return -1;
        }
    }
}
