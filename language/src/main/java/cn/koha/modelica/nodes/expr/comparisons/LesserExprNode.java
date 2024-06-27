package cn.koha.modelica.nodes.expr.comparisons;

import cn.koha.modelica.nodes.expr.BinaryNode;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;

@NodeInfo(shortName = "<")
public abstract class LesserExprNode extends BinaryNode {
    @Specialization
    protected boolean doInt(int left, int right) {
        return left < right;
    }
    @Specialization(replaces = "doInt")
    protected boolean doDouble(double left, double right) {
        return left < right;
    }
    @Specialization
    protected boolean doString(TruffleString left, TruffleString right,
                               @Cached TruffleString.CompareCharsUTF16Node compareNode) {
        return compareNode.execute(left, right) < 0;
    }
    @Specialization
    protected boolean doObject(Object left, Object right) {
        return false;
    }
}
