package cn.koha.modelica.nodes.expr;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "rangeStep")
@NodeChild("start")
@NodeChild("end")
@NodeChild("step")
public abstract class RangeStepNode extends MoExprNode {
    private boolean initialized = false;
    private int current = -1;

    @Specialization
    protected int doInt(int start, int end, int step) {
        if (!initialized) {
            initialized = true;
            current = start;
            if (end < start) return -1;
        } else {
            current += step;
            if (current > end) return -1;
        }
        return current;
    }
}
