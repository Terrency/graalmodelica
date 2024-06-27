package cn.koha.modelica.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.object.Shape;

import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public final class MatrixObject extends ArrayObject {
    @DynamicField
    private final int m;
    @DynamicField
    private final int n;

    public MatrixObject(Shape shape, ClassPrototypeObject arrayPrototype, int m, int n, Object[][] elements) {
        super(shape, arrayPrototype, Arrays.stream(elements).flatMap(Arrays::stream).toArray());
        this.m = m;
        this.n = n;
    }
}
