package cn.koha.modelica.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;

import java.util.List;

@ExportLibrary(InteropLibrary.class)
public class ArrayObject extends ArrayBaseObject{
    @DynamicField
    private final int[] dimns;
    protected ArrayObject(Shape shape, ClassPrototypeObject arrayPrototype, List<Integer> dimns, List<Object> elements) {
        super(shape, arrayPrototype, elements);
        this.dimns = dimns.stream().mapToInt(i -> i).toArray();
        DynamicObjectLibrary.getUncached().putInt(this, "size", this.elements.length);
    }

}
