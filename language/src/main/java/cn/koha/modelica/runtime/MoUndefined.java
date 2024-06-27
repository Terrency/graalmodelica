package cn.koha.modelica.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
public final class MoUndefined implements TruffleObject {
    public static final MoUndefined INSTANCE = new MoUndefined();
    private MoUndefined(){}

    @ExportMessage
    boolean isNull() {
        return true;
    }
    @ExportMessage
    Object toDisplayString(boolean allowSideEffects) {
        return this.toString();
    }

    @Override
    public String toString() {
        return "undefined";
    }
}
