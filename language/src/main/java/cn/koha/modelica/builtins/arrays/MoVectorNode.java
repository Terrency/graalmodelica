package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.ArrayObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "vector")
@NodeChild("array")
public abstract class MoVectorNode extends MoExprNode {
    @Specialization
    protected VectorObject vector(ArrayObject array) {
        return null;
    }
}
