package cn.koha.modelica.nodes.expr.literals;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;

@NodeInfo(shortName = "strConst")
public class StringLiteralNode extends MoExprNode {
    private final TruffleString value;

    public StringLiteralNode(TruffleString value) {
        this.value = value;
    }

    @Override
    public TruffleString executeGeneric(VirtualFrame frame) {
        return value;
    }
}
