package cn.koha.modelica.builtins.math;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "sqrt")
public abstract class MoSqrtNode extends MoExprNode {
    @Specialization
    public double doInt(int a) {
        if (a > 0) return Math.sqrt(a);
        else throw new MoException("need to be >= 0, but with " + a);
    }
    @Specialization
    public double doReal(double a) {
        if (a > 0) return Math.sqrt(a);
        else throw new MoException("need to be >= 0, but with " + a);
    }
}
