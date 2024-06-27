package cn.koha.modelica.builtins;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeChild(value = "arguments", type = MoExprNode[].class)
@GenerateNodeFactory
public abstract class MoBuiltinNode extends MoExprNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return execute(frame);
    }

    protected abstract Object execute(VirtualFrame frame);
}
