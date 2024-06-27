package cn.koha.modelica.nodes.stmts.controlflow;

import cn.koha.modelica.nodes.stmts.MoStmtNode;
import cn.koha.modelica.runtime.MoUndefined;
import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * condition：离散时间boolean标量与向量表达式
 */
public class WhenNode extends MoStmtNode {
    @Override
    public Object executeStatement(VirtualFrame frame) {
        return MoUndefined.INSTANCE;
    }
}
