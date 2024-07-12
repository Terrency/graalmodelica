package cn.koha.modelica.nodes.expr.arrays;

import cn.koha.modelica.common.ShapesAndPrototypes;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.ArrayObject;
import cn.koha.modelica.runtime.MatrixObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

import java.util.Arrays;
import java.util.List;

public final class ArrayLiteralExprNode extends MoExprNode {
    @Children
    public final MoExprNode[] arrayElementExprs;

    public ArrayLiteralExprNode(List<MoExprNode> arrElementExprs) {
        this.arrayElementExprs = arrElementExprs.toArray(new MoExprNode[]{});
    }

    @Override
    @ExplodeLoop
    public Object executeGeneric(VirtualFrame frame) {
        Object[] arrayElements = new Object[this.arrayElementExprs.length];
        for (int i = 0; i < this.arrayElementExprs.length; i++) {
            arrayElements[i] = this.arrayElementExprs[i].executeGeneric(frame);
        }
        if (Arrays.stream(arrayElements).noneMatch(a -> a instanceof ArrayObject)) {
            ShapesAndPrototypes shapesAndPrototypes = this.currentLanguageContext().shapesAndPrototypes;
            return new VectorObject(shapesAndPrototypes.arrayShape, shapesAndPrototypes.arrayPrototype, arrayElements);
        } else {
            int m = this.arrayElementExprs.length;
            int n = ((ArrayObject) (arrayElements[0])).getArraySize();
            Object[] matrixElements = new Object[m * n];
            for (int mi = 0; mi < m; mi++) {
                for (int ni = 0; ni < n; ni++) {
                    ArrayObject ao = (ArrayObject) arrayElements[mi];
                    matrixElements[mi * m + ni] = ao.readArrayElement(ni);
                }
            }
            ShapesAndPrototypes shapesAndPrototypes = this.currentLanguageContext().shapesAndPrototypes;
            return new MatrixObject(shapesAndPrototypes.arrayShape, shapesAndPrototypes.arrayPrototype, m, n, matrixElements);
        }
    }
}
