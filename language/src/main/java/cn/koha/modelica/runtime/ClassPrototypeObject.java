package cn.koha.modelica.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;

@ExportLibrary(InteropLibrary.class)
public class ClassPrototypeObject extends ModelicaObject {
    public final String className;

    public ClassPrototypeObject(Shape shape, String className, DynamicObject prototype) {
        super(shape, prototype);
        this.className = className;
    }

    @Override
    public String toString() {
        return "[class " + this.className + "]";
    }

    @ExportMessage
    public Object toDisplayString(@SuppressWarnings("unused") boolean allowSideEffects) {
        return "[class]";
    }
}
