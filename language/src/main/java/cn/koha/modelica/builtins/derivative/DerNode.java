package cn.koha.modelica.builtins.derivative;

import cn.koha.modelica.builtins.MoBuiltinNode;
import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "der")
public final class DerNode extends MoBuiltinNode {
    @Child
    public MoExprNode expr;
    public DerNode(MoExprNode expr) {
        this.expr = expr;
    }
    @Override
    protected Object execute(VirtualFrame frame) {
        return null;
    }
}
