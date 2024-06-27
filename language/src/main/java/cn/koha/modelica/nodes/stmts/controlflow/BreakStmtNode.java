package cn.koha.modelica.nodes.stmts.controlflow;

import cn.koha.modelica.exceptions.BreakException;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class BreakStmtNode extends MoStmtNode {
    @Override
    public Object executeStatement(VirtualFrame frame) {
        return BreakException.INSTANCE;
    }
}
