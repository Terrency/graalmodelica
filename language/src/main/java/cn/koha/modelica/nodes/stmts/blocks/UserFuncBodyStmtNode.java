package cn.koha.modelica.nodes.stmts.blocks;

import cn.koha.modelica.nodes.stmts.MoStmtNode;
import cn.koha.modelica.runtime.MoNull;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

import java.util.List;

public final class UserFuncBodyStmtNode extends MoStmtNode {
    @Node.Children
    private final MoStmtNode[] stmts;
    public UserFuncBodyStmtNode(List<MoStmtNode> stmts) {
        this.stmts = stmts.toArray(new MoStmtNode[]{});
    }

    @Override
    public Object executeStatement(VirtualFrame frame) {
        return MoNull.SINGLETON;
    }
}
