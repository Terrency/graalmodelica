package cn.koha.modelica.nodes.expr.arithmetic;

import cn.koha.modelica.nodes.expr.BinaryNode;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "sub")
public abstract class SubNode extends BinaryNode {
    @Specialization(rewriteOn = ArithmeticException.class)
    protected int doInt(int left, int right) {
        return Math.subtractExact(left, right);
    }

    @Specialization(replaces = "doInt")
    protected double doDouble(double left, double right) {
        return left - right;
    }
}
