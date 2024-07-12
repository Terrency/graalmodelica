package cn.koha.modelica.nodes.expr.variables;

import cn.koha.modelica.common.DeclarationKind;
import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.expr.GlobalScopeObjectExprNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;

@NodeChild(value = "globalScopeObjectExpr", type = GlobalScopeObjectExprNode.class)
@NodeChild(value = "assignmentExpr")
@NodeField(name = "name", type = String.class)
public abstract class GlobalVarAssignmentExprNode extends MoExprNode {
    protected abstract String getName();

    @Specialization(limit = "2")
    protected Object assignVariable(DynamicObject globalScopeObject, Object value,
                                    @CachedLibrary("globalScopeObject") DynamicObjectLibrary objectLibrary) {
        String name = this.getName();
        Property property = objectLibrary.getProperty(globalScopeObject, name);
        if (property == null) {
            objectLibrary.putWithFlags(globalScopeObject, name, value, 0);
//            throw new MoException("'" + name + "' is not defined", this);
        } else {
            if (property.getFlags() == 1) {
                // 常量不允许再赋值
                throw new MoException("Assignment to constant variable '" + name + "' is not allowed");
            }
            objectLibrary.put(globalScopeObject, name, value);
        }
        return value;
    }

}
