package cn.koha.modelica.nodes.expr.arithmetic;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.expr.BinaryNode;
import cn.koha.modelica.runtime.ArrayBaseObject;
import cn.koha.modelica.runtime.MatrixObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.ArrayList;
import java.util.List;

@NodeInfo(shortName = "*")
public abstract class MulNode extends BinaryNode {
    @Specialization
    protected int doInt(int left, int right) {
        return Math.multiplyExact(left, right);
    }

    @Specialization
    protected VectorObject doIntVector(int left, VectorObject right) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < right.getArraySize(); i++) {
            result.add(Math.multiplyExact(left, (int) right.readArrayElement(i)));
        }
        return new VectorObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, result);
    }

    @Specialization
    protected VectorObject doVectorInt(VectorObject left, int right) {
        return doIntVector(right, left);
    }

    @Specialization
    protected MatrixObject doIntMatrix(int left, MatrixObject right) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < right.getArraySize(); i++) {
            result.add(Math.multiplyExact(left, (int) right.readArrayElement(i)));
        }
        return new MatrixObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, right.getM(), right.getN(), result);
    }

    @Specialization
    protected MatrixObject doMatrixInt(MatrixObject left, int right) {
        return doIntMatrix(right, left);
    }

    @Specialization
    protected VectorObject doMatrixVector(MatrixObject left, VectorObject right) {
        if (left.getN() != right.getArraySize()) {
            throw new MoException("Matrix n dims is not matched Vector size");
        }
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < left.getM(); i++) {
            int lineI = 0;
            for (int j = 0; j < right.getArraySize(); j++) {
                lineI += Math.multiplyExact((int) left.readArrayElement(i * left.getM() + j), (int) right.readArrayElement(j));
            }
            result.add(lineI);
        }
        return new VectorObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, result);
    }

    @Specialization
    protected VectorObject doVectorMatrix(VectorObject left, MatrixObject right) {
        return doMatrixVector(right, left);
    }

    @Specialization
    protected MatrixObject doMatrixMatrix(MatrixObject left, MatrixObject right) {
        if (left.getN() != right.getM()) {
            throw new MoException("Matrix left n dims is not matched with right m dims");
        }
        int m = left.getM();
        int n = right.getN();
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int lineIJ = 0;
                for (int k = 0; k < n; k++) {
                    lineIJ += (int) left.readArrayElement(i * left.getM() + k) * (int) right.readArrayElement(k * right.getM() + j);
                }
                result.add(lineIJ);
            }
        }
        return new MatrixObject(this.currentLanguageContext().shapesAndPrototypes.arrayShape,
                this.currentLanguageContext().shapesAndPrototypes.arrayPrototype, m, n, result);
    }

    @Specialization
    protected int doArray(ArrayBaseObject left, ArrayBaseObject right) {
        if (left.getArraySize() != right.getArraySize()) {
            throw new MoException("Array not matched, left length is " + left.getArraySize() + ", right length is " + right.getArraySize());
        }
        int result = 0;
        for (int i = 0; i < left.getArraySize(); i++) {
            result += Math.multiplyExact((int) left.readArrayElement(i), (int) right.readArrayElement(i));
        }
        return result;
    }
}
