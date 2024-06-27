package cn.koha.modelica.nodes.stmts.controlflow;

import cn.koha.modelica.exceptions.ReturnException;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class ReturnStmtNode extends MoStmtNode {
    @Override
    public Object executeStatement(VirtualFrame frame) {
        throw ReturnException.INSTANCE;
    }
}
