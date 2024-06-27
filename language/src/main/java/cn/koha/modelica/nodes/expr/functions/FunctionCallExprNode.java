package cn.koha.modelica.nodes.expr.functions;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;

import java.util.List;

public final class FunctionCallExprNode extends MoExprNode {
    @Child
    private MoExprNode targetFunction;

    @Children
    private final MoExprNode[] callArguments;

    @Child
    private FunctionDispatchNode dispatchNode;

    public FunctionCallExprNode(MoExprNode targetFunction, List<MoExprNode> callArguments){
        this.targetFunction = targetFunction;
        this.callArguments = callArguments.toArray(new MoExprNode[]{});
        this.dispatchNode = FunctionDispatchNodeGen.create();
    }
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object receiver = this.targetFunction.evaluateAsReceiver(frame);
        Object function = this.targetFunction.evaluateAsFunction(frame, receiver);

        Object[] argumentValues = new Object[this.callArguments.length];
        for(int i=0; i< this.callArguments.length; i++) {
            argumentValues[i] = this.callArguments[i].executeGeneric(frame);
        }
        return this.dispatchNode.executeDispatch(function, argumentValues, receiver);
    }
}
