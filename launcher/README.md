## shell make expression work like open modelica
```
>> a:=1:5;
>> b:=3:8
{3,4,5,6,7,8}
>>> a*b

>>> getErrorString()
"[<interactive>:1:1-1:0:writable] Error: Incompatible argument types to operation scalar product in component <NO COMPONENT>, left type: Integer[5], right type: Integer[6]
[<interactive>:1:1-1:0:writable] Error: Incompatible argument types to operation scalar product in component <NO COMPONENT>, left type: Real[5], right type: Real[6]
[<interactive>:1:1-1:0:writable] Error: Cannot resolve type of expression a * b. The operands have types Integer[5], Integer[6] in component <NO COMPONENT>.
"
>> b:=3:7;
>> a*b
85
>>> listVariables()
{b, a}
```