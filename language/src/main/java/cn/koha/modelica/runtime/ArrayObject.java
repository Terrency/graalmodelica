package cn.koha.modelica.runtime;

import cn.koha.modelica.exceptions.MoException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;

@ExportLibrary(InteropLibrary.class)
public class ArrayObject extends ModelicaObject {
    @DynamicField
    private int size;
    protected Object[] elements;

    protected ArrayObject(Shape shape, ClassPrototypeObject arrayPrototype, Object[] elements) {
        super(shape, arrayPrototype);
        this.size = elements.length;
        this.setElements(elements, DynamicObjectLibrary.getUncached());
    }

    @ExportMessage
    public boolean isArrayElementReadable(long index) {
        return index >= 0 && index < elements.length;
    }

    @ExportMessage
    public boolean hasArrayElements() {
        return true;
    }

    @ExportMessage
    public int getArraySize() {
        return elements.length;
    }

    @ExportMessage
    public Object getMembers(boolean includeInternal) {
        return new MemberNamesObject(new String[]{"dims", "size"});
    }

    @ExportMessage
    public boolean isMemberReadable(String name) {
        return true;
    }

    @ExportMessage
    public boolean hasMembers() {
        return true;
    }

    @ExportMessage
    public Object readArrayElement(long index) {
        return isArrayElementReadable(index) ? this.elements[(int) index] : MoNull.SINGLETON;
    }

    @ExportMessage
    public Object readMember(String member, @CachedLibrary("this") DynamicObjectLibrary library) {
        return switch (member) {
//            case "dims" -> library.getOrDefault(this, "dims", new int[]{0});
            case "size" -> library.getOrDefault(this, "size", 0);
            default -> throw new MoException("read member");
        };
    }

    public int getSize() {
        return this.size;
    }

    private void setElements(Object[] elements, DynamicObjectLibrary library) {
        this.elements = elements;
//        library.put(this, "dims", dims);
        library.putInt(this, "size", this.elements.length);
    }

    public static MatrixObject vector2Matrix(VectorObject source, Shape shape, ClassPrototypeObject prototype, int n) {
        int m = source.getArraySize();
        Object[] target = new Object[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                target[i * m + j] = source.readArrayElement(i);
            }
        }
        return new MatrixObject(shape, prototype, m, n, target);
    }
}
