package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.builtins.MoBuiltinNode;
import cn.koha.modelica.runtime.ArrayObject;
import cn.koha.modelica.runtime.MatrixObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "ndims")
public abstract class MoNdimsNode extends MoBuiltinNode {
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
