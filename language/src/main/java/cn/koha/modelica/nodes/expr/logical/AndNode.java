package cn.koha.modelica.nodes.expr.logical;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "&&")
public final class AndNode extends ShortCircuitNode {

    public AndNode(MoExprNode left, MoExprNode right) {
        super(left, right);
    }

    @Override
    protected boolean isEvaluateRight(boolean leftValue) {
        return leftValue;
    }

    @Override
    protected boolean execute(boolean left, boolean right) {
        return left && right;
    }
}
