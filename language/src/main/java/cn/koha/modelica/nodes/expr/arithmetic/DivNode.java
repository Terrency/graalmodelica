package cn.koha.modelica.nodes.expr.arithmetic;

import cn.koha.modelica.nodes.expr.BinaryNode;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "/")
public abstract class DivNode extends BinaryNode {
    @Specialization(rewriteOn = ArithmeticException.class)
    protected int doInt(int left, int right) {
        return Math.floorDiv(left, right);
    }

    @Specialization
    protected double doDouble(double left, double right) {
        return left / right;
    }
}
