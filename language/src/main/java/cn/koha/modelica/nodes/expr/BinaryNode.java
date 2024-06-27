package cn.koha.modelica.nodes.expr;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild("left")
@NodeChild("right")
public abstract class BinaryNode extends MoExprNode {
}
