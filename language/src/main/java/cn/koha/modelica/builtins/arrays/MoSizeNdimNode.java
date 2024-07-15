package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.MatrixObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "size")
@NodeChild(value = "array")
@NodeChild(value = "ndim")
public abstract class MoSizeNdimNode extends MoExprNode {
    @Specialization
    protected int size(VectorObject vectorObject, int ndim) {
        if(ndim != 1) throw new MoException("Vector not support ndim " + ndim);
        return vectorObject.getArraySize();
    }
    @Specialization
    protected int size(MatrixObject matrixObject, int ndim) {
        if(ndim == 1) return matrixObject.getM();
        if(ndim == 2) return matrixObject.getN();
        throw new MoException("Vector not support ndim " + ndim);
    }
}
