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
@NodeChild("indexExpr")
@NodeChild("rvalueExpr")
public abstract class ArrayIndexWriteExprNode extends MoExprNode {

    @Specialization(limit = "2")
    protected Object writeIntIndexOfArray(Object array, int index, Object rvalue,
                                         @CachedLibrary("array")InteropLibrary arrayInteropLibrary) {
        try {
            arrayInteropLibrary.writeArrayElement(array, index, rvalue);
        } catch (InvalidArrayIndexException | UnsupportedMessageException | UnsupportedTypeException e) {
            throw new MoException(e.getMessage(), this);
        }
        return rvalue;
    }
}
