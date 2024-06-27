package cn.koha.modelica.nodes.expr.arrays;

import cn.koha.modelica.common.ShapesAndPrototypes;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.ArrayObject;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

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
        for(int i=0; i< this.arrayElementExprs.length; i++) {
            arrayElements[i] = this.arrayElementExprs[i].executeGeneric(frame);
        }
        ShapesAndPrototypes shapesAndPrototypes = this.currentLanguageContext().shapesAndPrototypes;
        return new ArrayObject(shapesAndPrototypes.arrayShape, shapesAndPrototypes.arrayPrototype, arrayElements);
    }
}
