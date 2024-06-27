package cn.koha.modelica.nodes.expr.literals;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.MoUndefined;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public final class UndefinedLiteralNode extends MoExprNode {
    @Override
    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        return false;
    }
    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        throw new UnexpectedResultException(MoUndefined.INSTANCE);
    }
    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        throw new UnexpectedResultException(MoUndefined.INSTANCE);
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return MoUndefined.INSTANCE;
    }
}
