package cn.koha.modelica.nodes.expr;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;

@NodeInfo(shortName = "access")
public class AccessNode extends MoExprNode {
    private final TruffleString name;

    public AccessNode(TruffleString name) {
        this.name = name;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return null;
    }
}
