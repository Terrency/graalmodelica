package cn.koha.modelica.common;

public final class LocalVariableFrameSlotId {
    public final String variableName;
    public final int index;
    public LocalVariableFrameSlotId(String variableName, int index) {
        this.variableName = variableName;
        this.index = index;
    }

    @Override
    public String toString() {
        return this.variableName + "-" + this.index;
    }
}
