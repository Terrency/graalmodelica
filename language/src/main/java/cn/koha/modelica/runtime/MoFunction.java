package cn.koha.modelica.runtime;

import cn.koha.modelica.MoLanguage;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.strings.TruffleString;

@ExportLibrary(InteropLibrary.class)
public class MoFunction implements TruffleObject {
    final TruffleString name;
    RootCallTarget callTarget;
    public MoFunction(TruffleString name, RootCallTarget callTarget) {
        this.name = name;
        this.callTarget = callTarget;
    }
    @ExportMessage
    boolean hasLanguage() { return true; }
    @ExportMessage
    Class<? extends TruffleLanguage<?>> getLanguage() {
        return MoLanguage.class;
    }
    @ExportMessage
    Object toDisplayString(boolean allowSideEffects) { return name; }
}
