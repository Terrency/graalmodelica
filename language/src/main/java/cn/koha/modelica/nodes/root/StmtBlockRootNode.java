package cn.koha.modelica.nodes.root;

import cn.koha.modelica.MoLanguage;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import cn.koha.modelica.nodes.stmts.blocks.BlockStmtNode;
import cn.koha.modelica.nodes.stmts.blocks.UserFuncBodyStmtNode;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public final class StmtBlockRootNode extends RootNode {
    @Child
    private MoStmtNode blockStmt;

    public StmtBlockRootNode(MoLanguage lang,
                             FrameDescriptor frameDescriptor, BlockStmtNode blockStmt) {
        this(lang, frameDescriptor, (MoStmtNode) blockStmt);
    }
    public StmtBlockRootNode(MoLanguage lang,
                             FrameDescriptor frameDescriptor, UserFuncBodyStmtNode blockStmt) {
        this(lang, frameDescriptor, (MoStmtNode) blockStmt);
    }
    private StmtBlockRootNode(MoLanguage lang, FrameDescriptor frameDescriptor, MoStmtNode blockStmt) {
        super(lang, frameDescriptor);
        this.blockStmt = blockStmt;
    }
    @Override
    public Object execute(VirtualFrame frame) {
        return this.blockStmt.executeStatement(frame);
    }
}
