package cn.koha.modelica.nodes.expr.arithmetic;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.expr.BinaryNode;
import cn.koha.modelica.nodes.util.MoToTruffleStringNode;
import cn.koha.modelica.runtime.ArrayBaseObject;
import cn.koha.modelica.runtime.MatrixObject;
import cn.koha.modelica.runtime.MoStrings;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;

import java.util.ArrayList;
import java.util.List;

@NodeInfo(shortName = "+")
public abstract class AddNode extends BinaryNode {
    @Specialization(rewriteOn = ArithmeticException.class)
    public int doInt(int left, int right) {
        return Math.addExact(left, right);
    }

    @Specialization(replaces = "doInt")
    public double doDouble(double left, double right) {
        return left + right;
    }

    @Specialization
    public VectorObject doIntVector(int left, VectorObject right) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < right.getArraySize(); i++) {
            result.add(Math.addExact(left, (int) right.readArrayElement(i)));
        }
        return new VectorObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, result);
    }

    @Specialization
    public VectorObject doVectorInt(VectorObject left, int right) {
        return doIntVector(right, left);
    }

    @Specialization
    public VectorObject doVectorVector(VectorObject left, VectorObject right) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < right.getArraySize(); i++) {
            result.add(Math.addExact((int) left.readArrayElement(i), (int) right.readArrayElement(i)));
        }
        return new VectorObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, result);
    }

    @Specialization
    public MatrixObject doMatrixMatrix(MatrixObject left, MatrixObject right) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < right.getArraySize(); i++) {
            result.add(Math.addExact((int) left.readArrayElement(i), (int) right.readArrayElement(i)));
        }
        return new MatrixObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, left.getM(), left.getN(), result);
    }

    @Specialization
    public MatrixObject doMatrixInt(MatrixObject left, int right) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < left.getArraySize(); i++) {
            result.add(Math.addExact((int) left.readArrayElement(i), right));
        }
        return new MatrixObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, left.getM(), left.getN(), result);
    }

    @Specialization
    public MatrixObject doIntMatrix(int left, MatrixObject right) {
        return doMatrixInt(right, left);
    }

    @Specialization
    public MatrixObject doMatrixVector(MatrixObject left, VectorObject right) {
        if (right.getArraySize() != left.getM()) {
            throw new MoException("Vector size is not match Matrix");
        }
        MatrixObject rightM = ArrayBaseObject.vector2Matrix(right, this.currentLanguageContext().shapesAndPrototypes.arrayShape, this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, left.getN());
        return doMatrixMatrix(left, rightM);
    }

    @Specialization
    public MatrixObject doVectorMatrix(VectorObject left, MatrixObject right) {
        return doMatrixVector(right, left);
    }

    @Specialization
    public TruffleString doString(TruffleString left, TruffleString right,
                                  @Cached TruffleString.ConcatNode concatNode) {
        return MoStrings.concat(left, right, concatNode);
    }

    @Specialization(guards = "isString(left, right)")
    public TruffleString doString(Object left, Object right,
                                  @Bind("this") Node node,
                                  @Cached MoToTruffleStringNode toTruffleStringNodeLeft,
                                  @Cached MoToTruffleStringNode toTruffleStringNodeRight,
                                  @Cached TruffleString.ConcatNode concatNode) {
        return concatNode.execute(toTruffleStringNodeLeft.execute(node, left), toTruffleStringNodeRight.execute(node, right), MoStrings.STRING_ENCODING, true);
    }

    protected boolean isString(Object left, Object right) {
        return left instanceof TruffleString || right instanceof TruffleString;
    }


}
