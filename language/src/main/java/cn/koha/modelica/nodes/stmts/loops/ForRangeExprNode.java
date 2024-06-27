package cn.koha.modelica.nodes.stmts.loops;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class ForRangeExprNode extends MoExprNode {
    private String name;
    private MoExprNode rangeExpr;
    public ForRangeExprNode(String name, MoExprNode rangeExpr) {
        this.name = name;
        this.rangeExpr = rangeExpr;
    }
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        if(rangeExpr == null) {
            //找到name在frame中的遍历表达式
        }
        return null;
    }
}
