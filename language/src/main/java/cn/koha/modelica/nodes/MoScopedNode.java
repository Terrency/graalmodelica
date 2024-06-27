package cn.koha.modelica.nodes;

import cn.koha.modelica.MoLanguage;
import cn.koha.modelica.runtime.MoContext;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.nodes.Node;

@ExportLibrary(value = NodeLibrary.class)
public abstract class MoScopedNode extends Node {
    protected final MoLanguage currentLanguage() {
        return MoLanguage.get(this);
    }
    protected final MoContext currentLanguageContext() {
        return MoContext.get(this);
    }
}
