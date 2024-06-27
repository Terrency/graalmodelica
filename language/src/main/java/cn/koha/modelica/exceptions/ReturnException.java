package cn.koha.modelica.exceptions;

import com.oracle.truffle.api.nodes.ControlFlowException;

public final class ReturnException extends ControlFlowException {
    public static ReturnException INSTANCE = new ReturnException();
    private ReturnException(){}
}
