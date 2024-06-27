package cn.koha.modelica.runtime;

import cn.koha.modelica.MoClassNode;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;

@ExportLibrary(InteropLibrary.class)
public class ModelicaModule implements TruffleObject {
    public final String name;
    public final MoClassNode node;
    public ModelicaModule(String name, MoClassNode node) {
        this.name = name;
        this.node = node;
    }
}
