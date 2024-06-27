package cn.koha.modelica.runtime;

import cn.koha.modelica.exceptions.MoException;
import com.oracle.truffle.api.nodes.Node;

public final class MoUndefinedNameException extends MoException {
    public MoUndefinedNameException(String message, Node location) {
        super(message, location);
    }
    public static MoUndefinedNameException undefinedFunction(Node location, Object name) {
        throw new MoUndefinedNameException("Undefined function: " + name, location);
    }
}
