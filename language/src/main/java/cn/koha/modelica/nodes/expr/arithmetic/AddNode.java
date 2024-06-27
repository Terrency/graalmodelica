package cn.koha.modelica.nodes.expr.arithmetic;

import cn.koha.modelica.nodes.expr.BinaryNode;
import cn.koha.modelica.nodes.util.MoToTruffleStringNode;
import cn.koha.modelica.runtime.MoStrings;
import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;

@NodeInfo(shortName = "+")
public abstract class AddNode extends BinaryNode {
    @Specialization(rewriteOn = ArithmeticException.class)
    public int doInt(int left, int right) {
        return Math.addExact(left, right);
    }

    @Specialization(replaces = "doInt")
    public double doDouble(double left, double right) {
        return left + right;
    }

    @Specialization
    public TruffleString doString(TruffleString left, TruffleString right,
                                  @Cached TruffleString.ConcatNode concatNode) {
        return MoStrings.concat(left, right, concatNode);
    }

    @Specialization(guards = "isString(left, right)")
    public TruffleString doString(Object left, Object right,
                                  @Bind("this") Node node,
                                  @Cached MoToTruffleStringNode toTruffleStringNodeLeft,
                                  @Cached MoToTruffleStringNode toTruffleStringNodeRight,
                                  @Cached TruffleString.ConcatNode concatNode) {
        return concatNode.execute(toTruffleStringNodeLeft.execute(node, left), toTruffleStringNodeRight.execute(node, right), MoStrings.STRING_ENCODING, true);
    }

    protected boolean isString(Object left, Object right) {
        return left instanceof TruffleString || right instanceof TruffleString;
    }
}
