## builtin
1. Builtin Operators 3.7 
   - abs\sign\sqrt\Integer\EnumTypeName\String 3.7.1
   - div\mod\rem\ceil\floor\integer 3.7.2
   - sin\cos\tan\asin\acos\atan\atan2\sinh\cosh\tanh\exp\log\log10 3.7.3
   - der\delay\cardinality\homotopy\semiLinear\inStream\actualStream\spatialDistribution\getInstanceName 3.7.4
   - initial\terminal\noEvent\smooth\sample\pre\edge\change\reinit 3.7.5
2. Builtin Varaible time 3.6.7
3. Builtin Array Function 10.3
    - promote 10.1
    - ndims\size 10.3.1
    - scalar\vector\matrix 10.3.2
    - identity\diagonal\zeros\ones\fill\linspace 10.3.3
    - min\max\sum\product 10.3.4
    - transpose\outerProduct\symmetric\cross\skew 10.3.5
4. Builtin Functions 12.5 (3.7.1\3.7.4\3.7.5\10.3)
5. Connectors and Connections 9.1
    - connect
6. Builtin external 12.9 由外部仿真器提供
7. Predefined Types
    - RealType\IntegerType\BooleanType\StringType\EnumType 4.8

## control flow
1. Statements 11.2

## 全局变量
1. time

## 外部调度库
1. SymPy
2. Maxima

## Node节点
1. 函数调用
2. 变量定义
3. 数组定义
4. 类继承

## 定义
这些术语涉及不同的数值积分方法，用于求解常微分方程（ODEs）或微分代数方程（DAEs）。下面是它们的简要区别：

1. **BDF（Backward Differentiation Formula）：**
   - BDF 是一种隐式数值积分方法，常用于求解刚性的常微分方程或微分代数方程。
   - 它是多步法，通过使用多个历史时间点的信息来计算下一个时间点的解。
   - BDF 方法的一个特点是其对于刚性系统具有较好的数值稳定性。

2. **DASSL：**
   - DASSL 是 Differential-Algebraic System Solver 的缩写，是一种专门用于求解微分代数方程组（DAEs）的数值积分方法。
   - 它结合了隐式积分方法、线性和非线性求解技术以及步长控制等策略，可以有效地处理刚性和非刚性系统，并提供较高的数值稳定性和精度。

3. **Euler：**
   - 欧拉方法是一种简单的数值积分方法，常用于对微分方程进行数值近似。
   - 它是一种显式方法，通过将微分方程的导数近似为差分来计算下一个时间点的解。
   - 欧拉方法的一个缺点是它对于刚性系统的数值稳定性较差，通常需要选择较小的时间步长。

4. **Radau：**
   - Radau 方法是一种隐式数值积分方法，常用于求解刚性的常微分方程。
   - 它是一种单步法，类似于隐式龙格-库塔（RK）方法，但具有更好的数值稳定性和精度。
   - Radau 方法通常用于需要高精度解的情况，特别是对于具有刚性的系统。

5. **RKSET：**
   - RKSET 是一种用于选择龙格-库塔（RK）数值积分方法的工具。
   - 它提供了一种自适应的方式来选择合适的 RK 方法和步长，以求得满足给定精度要求的解。

总的来说，这些方法在原理、适用范围和数值效率等方面有所不同，可以根据具体的求解问题和数值要求选择合适的方法。

## 性能记录
1. 2024年4月24日 现有解析器 MSL 25s
## 参考文档
1. https://www.endoflineblog.com/graal-truffle-tutorial-part-3-specializations-with-truffle-dsl-typesystem
2. https://github.com/tzaeschke/ode4j/tree/master
3. https://www.ufrgs.br/dequi-app/enqlib/numeric/
4. 