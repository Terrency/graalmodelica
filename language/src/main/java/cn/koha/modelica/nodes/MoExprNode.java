package cn.koha.modelica.nodes;

import cn.koha.modelica.nodes.stmts.ExprStmtNode;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import cn.koha.modelica.runtime.MoUndefined;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;

@TypeSystemReference(MoTypes.class)
public abstract class MoExprNode extends MoScopedNode {
    public abstract Object executeGeneric(VirtualFrame frame);

    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        return MoTypesGen.expectInteger(this.executeGeneric(frame));
    }

    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        return MoTypesGen.expectDouble(executeGeneric(frame));
    }

    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        Object value = this.executeGeneric(frame);
        if(value == MoUndefined.INSTANCE) {
            return false;
        }
        if(value instanceof Boolean) {
            return (Boolean) value;
        }
        if(value instanceof Integer) {
            return (Integer) value != 0;
        }
        if(value instanceof Double) {
            return (Double) value != 0.0;
        }
        if(value instanceof TruffleString) {
            return !((TruffleString) value).isEmpty();
        }
        return true;
    }

    public Object evaluateAsReceiver(VirtualFrame frame) {
        return MoUndefined.INSTANCE;
    }
    public Object evaluateAsFunction(VirtualFrame frame, Object receiver) {
        return this.executeGeneric(frame);
    }

    public MoStmtNode toStmt() {
        return new ExprStmtNode(this);
    }
}
