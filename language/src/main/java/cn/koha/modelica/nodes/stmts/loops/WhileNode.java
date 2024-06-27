package cn.koha.modelica.nodes.stmts.loops;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "while", description = "The node implementing a for loop")
public class WhileNode extends MoStmtNode {
    @Child
    private LoopNode loopNode;

    public WhileNode(MoExprNode conditionNode, MoStmtNode bodyNode) {
        this.loopNode = Truffle.getRuntime().createLoopNode(new WhileRepeatingNode(conditionNode, bodyNode));
    }

    @Override
    public Object executeStatement(VirtualFrame frame) {
        return loopNode.execute(frame);
    }
}
