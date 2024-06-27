package cn.koha.modelica.nodes.stmts.loops;

import cn.koha.modelica.exceptions.BreakException;
import cn.koha.modelica.exceptions.ContinueException;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;

public class WhileRepeatingNode extends Node implements RepeatingNode {
    @Child
    private MoExprNode conditionNode;
    @Child
    private MoStmtNode bodyNode;
    private final BranchProfile continueTaken = BranchProfile.create();
    private final BranchProfile breakTaken = BranchProfile.create();

    public WhileRepeatingNode(MoExprNode conditionNode, MoStmtNode bodyNode) {
        this.conditionNode = conditionNode;
        this.bodyNode = bodyNode;
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
        if (!evaluateCondition(frame)) {
            return false;
        }
        try {
            bodyNode.executeStatement(frame);
            return true;
        } catch (ContinueException e) {
            continueTaken.enter();
            return true;
        } catch (BreakException e) {
            breakTaken.enter();
            return false;
        }
    }

    private boolean evaluateCondition(VirtualFrame frame) {
        try {
            return conditionNode.executeBoolean(frame);
        } catch (UnexpectedResultException e) {
            throw new UnsupportedSpecializationException(this, new Node[]{conditionNode}, e.getResult());
        }
    }
}
