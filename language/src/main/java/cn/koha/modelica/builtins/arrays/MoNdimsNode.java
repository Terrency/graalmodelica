package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.MatrixObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "ndims")
@NodeChild("array")
public abstract class MoNdimsNode extends MoExprNode {
    @Specialization
    protected int ndims(int i) {
        return 0;
    }
    @Specialization
    protected int ndims(VectorObject vectorObject) {
        return 1;
    }
    @Specialization
    protected int ndims(MatrixObject matrixObject) {
        return 2;
    }
}
