package cn.koha.modelica.runtime;

import com.oracle.truffle.api.strings.TruffleString;

public final class MoStrings {
    public static final TruffleString.Encoding STRING_ENCODING = TruffleString.Encoding.UTF_16;
    public static final TruffleString EMPTY = STRING_ENCODING.getEmpty();
    public static TruffleString concat(String v) {
        return fromJavaString(v);
    }
    public static TruffleString concat(TruffleString s1, TruffleString s2, TruffleString.ConcatNode concatNode) {
        return concatNode.execute(s1, s2, STRING_ENCODING, true);
    }
    public static TruffleString fromJavaString(String v) {
        return TruffleString.fromJavaStringUncached(v, STRING_ENCODING);
    }
    public static int length(TruffleString truffleString, TruffleString.CodePointLengthNode lengthNode) {
        return lengthNode.execute(truffleString, STRING_ENCODING);
    }
    public static TruffleString substring(TruffleString truffleString, int index, int length,
                                          TruffleString.SubstringNode substringNode) {
        return substringNode.execute(truffleString, index, length, STRING_ENCODING, true);
    }
    public static boolean equals(TruffleString s1, TruffleString s2,
                                 TruffleString.EqualNode equalNode) {
        return equalNode.execute(s1, s2, STRING_ENCODING);
    }

}
