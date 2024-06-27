package cn.koha.modelica.nodes.expr;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = ":=")
@NodeChild("receive")
@NodeChild("name")
@NodeChild("value")
public abstract class WritePropertyNode extends MoExprNode {
}
