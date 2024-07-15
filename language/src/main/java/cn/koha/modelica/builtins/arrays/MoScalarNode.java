package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "scalar")
@NodeChild("array")
public abstract class MoScalarNode extends MoExprNode {
    @Specialization
    protected double scalar(double value) {
        return value;
    }
}
