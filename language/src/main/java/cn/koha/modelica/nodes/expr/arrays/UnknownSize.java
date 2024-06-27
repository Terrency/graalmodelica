package cn.koha.modelica.nodes.expr.arrays;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class UnknownSize extends MoExprNode {
    private UnknownSize(){}
    public static UnknownSize INSTANCE = new UnknownSize();
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return null;
    }

    @Override
    public String toString() {
        return ":";
    }
}
