package cn.koha.modelica.common;

import cn.koha.modelica.runtime.ClassPrototypeObject;
import cn.koha.modelica.runtime.ObjectPrototype;
import com.oracle.truffle.api.object.Shape;

public final class ShapesAndPrototypes {
    public final Shape rootShape;
    public final Shape arrayShape;
    public final ObjectPrototype objectPrototype;
    public final ClassPrototypeObject functionPrototype;
    public final ClassPrototypeObject arrayPrototype;
    public final ClassPrototypeObject stringPrototype;
    public ShapesAndPrototypes(Shape rootShape, Shape arrayShape,
                               ObjectPrototype objectPrototype, ClassPrototypeObject functionPrototype,
                               ClassPrototypeObject arrayPrototype, ClassPrototypeObject stringPrototype) {
        this.rootShape = rootShape;
        this.arrayShape = arrayShape;
        this.objectPrototype = objectPrototype;
        this.functionPrototype = functionPrototype;
        this.arrayPrototype = arrayPrototype;
        this.stringPrototype = stringPrototype;
    }
}
