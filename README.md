# GraalModelica
1. 引入 JBlas 对接 Lapack 项目和专属适配 https://github.com/jblas-project/jblas
2. LM 算法 https://github.com/truongduy134/levenberg-marquardt
3. 引入Truffle框架快速开发语言逻辑结构，见参考资料，打包命令 mvn package -Pnative -DskipTests=true


## Plans
| Step | Title                                   | Date            |
|------|-----------------------------------------|-----------------|
| 1    | Structure & Framework                   | 2024.4          |
| 2    | Core                                    | 2024.4          |
| 3    | MSL                                     | 2024.5          |
| 4    | Lookup                                  | 2024.5          |
| 5    | Builtin & External                      | 2024.6          |
| 6    | Equation \ Optimization & Solver        | 2024.7 - 2024.9 |
| 7    | SourceSection \ BenchMark & Performance | 2024.10         |
| 8    | FMU                                     | 2024.11         |


## fmu progress
1. 是固定步长 -> simulate
2. 不是固定步长 -> stepSimulate
3. instantiate
4. setupExperiment
5. enterInitializeMode
6. exitInitializeMode
7. while doStep
8. terminate
9. freeInstance

## Nodes


## 参考资料
1. [GraalVM Truffle Language Implementation Framework](https://www.graalvm.org/latest/graalvm-as-a-platform/language-implementation-framework/LanguageTutorial/)
2. [SimpleLanguage Github](https://github.com/graalvm/simplelanguage)
3. [SimpleLanguage 教程](https://www.bilibili.com/video/BV1ox411L7fa)
4. 类似产品
   - Walfram (商业产品) 
   - Maxima (Lisp编写)[源码](https://github.com/maxima-project-on-github/mixima)
   - sage (集成Maxima)[源码](https://github.com/sagemath/sage/) (https://doc.sagemath.org/html/en/tutorial/tour_algebra.html)
   - SymPy [WIKI](https://zh.wikipedia.org/zh-cn/SymPy)
   - 