package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.MatrixObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "scalar")
@NodeChild("array")
public abstract class MoScalarNode extends MoExprNode {
    @Specialization
    protected int scalar(int value) {
        return value;
    }
    @Specialization
    protected int scalar(VectorObject vectorObject) {
        if(vectorObject.getArraySize() != 1) {
            //do nothing
            throw new MoException("Vector size is not 1");
        }
        return (int) vectorObject.readArrayElement(0);
    }
    @Specialization
    protected int scalar(MatrixObject matrixObject) {
        if(matrixObject.getM() != 1 || matrixObject.getN() != 1) {
            //do nothing
            throw new MoException("Matrix each dims size is not 1");
        }
        return (int) matrixObject.readArrayElement(0);
    }
}
