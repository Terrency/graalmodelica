package cn.koha.modelica.nodes.expr.comparisons;

import cn.koha.modelica.nodes.expr.BinaryNode;
import cn.koha.modelica.runtime.MoStrings;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;

@NodeInfo(shortName = "==")
public abstract class EqualityExprNode extends BinaryNode {
    @Specialization
    protected boolean doInt(int left, int right) {
        return left == right;
    }

    @Specialization(replaces = "doInt")
    protected boolean doDouble(double left, double right) {
        double result = left - right;
        double epsilon = 0.0001;
        return result > -epsilon && result < epsilon;
    }

    @Specialization
    protected boolean doBoolean(boolean left, boolean right) {
        return left == right;
    }

    @Specialization
    protected boolean doString(TruffleString left, TruffleString right,
                               @Cached TruffleString.EqualNode equalNode) {
        return MoStrings.equals(left, right, equalNode);
    }
    @Specialization
    protected boolean doObject(Object left, Object right) {
        return left == right;
    }
}
