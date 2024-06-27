package cn.koha.modelica.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.DynamicObjectLibrary;

@ExportLibrary(InteropLibrary.class)
public final class MoClassInstanceObject implements TruffleObject {
    final ClassPrototypeObject prototypeObject;

    public MoClassInstanceObject(ClassPrototypeObject prototypeObject) {
        this.prototypeObject = prototypeObject;
    }

    @Override
    public String toString() {
        return "[object ClassInstanceObject]";
    }

    @ExportMessage
    Object toDisplayString(boolean allowSideEffects) {
        return this.toString();
    }

    @ExportMessage
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    boolean isMemberReadable(String member,
                             @CachedLibrary("this.prototypeObject") DynamicObjectLibrary dynamicObjectLibrary) {
        return dynamicObjectLibrary.containsKey(prototypeObject, member);
    }
    @ExportMessage
    Object readMember(String member, @CachedLibrary("this.prototypeObject") DynamicObjectLibrary dynamicObjectLibrary) throws UnknownIdentifierException {
        Object value = dynamicObjectLibrary.getOrDefault(prototypeObject, member, null);
        if(value == null) {
            throw UnknownIdentifierException.create(member);
        }
        return value;
    }
    @ExportMessage
    Object getMembers(boolean includeInternal,
                      @CachedLibrary("this.prototypeObject") DynamicObjectLibrary dynamicObjectLibrary) {
        return new MemberNamesObject(dynamicObjectLibrary.getKeyArray(this.prototypeObject));
    }
}
