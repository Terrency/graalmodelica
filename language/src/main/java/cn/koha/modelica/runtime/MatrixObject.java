package cn.koha.modelica.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.object.Shape;

import java.util.List;

@ExportLibrary(InteropLibrary.class)
public final class MatrixObject extends ArrayBaseObject {
    @DynamicField
    private final int m;
    @DynamicField
    private final int n;

    public MatrixObject(Shape shape, ClassPrototypeObject arrayPrototype, int m, int n, List<Object> elements) {
        super(shape, arrayPrototype, elements);
        this.m = m;
        this.n = n;
    }
    public int getM(){ return m; }
    public int getN(){ return n; }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (int i = 0; i < m; i++) {
            sb.append("{");
            for (int j = 0; j < n; j++) {
                sb.append(this.elements[i * m + j]);
                if (j < n - 1) {
                    sb.append(", ");
                }
            }
            sb.append("}");
            if (i < m - 1) {
                sb.append(", \n");
            }
        }
        sb.append("\n}");
        return sb.toString();
    }
}
