package cn.koha.modelica.nodes.stmts;

import cn.koha.modelica.nodes.MoScopedNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(language = "MO", description = "abstract node form Modelica statements")
@GenerateWrapper
public abstract class MoStmtNode extends MoScopedNode implements InstrumentableNode {
    public static int NO_SOURCE = -1;
    public static int UNAVAILABLE_SOURCE = -2;

    private int sourceCharIndex = NO_SOURCE;

    public abstract Object executeStatement(VirtualFrame frame);

    @Override
    public boolean isInstrumentable() {
        return sourceCharIndex != NO_SOURCE;
    }

    @Override
    public WrapperNode createWrapper(ProbeNode probe) {
        return new MoStmtNodeWrapper(this, probe);
    }
}
