package cn.koha.modelica.nodes.expr.variables;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.expr.GlobalScopeObjectExprNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;

@NodeChild(value = "globalScopeObjectExpr", type = GlobalScopeObjectExprNode.class)
@NodeField(name = "name", type = String.class)
public abstract class GlobalVarReferenceExprNode extends MoExprNode {
    protected abstract String getName();

    @Specialization(limit = "2")
    protected Object readVariable(DynamicObject globalScopeObject,
                                  @CachedLibrary("globalScopeObject")DynamicObjectLibrary objectLibrary) {
        String variableId = this.getName();
        Object value = objectLibrary.getOrDefault(globalScopeObject, variableId, null);
        if(value == null) {
            throw new MoException("is not defined", this);
        }
        return value;
    }
}
