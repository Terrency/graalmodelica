package cn.koha.modelica.nodes.stmts.variables;

import cn.koha.modelica.MoLanguage;
import cn.koha.modelica.common.ShapesAndPrototypes;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.root.StmtBlockRootNode;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import cn.koha.modelica.nodes.stmts.blocks.UserFuncBodyStmtNode;
import cn.koha.modelica.runtime.MoUndefined;
import cn.koha.modelica.runtime.ModelicaFunctionObject;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;

@NodeChild(value = "containerObjectExpr", type = MoExprNode.class)
@NodeField(name = "funcName", type = String.class)
@NodeField(name = "frameDescriptor", type = FrameDescriptor.class)
@NodeField(name = "funcBody", type = UserFuncBodyStmtNode.class)
@NodeField(name = "argumentCount", type = int.class)
public abstract class FuncDeclStmtNode extends MoStmtNode {
    protected abstract String getFuncName();
    protected abstract FrameDescriptor getFrameDescriptor();
    protected abstract UserFuncBodyStmtNode getFuncBody();
    protected abstract int getArgumentCount();

    @CompilerDirectives.CompilationFinal
    private ModelicaFunctionObject cachedFunction;

    @Specialization(limit = "2")
    protected Object declareFunction(DynamicObject containerObject,
                                     @CachedLibrary("containerObject")DynamicObjectLibrary objectLibrary) {
        if(this.cachedFunction == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            MoLanguage truffleLanguage = this.currentLanguage();
            StmtBlockRootNode funcRootNode = new StmtBlockRootNode(truffleLanguage, this.getFrameDescriptor(), this.getFuncBody());
            CallTarget callTarget = funcRootNode.getCallTarget();
            ShapesAndPrototypes shapesAndPrototypes = this.currentLanguageContext().shapesAndPrototypes;
            this.cachedFunction = new ModelicaFunctionObject(shapesAndPrototypes.rootShape,
                    shapesAndPrototypes.functionPrototype, callTarget, this.getArgumentCount());
        }
        objectLibrary.putConstant(containerObject, this.getFuncName(), this.cachedFunction, 0);
        return MoUndefined.INSTANCE;
    }
}
