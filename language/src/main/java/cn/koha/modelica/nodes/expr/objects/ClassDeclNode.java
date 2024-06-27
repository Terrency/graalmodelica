package cn.koha.modelica.nodes.expr.objects;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.ClassPrototypeObject;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

public final class ClassDeclNode extends MoExprNode {
    private final ClassPrototypeObject classPrototypeObject;

    public ClassDeclNode(ClassPrototypeObject classPrototypeObject) {
        this.classPrototypeObject = classPrototypeObject;
    }

    @Override
    @ExplodeLoop
    public Object executeGeneric(VirtualFrame frame) {
        return classPrototypeObject;
    }
}
