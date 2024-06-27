package cn.koha.modelica.nodes.expr.literals;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "intConst")
public class IntegerLiteralNode extends MoExprNode {
    private final int value;

    public IntegerLiteralNode(int value) {
        this.value = value;
    }

    @Override
    public Integer executeGeneric(VirtualFrame frame) {
        return value;
    }
}
