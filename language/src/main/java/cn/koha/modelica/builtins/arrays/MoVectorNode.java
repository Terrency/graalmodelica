package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.MatrixObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NodeInfo(shortName = "vector")
@NodeChild("array")
public abstract class MoVectorNode extends MoExprNode {
    @Specialization
    protected VectorObject vector(int a) {
        return new VectorObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape, this.currentLanguageContext().shapesAndPrototypes.objectPrototype, new ArrayList<>(a));
    }

    @Specialization
    protected VectorObject vector(VectorObject vectorObject) {
        return vectorObject;
    }

    @Specialization
    protected VectorObject vector(MatrixObject matrixObject) {
        int m = matrixObject.getM();
        int n = matrixObject.getN();
        if ((m == 1 && n > 1) || (m > 1 && n == 1)) {
            List<Object> element = new ArrayList<>(Arrays.stream(matrixObject.elements).toList());
            return new VectorObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape, this.currentLanguageContext().shapesAndPrototypes.objectPrototype, element);
        }
        throw new MoException("dims not math support");
    }
}
