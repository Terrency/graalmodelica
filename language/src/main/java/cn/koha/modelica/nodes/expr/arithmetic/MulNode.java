package cn.koha.modelica.nodes.expr.arithmetic;

import cn.koha.modelica.nodes.expr.BinaryNode;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "*")
public abstract class MulNode extends BinaryNode {
    @Specialization
    protected long doLong(long left, long right) {
        return Math.multiplyExact(left, right);
    }
}
