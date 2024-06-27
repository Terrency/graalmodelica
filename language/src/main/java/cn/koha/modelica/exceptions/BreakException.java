package cn.koha.modelica.exceptions;

import com.oracle.truffle.api.nodes.ControlFlowException;

public final class BreakException extends ControlFlowException {
    public static BreakException INSTANCE = new BreakException();
    private BreakException(){}
}
