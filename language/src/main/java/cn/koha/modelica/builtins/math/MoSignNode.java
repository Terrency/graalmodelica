package cn.koha.modelica.builtins.math;

import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "sign")
@NodeChild("element")
public abstract class MoSignNode extends MoExprNode {
    @Specialization
    public int doInt(int a) {
        if (a > 0) return 1;
        else if(a < 0) return -1;
        else return 0;
    }
    @Specialization
    public double doReal(double a) {
        if (a > 0) return 1;
        else if(a < 0) return -1;
        else return -a;
    }
}
