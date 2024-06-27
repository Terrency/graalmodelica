package cn.koha.modelica;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.stmts.controlflow.ClassBlockNode;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.strings.TruffleString;

public class MoClassNode extends RootNode {
    public enum ClassType {
        CLASS, MODEL, RECORD, OPERATOR_RECORD, BLOCK, CONNECTOR, EXPANDABLE_CONNECTOR,
        TYPE, PACKAGE, FUNCTION, OPERATOR_FUNCTION, OPERATOR
    }

    public ClassType type = ClassType.CLASS;
    public boolean hasEncapsulated = false;
    public boolean isPartial = false;
    public TruffleString name;
    @Child
    MoExprNode bodyNode = null;
    @Child public ClassBlockNode childNodes;

    public MoClassNode(MoLanguage lang, FrameDescriptor frame){
        super(lang, frame);
    }
    @Override
    public Object execute(VirtualFrame frame) {
        if(bodyNode == null) return this;
        return bodyNode.executeGeneric(frame);
    }

    @Override
    public String toString() {
        return type + "{ "+ name.toString() + " }";
    }
}
