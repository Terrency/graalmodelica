package cn.koha.modelica.nodes.expr.arrays;

import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.nodes.MoExprNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;

@NodeChild("arrayExpr")
@NodeChild("rvalueExpr")
public abstract class ArrayWriteExprNode extends MoExprNode {
    @Specialization(limit = "2")
    protected Object writeOfArray(Object array, Object rvalue,
                                  @CachedLibrary("array") InteropLibrary arrayInteropLibrary) {
        try {
            long size = arrayInteropLibrary.getArraySize(array);
            for (int i = 0; i < size; i++) {
                Object value = arrayInteropLibrary.readArrayElement(rvalue, i);
                arrayInteropLibrary.writeArrayElement(array, i, value);
            }
        } catch (InvalidArrayIndexException | UnsupportedMessageException | UnsupportedTypeException e) {
            throw new MoException(e.getMessage(), this);
        }
        return rvalue;
    }
}
