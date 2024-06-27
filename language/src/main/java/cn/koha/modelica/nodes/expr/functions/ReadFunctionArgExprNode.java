package cn.koha.modelica.nodes.expr.functions;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class ReadFunctionArgExprNode extends MoExprNode {
    private final int index;
    public ReadFunctionArgExprNode(int index) {
        this.index = index;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return frame.getArguments()[this.index];
    }
}
