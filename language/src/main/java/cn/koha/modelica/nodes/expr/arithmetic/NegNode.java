package cn.koha.modelica.nodes.expr.arithmetic;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "-")
@NodeChild("expr")
public abstract class NegNode extends MoExprNode {
    @Specialization
    protected int doInt(int value) {
        return Math.negateExact(value);
    }

    @Specialization
    protected double doDouble(double value) {
        return -value;
    }
}
