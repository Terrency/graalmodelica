package cn.koha.modelica.nodes.expr.functions.built_in.method;

import cn.koha.modelica.nodes.expr.functions.built_in.BuiltInFunctionBodyExprNode;
import cn.koha.modelica.runtime.MoStrings;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;

public abstract class CharAtMethodBodyExprNode extends BuiltInFunctionBodyExprNode {
    @Specialization
    protected TruffleString charAtInt(TruffleString self, int index,
                                      @Cached TruffleString.CodePointLengthNode lengthNode,
                                      @Cached TruffleString.SubstringNode substringNode) {
        return index < 0 || index >= MoStrings.length(self, lengthNode)
                ? MoStrings.EMPTY
                : MoStrings.substring(self, index, 1, substringNode);
    }
}
