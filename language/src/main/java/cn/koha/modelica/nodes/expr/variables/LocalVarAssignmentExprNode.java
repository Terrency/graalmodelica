package cn.koha.modelica.nodes.expr.variables;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeChild("initializerExpr")
@NodeField(name = "frameSlot", type = int.class)
@ImportStatic(FrameSlotKind.class)
public abstract class LocalVarAssignmentExprNode extends MoExprNode {
    protected abstract int getFrameSlot();

    @Specialization(guards = "frame.getFrameDescriptor().getSlotKind(getFrameSlot()) == Illegal || " +
            "frame.getFrameDescriptor().getSlotKind(getFrameSlot()) == Int")
    protected int doInt(VirtualFrame frame, int value) {
        int frameSlot = this.getFrameSlot();
        frame.getFrameDescriptor().setSlotKind(frameSlot, FrameSlotKind.Int);
        frame.setInt(frameSlot, value);
        return value;
    }
    @Specialization(guards = "frame.getFrameDescriptor().getSlotKind(getFrameSlot()) == Illegal || " +
            "frame.getFrameDescriptor().getSlotKind(getFrameSlot()) == Double")
    protected double doDouble(VirtualFrame frame, double value) {
        int frameSlot = this.getFrameSlot();
        frame.getFrameDescriptor().setSlotKind(frameSlot, FrameSlotKind.Double);
        frame.setDouble(frameSlot, value);
        return value;
    }
    @Specialization(guards = "frame.getFrameDescriptor().getSlotKind(getFrameSlot()) == Illegal || " +
            "frame.getFrameDescriptor().getSlotKind(getFrameSlot()) == Boolean")
    protected boolean doBoolean(VirtualFrame frame, boolean value) {
        int frameSlot = this.getFrameSlot();
        frame.getFrameDescriptor().setSlotKind(frameSlot, FrameSlotKind.Boolean);
        frame.setBoolean(frameSlot, value);
        return value;
    }
    @Specialization(replaces = {"doInt", "doDouble", "doBoolean"})
    protected Object doObject(VirtualFrame frame, Object value) {
        int frameSlot = this.getFrameSlot();
        frame.getFrameDescriptor().setSlotKind(frameSlot, FrameSlotKind.Object);
        frame.setObject(frameSlot, value);
        return value;
    }
}
