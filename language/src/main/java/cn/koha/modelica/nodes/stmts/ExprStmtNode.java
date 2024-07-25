package cn.koha.modelica.nodes.stmts;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.MoNull;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class ExprStmtNode extends MoStmtNode{
    @Child
    private MoExprNode expr;
    private boolean withStatement = true;
    public ExprStmtNode(MoExprNode expr) {
        this.expr = expr;
    }
    public ExprStmtNode(MoExprNode expr, boolean withStatement) {
        this(expr);
        this.withStatement = withStatement;
    }

    public void setWithStatement(boolean withStatement) {
        this.withStatement = withStatement;
    }

    @Override
    public Object executeStatement(VirtualFrame frame) {
        Object result = this.expr.executeGeneric(frame);
        if(this.withStatement) {
            return result;
        } else {
            return MoNull.SINGLETON;
        }
    }
}
