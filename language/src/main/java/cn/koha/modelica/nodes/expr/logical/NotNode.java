package cn.koha.modelica.nodes.expr.logical;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "!")
@NodeChild("value")
public abstract class NotNode extends MoExprNode {
    @Specialization
    public boolean doBoolean(boolean value) {
        return !value;
    }
}
