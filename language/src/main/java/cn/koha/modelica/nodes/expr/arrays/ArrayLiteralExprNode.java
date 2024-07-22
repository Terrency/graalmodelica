package cn.koha.modelica.nodes.expr.arrays;

import cn.koha.modelica.common.ShapesAndPrototypes;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.ArrayBaseObject;
import cn.koha.modelica.runtime.MatrixObject;
import cn.koha.modelica.runtime.VectorObject;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ArrayLiteralExprNode extends MoExprNode {
    @Children
    public final MoExprNode[] arrayElementExprs;

    public ArrayLiteralExprNode(List<MoExprNode> arrElementExprs) {
        this.arrayElementExprs = arrElementExprs.toArray(new MoExprNode[]{});
    }

    @Override
    @ExplodeLoop
    public Object executeGeneric(VirtualFrame frame) {
        List<Object> arrayElements = Arrays.stream(this.arrayElementExprs).map(a -> a.executeGeneric(frame)).toList();
        if (arrayElements.stream().noneMatch(a -> a instanceof ArrayBaseObject)) {
            ShapesAndPrototypes shapesAndPrototypes = this.currentLanguageContext().shapesAndPrototypes;
            return new VectorObject(shapesAndPrototypes.arrayShape, shapesAndPrototypes.arrayPrototype, arrayElements);
        } else {
            int m = this.arrayElementExprs.length;
            int n = ((ArrayBaseObject) (arrayElements.get(0))).getArraySize();
            List<Object> matrixElements = new ArrayList<>(List.of(((ArrayBaseObject) arrayElements).elements));
            ShapesAndPrototypes shapesAndPrototypes = this.currentLanguageContext().shapesAndPrototypes;
            return new MatrixObject(shapesAndPrototypes.arrayShape, shapesAndPrototypes.arrayPrototype, m, n, matrixElements);
        }
    }
}
