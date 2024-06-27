package cn.koha.modelica.nodes.stmts.loops;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import cn.koha.modelica.runtime.MoUndefined;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

import java.util.Collections;
import java.util.List;

@NodeInfo(shortName = "if", description = "Then node with a condional statement")
public class IfStmtNode extends MoStmtNode {
    @Node.Child
    private MoExprNode condition;
    @Node.Children
    private MoStmtNode[] thenStmts;
    @Node.Children
    private MoStmtNode[] elseStmts;

    public IfStmtNode(MoExprNode condition, List<MoStmtNode> thenStmts, List<MoStmtNode> elseStmts) {
        this.condition = condition;
        this.thenStmts = thenStmts.toArray(new MoStmtNode[]{});
        this.elseStmts = elseStmts.toArray(new MoStmtNode[]{});
    }
    public IfStmtNode(MoExprNode condition, List<MoStmtNode> thenStmts, MoStmtNode elseStmt) {
        this(condition, thenStmts, elseStmt == null? Collections.emptyList() : List.of(elseStmt));
    }

    @Override
    public Object executeStatement(VirtualFrame frame) {
        try {
            if (condition.executeBoolean(frame)) {
                for (MoStmtNode stmt : thenStmts) {
                    stmt.executeStatement(frame);
                }
            } else {
                for (MoStmtNode stmt : elseStmts) {
                    stmt.executeStatement(frame);
                }
            }
        } catch (UnexpectedResultException e) {
            throw new MoException("error", condition);
        }
        return MoUndefined.INSTANCE;

    }
}
