package cn.koha.modelica.runtime;

import cn.koha.modelica.MoLanguage;
import cn.koha.modelica.nodes.expr.functions.FunctionDispatchNode;
import cn.koha.modelica.nodes.expr.functions.FunctionDispatchNodeGen;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;

@ExportLibrary(InteropLibrary.class)
public final class ModelicaFunctionObject extends ModelicaObject {
    public final CallTarget callTarget;
    public final int argumentCount;
    private final FunctionDispatchNode functionDispatchNode;
    public ModelicaFunctionObject(Shape shape, ClassPrototypeObject functionPrototype, CallTarget callTarget, int argumentCount) {
        super(shape, functionPrototype);

        this.callTarget = callTarget;
        this.argumentCount = argumentCount;
        this.functionDispatchNode = FunctionDispatchNodeGen.create();
    }
    @ExportMessage
    boolean hasLanguage() { return true; }
    @ExportMessage
    boolean isExecutable() {
        return true;
    }
    @ExportMessage
    Class<? extends TruffleLanguage<?>> getLanguage() {
        return MoLanguage.class;
    }
    @ExportMessage
    Object toDisplayString(boolean allowSideEffects) { return "global"; }
    @ExportMessage
    Object execute(Object[] arguments) {
        // for GraalVM polyglot calls, we never fill the receiver
        return this.functionDispatchNode.executeDispatch(this, arguments, MoUndefined.INSTANCE);
    }
}
