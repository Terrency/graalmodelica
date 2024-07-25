package cn.koha.modelica.runtime;

import com.oracle.truffle.api.interop.TruffleObject;

public final class MoNull implements TruffleObject {
    public static final MoNull SINGLETON = new MoNull();
    private MoNull(){}

    @Override
    public String toString() {
        return "";
    }
}
