package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.builtins.MoBuiltinNode;
import cn.koha.modelica.runtime.ArrayObject;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "ndims")
public abstract class MoNdimsNode extends MoBuiltinNode {
    @Specialization
    protected int[] ndims(ArrayObject array) {
        return new int[]{1};
    }
}
