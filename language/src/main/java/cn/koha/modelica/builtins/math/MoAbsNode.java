package cn.koha.modelica.builtins.math;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "abs")
@NodeChild("element")
public abstract class MoAbsNode extends MoExprNode {
    @Specialization
    public int doInt(int a) {
        if (a >= 0) return a;
        else return -a;
    }
    @Specialization
    public double doReal(double a) {
        if (a >= 0) return a;
        else return -a;
    }
}
