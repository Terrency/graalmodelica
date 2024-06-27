package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.builtins.MoBuiltinNode;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "scalar")
public abstract class MoScalarNode extends MoBuiltinNode {
    @Specialization
    protected double scalar(double value) {
        return value;
    }
}
