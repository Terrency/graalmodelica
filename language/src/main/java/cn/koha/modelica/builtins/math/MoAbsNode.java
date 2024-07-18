package cn.koha.modelica.builtins.math;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "abs")
public abstract class MoAbsNode extends MoExprNode {
    @Specialization
    public int doAbs(int a) {
        if (a >= 0) return a;
        else return -a;
    }
    @Specialization
    public double doAbs(double a) {
        if (a >= 0) return a;
        else return -a;
    }
}
