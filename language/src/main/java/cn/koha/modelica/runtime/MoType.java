package cn.koha.modelica.runtime;

import cn.koha.modelica.MoLanguage;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
public class MoType implements TruffleObject {
    public static final MoType INTEGER_TYPE = new MoType("IntegerType", (l, v) -> l.fitsInInt(v));
    public static final MoType REAL_TYPE = new MoType("RealType", (l, v) -> l.fitsInDouble(v));
    public static final MoType BOOLEAN_TYPE = new MoType("BooleanType", (l, v) -> l.isBoolean(v));
    public static final MoType STRING_TYPE = new MoType("StringType", (l, v) -> l.isString(v));
    public static final MoType ENUM_TYPE = new MoType("EnumType", (l, v) -> l.isInstant(v));
    public static final MoType NULL = new MoType("NULL", (l, v) -> l.isNull(v));
    public static final MoType OBJECT = new MoType("Object", (l, v) -> l.hasMembers(v));

    @CompilerDirectives.CompilationFinal
    public static final MoType[] PRECEDENCE = new MoType[]{NULL, INTEGER_TYPE, REAL_TYPE, BOOLEAN_TYPE, STRING_TYPE, ENUM_TYPE, OBJECT};

    private final String name;
    private final TypeCheck isInstance;
    private MoType(String name, TypeCheck isInstance) {
        this.name = name;
        this.isInstance = isInstance;
    }
    public boolean isInstance(Object value, InteropLibrary interop) {
        CompilerAsserts.partialEvaluationConstant(this);
        return isInstance.check(interop, value);
    }
    @ExportMessage
    public boolean hasLanguage() {
        return true;
    }
    @ExportMessage
    public Class<? extends TruffleLanguage<?>> getLanguage() {
        return MoLanguage.class;
    }
    @ExportMessage
    public String toDisplayString(boolean allowSideEffects) {
        return name;
    }

    @Override
    public String toString() {
        return "MoType[" + name + "]";
    }

    @FunctionalInterface
    interface TypeCheck {
        boolean check(InteropLibrary lib, Object value);
    }
}
