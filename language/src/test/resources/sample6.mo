model SinExample
  // 定义一个输入变量
  Real i = 0.5;
  // 定义一个输出变量
  Real o;
equation
  // 使用sin()函数计算输出值
  o = Modelica.Math.sin(i);
end SinExample;