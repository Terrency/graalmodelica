package cn.koha.modelica.nodes.expr.arithmetic;

import cn.koha.modelica.nodes.expr.BinaryNode;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "^")
public abstract class PowNode extends BinaryNode {
    @Specialization
    protected double doInt(int left, int right) {
        return Math.pow(left, right);
    }

    @Specialization
    protected double doDouble(double left, double right) {
        return Math.pow(left, right);
    }
}
