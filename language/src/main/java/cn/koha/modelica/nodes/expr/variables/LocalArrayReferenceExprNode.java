package cn.koha.modelica.nodes.expr.variables;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeField(name = "frameSlot", type = int.class)
@NodeField(name = "index", type = MoExprNode.class)
public abstract class LocalArrayReferenceExprNode extends MoExprNode {
    protected abstract int getFrameSlot();
    public abstract MoExprNode getIndex();

    @Specialization(guards = "frame.isInt(getFrameSlot())")
    protected int readInt(VirtualFrame frame) {
        return frame.getInt(this.getFrameSlot());
    }
    @Specialization(guards = "frame.isDouble(getFrameSlot())")
    protected double readDouble(VirtualFrame frame) {
        return frame.getDouble(this.getFrameSlot());
    }
    @Specialization(guards = "frame.isBoolean(getFrameSlot())")
    protected boolean readBool(VirtualFrame frame) {
        return frame.getBoolean(this.getFrameSlot());
    }
    @Specialization(replaces = {"readInt", "readDouble", "readBool"})
    protected Object readObject(VirtualFrame frame) {
        return frame.getObject(this.getFrameSlot());
    }
}
