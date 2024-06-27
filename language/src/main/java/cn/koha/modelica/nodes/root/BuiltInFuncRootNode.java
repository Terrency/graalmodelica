package cn.koha.modelica.nodes.root;

import cn.koha.modelica.MoLanguage;
import cn.koha.modelica.nodes.expr.functions.built_in.BuiltInFunctionBodyExprNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public final class BuiltInFuncRootNode extends RootNode {
    @Child
    private BuiltInFunctionBodyExprNode functionBodyExpr;
    public BuiltInFuncRootNode(MoLanguage truffleLanguage,
                               BuiltInFunctionBodyExprNode functionBodyExpr) {
        super(truffleLanguage);

        this.functionBodyExpr = functionBodyExpr;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.functionBodyExpr.executeGeneric(frame);
    }
}
