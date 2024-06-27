package cn.koha.modelica.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "enumeration")
public abstract class MoEnumerationNode extends MoBuiltinNode {
    @Specialization
    public Object enumeration(String[] names) {
        return names;
    }
}
