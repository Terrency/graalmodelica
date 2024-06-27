package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.builtins.MoBuiltinNode;
import cn.koha.modelica.runtime.ArrayObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "vector")
public abstract class MoVectorNode extends MoBuiltinNode {
    @Specialization
    protected VectorObject vector(ArrayObject array) {
        return null;
    }
}
