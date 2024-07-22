package cn.koha.modelica.nodes.expr.arithmetic;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.expr.BinaryNode;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.ArrayList;
import java.util.List;

@NodeInfo(shortName = ".*")
public abstract class DotMulNode extends BinaryNode {
    @Specialization
    protected VectorObject doArray(VectorObject left, VectorObject right) {
        if(left.getArraySize() != right.getArraySize()) {
            throw new MoException("Array not matched, left length is " + left.getArraySize() + ", right length is " + right.getArraySize());
        }
        List<Object> result = new ArrayList<>();
        for(int i=0 ;i< left.getArraySize(); i++) {
            result.add(Math.multiplyExact((int) left.readArrayElement(i), (int) right.readArrayElement(i)));
        }
        return new VectorObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, result);
    }
}
