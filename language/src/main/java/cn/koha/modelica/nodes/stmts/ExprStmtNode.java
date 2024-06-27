package cn.koha.modelica.nodes.stmts;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class ExprStmtNode extends MoStmtNode{
    @Child
    private MoExprNode expr;
    public ExprStmtNode(MoExprNode expr) {
        this.expr = expr;
    }
    @Override
    public Object executeStatement(VirtualFrame frame) {
        return this.expr.executeGeneric(frame);
    }
}
