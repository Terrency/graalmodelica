package cn.koha.modelica.nodes.stmts.blocks;

import cn.koha.modelica.nodes.stmts.MoStmtNode;
import cn.koha.modelica.runtime.MoNull;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;

import java.util.List;

public final class BlockStmtNode extends MoStmtNode {
    @Node.Children
    private final MoStmtNode[] stmts;
    public BlockStmtNode(List<MoStmtNode> stmts) {
        this.stmts = stmts.toArray(new MoStmtNode[]{});
    }
    @Override
    @ExplodeLoop
    public Object executeStatement(VirtualFrame frame) {
        int last = this.stmts.length - 1;
        for(int i=0; i< last; i++){
            this.stmts[i].executeStatement(frame);
        }
        return last < 0 ? MoNull.SINGLETON : this.stmts[last].executeStatement(frame);
    }
}
