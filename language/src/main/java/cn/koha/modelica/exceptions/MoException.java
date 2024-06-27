package cn.koha.modelica.exceptions;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;

public class MoException extends AbstractTruffleException {
    @TruffleBoundary
    public MoException(String message, Node location) {
        super(message, location);
    }
    public MoException(String message) {
        this(message, null);
    }
    public static MoException typeError(Node operation, Object... values) {
        StringBuilder result = new StringBuilder();
        result.append("Type error");
        if(operation != null) {
            SourceSection ss = operation.getEncapsulatingSourceSection();
            if(ss != null && ss.isAvailable()) {
                result.append(" at ").append(ss.getSource().getName()).append(" line ").append(ss.getStartLine()).append(" col ").append(ss.getStartColumn());
            }
        }
        result.append(": operation");
        if(operation != null) {
        }
        result.append(" not defined for");
        return new MoException(result.toString(), operation);
    }
}
