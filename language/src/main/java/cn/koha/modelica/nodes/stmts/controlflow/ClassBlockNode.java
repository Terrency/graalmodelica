package cn.koha.modelica.nodes.stmts.controlflow;

import cn.koha.modelica.MoClassNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.Node;

public final class ClassBlockNode extends Node implements BlockNode.ElementExecutor<MoClassNode> {
    public ClassBlockNode(MoClassNode[] bodyNodes) {
        this.block = bodyNodes.length > 0 ? BlockNode.create(bodyNodes, this) : null;
    }

    @Child
    BlockNode<MoClassNode> block;

    @Override
    public void executeVoid(VirtualFrame frame, MoClassNode node, int index, int argument) {
    }
}
