package cn.koha.modelica.nodes.expr.arrays;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild("arrayExpr")
@NodeChild("indexExpr")
public abstract class ArrayIndexReadExprNode extends MoExprNode {
    public abstract MoExprNode getArrayExpr();
    public abstract MoExprNode getIndexExpr();

}
