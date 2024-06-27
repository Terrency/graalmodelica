package cn.koha.modelica.ffi;

public interface NativeSignature {
    NativeType getReturnType();

    int getParameterCount();
    int getVarArgsParameterCount();
    NativeType parameterTypeAt(int index);

    static NativeSignature create(NativeType returnType, NativeType... parameterTypes) {
        return new NativeSignatureImpl(returnType, parameterTypes);
    }

}
