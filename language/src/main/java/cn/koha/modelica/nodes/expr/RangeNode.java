package cn.koha.modelica.nodes.expr;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.ArrayObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "range")
@NodeChild("start")
@NodeChild("end")
public abstract class RangeNode extends MoExprNode {

    @Specialization
    protected VectorObject doInt(int start, int end) {
        Integer[] arrayElement = new Integer[end-start + 1];
        for(int i=0; i<= end-start; i++){
            arrayElement[i] = start + i;
        }
        return new VectorObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, arrayElement);
    }
}
