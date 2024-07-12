package cn.koha.modelica.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.object.Shape;

@ExportLibrary(InteropLibrary.class)
public final class VectorObject extends ArrayObject {

    public VectorObject(Shape shape, ClassPrototypeObject arrayPrototype, Object[] elements) {
        super(shape, arrayPrototype, elements);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i=0; i< this.elements.length; i++){
            sb.append(this.elements[i]);
            if(i < this.elements.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
