package cn.koha.modelica.nodes.expr;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.ArrayList;
import java.util.List;

@NodeInfo(shortName = "range")
@NodeChild("start")
@NodeChild("end")
public abstract class RangeNode extends MoExprNode {

    @Specialization
    protected VectorObject doInt(int start, int end) {
        List<Object> result = new ArrayList<>();
        for(int i=0; i<= end-start; i++){
            result.add(start + i);
        }
        return new VectorObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, result);
    }
}
