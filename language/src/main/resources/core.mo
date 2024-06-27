type Real
    RealType value;
    parameter StringType quantity = "";
    parameter StringType unit = "";
    parameter StringType displayUnit = "";
    parameter RealType min = -1e20;
    parameter RealType max = 1e20;
    parameter RealType start = 0.0;
    parameter BooleanType fixed = false;
    parameter RealType nominal = 0.0;
    parameter EnumType stateSelect = StateSelect.default;
    parameter RealType initialGuess = 0.0;
    parameter BooleanType free = false;
end Real;
type Integer
    IntegerType value;
    parameter StringType quantity = "";
    parameter IntegerType min = -2147483648;
    parameter IntegerType max = 2147483647;
    parameter IntegerType start = 0;
    parameter BooleanType fixed = false;
    parameter IntegerType initialGuess = 0;
    parameter BooleanType free = false;
end Integer;
type Boolean
    BooleanType value;
    parameter StringType quantity = "";
    parameter BooleanType start = false;
    parameter BooleanType fixed = true;
end Boolean;
type String
    StringType value;
    parameter StringType quantity = "";
    parameter StringType start = "";
end String;
partial model ExternalObject
end ExternalObject;
type StateSelect = enumeration(never   "Do not use as state at all.", avoid   "Use as state, if it cannot be avoided (but only if variable appears differentiated and no other potential state with attribute default, prefer, or always can be selected).", default "Use as state if appropriate, but only if variable appears differentiated.", prefer  "Prefer it as state over those having the default value (also variables can be selected, which do not appear differentiated).", always  "Do use it as a state.");
type AssertionLevel = enumeration(error, warning);
type Color = Integer[3](min=0, max=255) "RGB representation";
type LinePattern = enumeration(None, Solid, Dash, Dot, DashDot, DashDotDot);
type FillPattern = enumeration(None, Solid, Horizontal, Vertical, Cross, Forward, Backward, CrossDiag, HorizontalCylinder, VerticalCylinder, Sphere);
type BorderPattern = enumeration(None, Raised, Sunken, Engraved);
type Smooth = enumeration(None, Bezier);
type Arrow = enumeration(None, Open, Filled, Half);
type TextStyle = enumeration(Bold, Italic, UnderLine);
type TextAlignment = enumeration(Left, Center, Right);

type BaseEnum
    EnumType value;
    parameter StringType quantity="";
    parameter EnumType min, max;
    parameter EnumType start;
    parameter BooleanType fixed=false;
end BaseEnum;


//function
//function initial end initial;  //initial与关键字initial冲突
function terminal end terminal;
function smooth
    input Integer p;
    input Real expr;
    output Real y;
end smooth;
function noEvent
    input Real expr;
    output Real y;
end noEvent;
function sample
    input Real start;
    input Real interval;
    output Real y;
end sample;
function pre
    input Real x;
    output Real y;
end pre;
function edge
    input Real x;
    output Real y;
end edge;
function change
    input Real x;
    output Real y;
end change;
function reinit
    input Real x;
    input Real expr;
end reinit;
function assert
    input Boolean condition;
    input String message;
    input AssertionLevel level = AssertionLevel.error;
end assert;
function terminate
    input String reason;
end terminate;
function abs
    input Real v;
    output Real y;
end abs;
function sign
    input Real v;
    output Real y;
end sign;
function sqrt
    input Real x;
    output Real y;
end sqrt;
function universeLifeAndEverything
    output Integer y = 42;
algorithm
end universeLifeAndEverything;
function div  input Real x1;
    input Real x2;
    output Real y;
end div;
function mod  input Real x1;
    input Real x2;
    output Real y;
end mod;
function rem  input Real x1;
    input Real x2;
    output Real y;
end rem;
function floor  input Real x;
    output Real y;
end floor;
function ceil  input Real x;
    output Real y;
end ceil;
function integer  input Real x;
    output Integer y;
end integer;
function Integer  input Real x;
    output Integer y;
end Integer;
function String  input Real x;
    input Integer minimumLength = 0;
    input Boolean leftJustified = true;
    input Integer significantDigits = 6;
    input String format = "";
    output String y;
end String;
function delay  input Real exp;
    input Real delayTime;
    input Real delayMax = delayTime;
    output Real y;
end delay;
function spatialDistribution  input Real in0;
    input Real in1;
    input Real x;
    input Boolean positiveVelocity;
    input Real[:] initialPoints = {0.0, 1.0};
    input Real[:] initialValues = {0.0, 0.0};
    output Real out0;
    output Real out1;
end spatialDistribution;
function cardinality
    input Real x;
    output Integer y;
end cardinality;
package Subtask
    function decouple
        input Real x;
        output Integer y;
    end decouple;
end Subtask;
function ndims
    input Real A;
    output Integer n;
end ndims;
function size
    input Real A;
    input Integer d = 0;
    output Integer s;
end size;
function scalar  input Real A;
    output Real y;
end scalar;
function vector  input Real A;
    output Real B;
end vector;
function matrix  input Real A;
    output Real B;
end matrix;
function transpose
    input Real A;
    output Real B;
end transpose;
function symmetric
    input Real A;
    output Real B;
end symmetric;
function cross
    input Real x[3];
    input Real y[3];
    output Real z[3];
end cross;
function outerProduct
    input Real x[:];
    input Real y[:];
    output Real z[:,:];
end outerProduct;
function diagonal
    input Real v[:];
    output Real z[:,:];
end diagonal;
function identity
    input Integer n;
    output Integer a[n, n];
end identity;
function array end array;
function zeros end zeros;
function ones end ones;
function fill
    input Real s;
    output Real o;
end fill;
function cat
    input Real k;
    output Real o;
end cat;
function linspace
    input Real x1;
    input Real x2;
    input Integer n;
    output Real z[1];
end linspace;
function min
    input Real x;
    input Real y = 0;
    output Real z;
end min;
function max
    input Real x;
    input Real y = 0;
    output Real z;
end max;
function sum
    input Real A;
    output Real o;
end sum;
function product
    input Real A;
    output Real o;
end product;
function skew
    input Real x[3];
    output Real y[3,3];
end skew;
function homotopy
    input Real actual;
    input Real simplified;
    output Real o;
end homotopy;
function semiLinear
    input Real v1;
    input Real v2;
    input Real v3;
    output Real o;
end semiLinear;
function inStream
    input Real v;
    output Real o;
end inStream;
function actualStream
    input Real n;
    output Real o;
end actualStream;
function getInstanceName
    output String o;
end getInstanceName;
function sin "sine"
    input Modelica.SIunits.Angle u;
    output Real y;
end sin;
function cos "cosine"  input Modelica.SIunits.Angle u;
    output Real y;
end cos;
function tan "tangent (u shall not be -pi/2, pi/2, 3*pi/2, ...)"
    input Modelica.SIunits.Angle u;
    output Real y;
end tan;
function asin "inverse sine (-1 <= u <= 1)"
    input Real u;
    output Modelica.SIunits.Angle y;
end asin;
function acos "inverse cosine (-1 <= u <= 1)"
    input Real u;
    output Modelica.SIunits.Angle y;
end acos;
function atan "inverse tangent"
    input Real u;
    output Modelica.SIunits.Angle y;
end atan;
function atan2 "four quadrant inverse tangent"
    input Real u1;
    input Real u2;
    output Modelica.SIunits.Angle y;
end atan2;
function sinh "hyperbolic sine"
    input Real u;
    output Real y;
end sinh;
function cosh "hyperbolic cosine"
    input Real u;
    output Real y;
end cosh;
function tanh "hyperbolic tangent"
    input Real u;
    output Real y;
end tanh;
function exp "exponential, base e"
    input Real u;
    output Real y;
end exp;
function log "natural (base e) logarithm (u shall be > 0)"
    input Real u;
    output Real y;
end log;
function log10 "base 10 logarithm (u shall be > 0)"
    input Real u;
    output Real y;
end log10;
function ln "natural (base e) logarithm (u shall be > 0)"
    input Real u;
    output Real y;
end ln;
package Connections
    function branch
        input Real a;
        input Real b;
    end branch;
    function root
        input Real a;
    end root;
    function potentialRoot
        input Real A;
        input Integer priority = 0;
    end potentialRoot;
    function isRoot
        input Real a;
    end isRoot;
    function rooted
        input Real a;
        output Boolean y;
    end rooted;
end Connections;
function rooted
    input Real a;
    output Boolean y;
end rooted;
function linspace
    input Real a;
    input Real b;
    input Real c;
    output Real y[:,:];
end linspace;
