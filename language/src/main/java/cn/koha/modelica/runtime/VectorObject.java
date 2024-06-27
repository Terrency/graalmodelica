package cn.koha.modelica.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.object.Shape;

@ExportLibrary(InteropLibrary.class)
public final class VectorObject extends ArrayObject {

    public VectorObject(Shape shape, ClassPrototypeObject arrayPrototype, Object[] elements) {
        super(shape, arrayPrototype, elements);
    }
}
