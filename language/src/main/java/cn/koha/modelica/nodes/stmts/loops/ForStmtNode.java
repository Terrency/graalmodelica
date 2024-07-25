package cn.koha.modelica.nodes.stmts.loops;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import cn.koha.modelica.runtime.MoNull;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;

public class ForStmtNode extends MoStmtNode {

    @Node.Child
    private LoopNode loopNode;

    public ForStmtNode(MoExprNode rangeNode, MoStmtNode bodyNode) {
        this.loopNode = Truffle.getRuntime().createLoopNode(new ForLoopRepeatingNode(rangeNode, bodyNode));
    }

    @Override
    public Object executeStatement(VirtualFrame frame) {
        this.loopNode.execute(frame);
        return MoNull.SINGLETON;
    }
}
