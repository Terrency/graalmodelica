package cn.koha.modelica.nodes.expr.logical;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.CountingConditionProfile;

public abstract class ShortCircuitNode extends MoExprNode {
    @Child
    private MoExprNode left;
    @Child
    private MoExprNode right;

    private final CountingConditionProfile evaluateRightProfile = CountingConditionProfile.create();

    public ShortCircuitNode(MoExprNode left, MoExprNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return executeBoolean(frame);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame) {
        boolean leftValue;
        try {
            leftValue = left.executeBoolean(frame);
        } catch (UnexpectedResultException e) {
            throw MoException.typeError(this, e.getResult(), null);
        }
        boolean rightValue;
        try {
            if (evaluateRightProfile.profile(isEvaluateRight(leftValue))) {
                rightValue = right.executeBoolean(frame);
            } else {
                rightValue = false;
            }
        } catch(UnexpectedResultException e1) {
            throw MoException.typeError(this, e1.getResult(), null);
        }
        return execute(leftValue, rightValue);
    }
    protected abstract boolean isEvaluateRight(boolean leftValue);
    protected abstract boolean execute(boolean left, boolean right);
}
