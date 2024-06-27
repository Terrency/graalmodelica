package cn.koha.modelica.nodes.util;

import cn.koha.modelica.nodes.MoTypes;
import cn.koha.modelica.runtime.MoStrings;
import com.oracle.truffle.api.dsl.*;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;

@TypeSystemReference(MoTypes.class)
@GenerateUncached
@GenerateInline
@GenerateCached(false)
public abstract class MoToTruffleStringNode extends Node {
    public static final TruffleString TRUE = MoStrings.concat("true");
    public static final TruffleString FALSE = MoStrings.concat("false");

    public abstract TruffleString execute(Node node, Object obj);

    @Specialization
    public static TruffleString fromString(String value,
                                           @Cached(inline = false) TruffleString.FromJavaStringNode fromJavaStringNode) {
        return fromJavaStringNode.execute(value, MoStrings.STRING_ENCODING);
    }

    @Specialization
    public static TruffleString fromTruffleString(TruffleString value) {
        return value;
    }

    @Specialization
    public static TruffleString fromBoolean(Boolean value) {
        return value ? TRUE : FALSE;
    }
}
