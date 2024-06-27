package cn.koha.modelica.nodes.stmts.variables;

import cn.koha.modelica.common.DeclarationKind;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.expr.GlobalScopeObjectExprNode;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import cn.koha.modelica.runtime.MoUndefined;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;

@NodeChild(value = "globalScopeObjectExpr", type = GlobalScopeObjectExprNode.class)
@NodeChild(value = "initializerExpr", type= MoExprNode.class)
@NodeField(name = "name", type = String.class)
@NodeField(name = "declarationKind", type = DeclarationKind.class)
public abstract class GlobalVarDeclStmtNode extends MoStmtNode {
    public abstract String getName();
    public abstract DeclarationKind getDeclarationKind();

    public enum ClassType {
        CLASS, MODEL, RECORD, OPERATOR_RECORD, BLOCK, CONNECTOR, EXPANDABLE_CONNECTOR,
        TYPE, PACKAGE, FUNCTION, OPERATOR_FUNCTION, OPERATOR
    }
    @Specialization(limit = "2")
    protected Object createVariable(DynamicObject globalScopeObject, Object value, @CachedLibrary("globalScopeObject")DynamicObjectLibrary objectLibrary) {
        String name = this.getName();
        objectLibrary.putWithFlags(globalScopeObject, name, value, 1);
        return MoUndefined.INSTANCE;
    }
}
