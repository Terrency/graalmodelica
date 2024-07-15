package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.MatrixObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "size")
@NodeChild(value = "array")
public abstract class MoSizeNode extends MoExprNode {
    @Specialization
    protected int size(int vectorObject) {
        return 1;
    }
    @Specialization
    protected int size(VectorObject vectorObject) {
        return vectorObject.getArraySize();
    }
    @Specialization
    protected int size(MatrixObject array) {
        return array.getArraySize();
    }
}
