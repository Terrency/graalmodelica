package cn.koha.modelica.nodes.expr;


import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.object.DynamicObject;

public abstract class GlobalScopeObjectExprNode extends MoExprNode {
    @Specialization
    protected DynamicObject returnGlobalScopeObject() {
//        return currentLanguageContext()
        return null;
    }
}
