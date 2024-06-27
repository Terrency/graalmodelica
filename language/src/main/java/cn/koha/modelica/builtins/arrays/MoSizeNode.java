package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.builtins.MoBuiltinNode;
import cn.koha.modelica.runtime.ArrayObject;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "size")
public abstract class MoSizeNode extends MoBuiltinNode {
    @Specialization
    protected int size(ArrayObject array) {
        return array.getArraySize();
    }
}
