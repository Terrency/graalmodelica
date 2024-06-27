package cn.koha.modelica.nodes.expr;

import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.runtime.MoUndefinedNameException;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "invoke")
public final class InvokeNode extends MoExprNode {
    @Child
    private MoExprNode functionNode;
    @Children
    private MoExprNode[] argumentNodes;
    @Child
    private InteropLibrary library;

    public InvokeNode(MoExprNode functionNode, MoExprNode[] argumentNodes) {
        this.functionNode = functionNode;
        this.argumentNodes = argumentNodes;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object function = functionNode.executeGeneric(frame);
        CompilerAsserts.compilationConstant(argumentNodes.length);
        Object[] argumentValues = new Object[argumentNodes.length];
        for (int i = 0; i < argumentNodes.length; i++) {
            argumentValues[i] = argumentNodes[i].executeGeneric(frame);
        }
        try {
            return library.execute(function, argumentValues);
        } catch (UnsupportedMessageException | UnsupportedTypeException | ArityException e) {
            throw MoUndefinedNameException.undefinedFunction(this, function);
        }
    }
}
