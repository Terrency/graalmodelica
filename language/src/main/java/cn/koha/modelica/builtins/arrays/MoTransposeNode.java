package cn.koha.modelica.builtins.arrays;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.MatrixObject;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.ArrayList;
import java.util.List;

@NodeInfo(shortName = "transpose")
@NodeChild("target")
public abstract class MoTransposeNode extends MoExprNode {
    @Specialization
    public MatrixObject doMatrix(MatrixObject target) {
        int m = target.getM();
        int n = target.getN();
        List<Object> result = new ArrayList<>();
        for(int i=0; i< n; i++){
            for(int j=0; j<m; j++){
                result.add(target.readArrayElement(j*m + i));
            }
        }
        return new MatrixObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, m, n, result);
    }
}
