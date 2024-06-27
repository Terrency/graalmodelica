package cn.koha.modelica.ffi;

import java.util.Objects;

public final class NativeSignatureImpl implements NativeSignature{
    private final NativeType returnType;
    private final NativeType[] parameterTypes;

    public NativeSignatureImpl(NativeType returnType, NativeType[] parameterTypes) {
        this.returnType = Objects.requireNonNull(returnType);
        this.parameterTypes = Objects.requireNonNull(parameterTypes);
    }
    @Override
    public NativeType getReturnType() {
        return returnType;
    }

    @Override
    public int getParameterCount() {
        return parameterTypes.length;
    }

    @Override
    public int getVarArgsParameterCount() {
        return 0;
    }

    @Override
    public NativeType parameterTypeAt(int index) {
        if(index < parameterTypes.length) {
            return parameterTypes[index];
        }
        return null;
    }
}
