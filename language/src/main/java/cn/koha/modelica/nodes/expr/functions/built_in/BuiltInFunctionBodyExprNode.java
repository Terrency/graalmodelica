package cn.koha.modelica.nodes.expr.functions.built_in;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.expr.functions.ReadFunctionArgExprNode;
import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild(value = "arguments", type = ReadFunctionArgExprNode[].class)
@GenerateNodeFactory
public abstract class BuiltInFunctionBodyExprNode extends MoExprNode {
}
