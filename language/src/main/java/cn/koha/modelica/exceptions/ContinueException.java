package cn.koha.modelica.exceptions;

import com.oracle.truffle.api.nodes.ControlFlowException;

public final class ContinueException extends ControlFlowException {
    public static ContinueException INSTANCE = new ContinueException();
    private ContinueException(){}
}
