package cn.koha.modelica.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;

@ExportLibrary(InteropLibrary.class)
public class ObjectPrototype extends ClassPrototypeObject {
    public ObjectPrototype(Shape shape) {
        super(shape, "Object", new DynamicObject(shape) {});
    }
    @ExportMessage
    boolean isMemberReadable(String member,
                             @CachedLibrary("this") DynamicObjectLibrary thisObjectLibrary) {
        return thisObjectLibrary.containsKey(this, member);
    }

    @ExportMessage
    Object readMember(String member,
                      @CachedLibrary("this") DynamicObjectLibrary thisObjectLibrary)
            throws UnknownIdentifierException {
        Object value = thisObjectLibrary.getOrDefault(this, member, null);
        if (value == null) {
            throw UnknownIdentifierException.create(member);
        }
        return value;
    }

    @ExportMessage
    boolean isMemberModifiable(String member,
                               @CachedLibrary("this") DynamicObjectLibrary thisObjectLibrary) {
        return this.isMemberReadable(member, thisObjectLibrary);
    }

    @ExportMessage
    boolean isMemberInsertable(String member,
                               @CachedLibrary("this") DynamicObjectLibrary thisObjectLibrary) {
        return !this.isMemberModifiable(member, thisObjectLibrary);
    }
}
