import cn.koha.modelica.MoLanguageProvider;

open module cn.koha.modelica {
	requires com.oracle.truffle.truffle_nfi;
	requires org.graalvm.truffle;
	requires org.antlr.antlr4.runtime;
	requires org.graalvm.polyglot;
    exports cn.koha.modelica;
    exports cn.koha.modelica.exceptions;
    provides com.oracle.truffle.api.provider.TruffleLanguageProvider with
            MoLanguageProvider;
}