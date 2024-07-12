package cn.koha.modelica.nodes.expr.arithmetic;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.expr.BinaryNode;
import cn.koha.modelica.runtime.ArrayObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "*")
public abstract class MulNode extends BinaryNode {
    @Specialization
    protected int doInt(int left, int right) {
        return Math.multiplyExact(left, right);
    }
    @Specialization
    protected VectorObject doIntArray(int left, VectorObject right) {
        Integer[] result = new Integer[right.getArraySize()];
        for(int i=0 ;i< right.getArraySize(); i++) {
            result[i] = Math.multiplyExact(left, (int) right.readArrayElement(i));
        }
        return new VectorObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, result);
    }
    @Specialization
    protected VectorObject doArrayInt(VectorObject left, int right) {
        return doIntArray(right, left);
    }
    @Specialization
    protected int doArray(ArrayObject left, ArrayObject right) {
        if(left.getArraySize() != right.getArraySize()) {
            throw new MoException("Array not matched, left length is " + left.getArraySize() + ", right length is " + right.getArraySize());
        }
        int result = 0;
        for(int i=0 ;i< left.getArraySize(); i++) {
            result += Math.multiplyExact((int) left.readArrayElement(i), (int) right.readArrayElement(i));
        }
        return result;
    }
}
