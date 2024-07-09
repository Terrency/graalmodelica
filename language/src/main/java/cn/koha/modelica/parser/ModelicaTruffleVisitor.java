package cn.koha.modelica.parser;

import cn.koha.modelica.builtins.derivative.DerNode;
import cn.koha.modelica.builtins.event.InitialNode;
import cn.koha.modelica.common.LocalVariableFrameSlotId;
import cn.koha.modelica.exceptions.MoException;
import cn.koha.modelica.MoLanguage;
import cn.koha.modelica.common.DeclarationKind;
import cn.koha.modelica.nodes.MoExprNode;
import cn.koha.modelica.nodes.expr.RangeNodeGen;
import cn.koha.modelica.nodes.expr.arithmetic.*;
import cn.koha.modelica.nodes.expr.arrays.ArrayLiteralExprNode;
import cn.koha.modelica.nodes.expr.arrays.ArrayWriteExprNodeGen;
import cn.koha.modelica.nodes.expr.arrays.UnknownSize;
import cn.koha.modelica.nodes.expr.comparisons.*;
import cn.koha.modelica.nodes.expr.functions.FunctionCallExprNode;
import cn.koha.modelica.nodes.expr.literals.*;
import cn.koha.modelica.nodes.expr.logical.AndNode;
import cn.koha.modelica.nodes.expr.logical.NotNodeGen;
import cn.koha.modelica.nodes.expr.logical.OrNode;
import cn.koha.modelica.nodes.expr.variables.*;
import cn.koha.modelica.nodes.MoScopedNode;
import cn.koha.modelica.nodes.root.StmtBlockRootNode;
import cn.koha.modelica.nodes.stmts.ExprStmtNode;
import cn.koha.modelica.nodes.stmts.MoStmtNode;
import cn.koha.modelica.nodes.stmts.blocks.BlockStmtNode;
import cn.koha.modelica.nodes.expr.GlobalScopeObjectExprNodeGen;
import cn.koha.modelica.nodes.expr.functions.ReadFunctionArgExprNode;
import cn.koha.modelica.nodes.expr.objects.ClassDeclNode;
import cn.koha.modelica.nodes.stmts.blocks.UserFuncBodyStmtNode;
import cn.koha.modelica.nodes.stmts.controlflow.BreakStmtNode;
import cn.koha.modelica.nodes.stmts.controlflow.ReturnStmtNode;
import cn.koha.modelica.nodes.stmts.loops.ForRangeExprNode;
import cn.koha.modelica.nodes.stmts.loops.ForStmtNode;
import cn.koha.modelica.nodes.stmts.loops.IfStmtNode;
import cn.koha.modelica.nodes.stmts.variables.FuncDeclStmtNodeGen;
import cn.koha.modelica.nodes.stmts.variables.GlobalVarDeclStmtNode;
import cn.koha.modelica.nodes.stmts.variables.GlobalVarDeclStmtNodeGen;
import cn.koha.modelica.runtime.ClassPrototypeObject;
import cn.koha.modelica.runtime.ObjectPrototype;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class ModelicaTruffleVisitor extends Modelica0_3BaseVisitor<Node> {
    public static StmtBlockRootNode parse(MoLanguage lang, Source source, Shape objectShape, ObjectPrototype objectPrototype) throws IOException {
        Modelica0_3Lexer lexer = new Modelica0_3Lexer(CharStreams.fromReader(source.getReader()));
        Modelica0_3Parser parser = new Modelica0_3Parser(new CommonTokenStream(lexer));
        lexer.removeErrorListeners();
        parser.removeErrorListeners();
        ModelicaTruffleVisitor visitor = new ModelicaTruffleVisitor(objectShape, objectPrototype);
        List<MoStmtNode> stmtNodes;
        if(!source.isInteractive()) {
            stmtNodes = visitor.visitStoredDefinition(parser.stored_definition());
        } else {
            stmtNodes = List.of(visitor.visitStatement_comma(parser.statement_comma()));
        }
        return new StmtBlockRootNode(lang, visitor.frameDescriptor.build(), new BlockStmtNode(stmtNodes));
    }

    private FrameDescriptor.Builder frameDescriptor;
    private Shape shape;

    private int localVariablesCounter = 0;

    public ModelicaTruffleVisitor(Shape shape, ObjectPrototype objectPrototype) {
        this.shape = shape;
        this.frameDescriptor = FrameDescriptor.newBuilder();
        this.localScopes = new Stack<>();
        Map<String, FrameMember> classPrototypes = new HashMap<>();
        classPrototypes.put("Object", new ClassPrototypeMember(objectPrototype));
        Map<String, FrameMember> enumPrototypes = new HashMap<>();
        classPrototypes.put("Enum", new ClassPrototypeMember(objectPrototype));
        Map<String, FrameMember> realPrototypes = new HashMap<>();
        classPrototypes.put("Real", new ClassPrototypeMember(objectPrototype));
        this.localScopes.push(classPrototypes);
    }

    public List<MoStmtNode> visitStoredDefinition(Modelica0_3Parser.Stored_definitionContext ctx) {
        return visitClassDefinition(ctx.class_definition().get(0));
    }
    @Override
    public MoStmtNode visitStatement_comma(Modelica0_3Parser.Statement_commaContext ctx) {
        if(ctx.children.size() == 1) {
            //直接输出
        } else {
            //结果保存到内存
        }
        return parseStmt(ctx.statement());
    }

    public List<MoStmtNode> visitClassDefinition(Modelica0_3Parser.Class_definitionContext ctx) {
        boolean hasEncapsulated = AntlrUtils.hasTerminalNodeChild(ctx, "encapsulated");
        return visitClassSpecifier(ctx.class_specifier());
//        result.hasEncapsulated = hasEncapsulated;
//        return visitClassPrefixes(ctx.class_prefixes(), result);
    }

    public List<MoStmtNode> visitClassSpecifier(Modelica0_3Parser.Class_specifierContext ctx) {
        if (ctx.long_class_specifier() != null) return visitLongClassSpecifier(ctx.long_class_specifier());
        if (ctx.short_class_specifier() != null) return visitShortClassSpecifier(ctx.short_class_specifier());
        return visitDerClassSpecifier(ctx.der_class_specifier());
    }

    private List<MoStmtNode> visitClassPrefixes(Modelica0_3Parser.Class_prefixesContext ctx, ClassDeclNode node) {
        String classPrefixes = ctx.getText().toLowerCase(Locale.getDefault());
//        node.isPartial = classPrefixes.contains("partial");
//        node.type = MoClassNode.ClassType.CLASS;
//        node.restriction = null; //visitRestriction(classPrefixes)
        return null;
    }

    public List<MoStmtNode> visitLongClassSpecifier(Modelica0_3Parser.Long_class_specifierContext ctx) {
        String name = ctx.IDENT(0).getText();
//        MoClassDeclNode node = MoGlobalVarDeclStmtNodeGen.create(this.shape, name);
//        node.name = TruffleString.fromJavaStringUncached(ctx.IDENT(0).getText(), TruffleString.Encoding.UTF_16);
        // TODO string_comment
//        currentNode = node;
        List<MoStmtNode> stmtNodes = visitCompositions(ctx.composition());
//        node.childNodes = new MoClassBlockNode(currentChildren.toArray(new MoClassNode[0]));
//        currentChildren = new ArrayList<>();
        return stmtNodes;
    }

    public List<MoStmtNode> visitShortClassSpecifier(Modelica0_3Parser.Short_class_specifierContext ctx) {
//        MoClassDeclNode node = new MoClassDeclNode(language, frame.build());
//        node.name = TruffleString.fromJavaStringUncached(ctx.IDENT().getText(), TruffleString.Encoding.UTF_16);
//        return node;
        return null;
    }

    public List<MoStmtNode> visitDerClassSpecifier(Modelica0_3Parser.Der_class_specifierContext ctx) {
//        return new MoClassDeclNode(language, frame.build());
        return null;
    }

    public List<MoStmtNode> visitCompositions(Modelica0_3Parser.CompositionContext ctx) {
        List<MoStmtNode> result = new ArrayList<>();
        // 第1步解析short类function
        Modelica0_3Parser.Element_listContext shortClassAndFunctions = ctx.element_list().get(ctx.element_list().size() - 1);
        List<MoStmtNode> funcDeclStmtNodes = new ArrayList<>();
        for (Modelica0_3Parser.ElementContext ec : shortClassAndFunctions.element()) {
            funcDeclStmtNodes.add(this.parseFuncAndShortClassStmt(ec));
        }
        // 第2步解析变量
        List<MoStmtNode> variableStmts = new ArrayList<>();
        Modelica0_3Parser.Element_listContext variableList = ctx.element_list(0);
        for (Modelica0_3Parser.ElementContext vl : variableList.element()) {
            variableStmts.addAll(this.visitComponentClause(vl.component_clause()));
        }
        // 第3步解析方程,初始化方程，绑定方程，普通方程
        Modelica0_3Parser.Equation_sectionContext initialEqns = ctx.equation_section(0);
        Modelica0_3Parser.Equation_sectionContext bindingEqns = ctx.equation_section(1);
        Modelica0_3Parser.Equation_sectionContext normalEqns = ctx.equation_section(2);
        List<MoStmtNode> initialEqnStmts = new ArrayList<>();
        List<MoStmtNode> bindingEqnStmts = new ArrayList<>();
        List<MoStmtNode> normalEqnStmts = new ArrayList<>();
        for (Modelica0_3Parser.EquationContext ec : initialEqns.equation()) {
            initialEqnStmts.add(this.visitEquation(ec));
        }
        for (Modelica0_3Parser.EquationContext ec : bindingEqns.equation()) {
            bindingEqnStmts.add(this.visitEquation(ec));
        }
        for (Modelica0_3Parser.EquationContext ec : normalEqns.equation()) {
            normalEqnStmts.add(this.visitEquation(ec));
        }
        return result;
    }

    @Override
    public MoStmtNode visitEquation(Modelica0_3Parser.EquationContext ctx) {
        if (ctx.annotation() != null) return null;
        if (ctx.simple_expression() != null) {
            return this.parseAssignmentExpr(ctx.simple_expression(), ctx.expression());
        } else if (ctx.if_equation() != null) {
            return this.visitIf_equation(ctx.if_equation());
        } else if (ctx.for_equation() != null) {
            return this.visitFor_equation(ctx.for_equation());
        } else if (ctx.connect_clause() != null) {
            // connect clause
            return null;
        } else if (ctx.when_equation() != null) {
            return this.visitWhen_equation(ctx.when_equation());
        } else if (ctx.name() != null) {
            return new ExprStmtNode(this.parseFunctionCall(ctx.name(), ctx.function_call_args()));
        }
        throw new MoException("cannot reach here!");
    }

    private MoExprNode parseFunctionCall(Modelica0_3Parser.NameContext name, Modelica0_3Parser.Function_call_argsContext functionCallArgsContext) {
        return new FunctionCallExprNode(visitName(name), this.visitFunctionCallArgs(functionCallArgsContext));
    }

    @Override
    public MoExprNode visitName(Modelica0_3Parser.NameContext ctx) {
        String name = ctx.getText();
        FrameMember frameMember = this.findFrameMember(name);
        if (frameMember == null || frameMember instanceof ClassPrototypeMember) {
            return GlobalVarReferenceExprNodeGen.create(GlobalScopeObjectExprNodeGen.create(), name);
        } else {
            return frameMember instanceof FunctionArgument
                    ? new ReadFunctionArgExprNode(((FunctionArgument) frameMember).argumentIndex)
                    : LocalVarReferenceExprNodeGen.create(((LocalVariable) frameMember).variableIndex);
        }
    }

    @Override
    public MoStmtNode visitIf_equation(Modelica0_3Parser.If_equationContext ctx) {
        final int endIndex = AntlrUtils.getFirstTerminalNodeChildIndex(ctx, "end");
        final int elseWhenIndex = !AntlrUtils.hasTerminalNodeChild(ctx, "elseif") ? endIndex : AntlrUtils.getFirstTerminalNodeChildIndex(ctx, "elseif");
        List<Modelica0_3Parser.EquationContext> whenEquations = ctx.equation().stream().filter(eq -> AntlrUtils.getChildIndex(eq) < elseWhenIndex).collect(Collectors.toCollection(ArrayList::new));

        List<MoStmtNode> thenStmts = new ArrayList<>();
        for (Modelica0_3Parser.EquationContext ec : whenEquations) {
            thenStmts.add(this.visitEquation(ec));
        }
        List<Modelica0_3Parser.ExpressionContext> expressions = ctx.expression();
        int lastExpressionIndex = endIndex;
        MoStmtNode lastStmtNode = null;
        for (int i = expressions.size() - 1; i > 0; i--) {
            Modelica0_3Parser.ExpressionContext expression = expressions.get(i);
            final int lll = lastExpressionIndex;
            java.util.List<Modelica0_3Parser.EquationContext> elseWhenEquations = ctx.equation().stream().filter(eq -> AntlrUtils.getChildIndex(eq) < lll && AntlrUtils.getChildIndex(eq) > AntlrUtils.getChildIndex(expression)).collect(Collectors.toCollection(ArrayList::new));
            lastExpressionIndex = AntlrUtils.getChildIndex(expression);
            lastStmtNode = visitWhen_equation(expression, elseWhenEquations, lastStmtNode);
        }
        return new IfStmtNode(this.visitExpression(ctx.expression(0)), thenStmts, lastStmtNode);
    }

    @Override
    public MoStmtNode visitWhen_equation(Modelica0_3Parser.When_equationContext ctx) {
        final int endIndex = AntlrUtils.getFirstTerminalNodeChildIndex(ctx, "end");
        final int elseWhenIndex = !AntlrUtils.hasTerminalNodeChild(ctx, "elsewhen") ? endIndex : AntlrUtils.getFirstTerminalNodeChildIndex(ctx, "elsewhen");
        List<Modelica0_3Parser.EquationContext> whenEquations = ctx.equation().stream().filter(eq -> AntlrUtils.getChildIndex(eq) < elseWhenIndex).collect(Collectors.toCollection(ArrayList::new));

        List<MoStmtNode> thenStmts = new ArrayList<>();
        for (Modelica0_3Parser.EquationContext ec : whenEquations) {
            thenStmts.add(this.visitEquation(ec));
        }
        List<Modelica0_3Parser.ExpressionContext> expressions = ctx.expression();
        int lastExpressionIndex = endIndex;
        MoStmtNode lastStmtNode = null;
        for (int i = expressions.size() - 1; i > 0; i--) {
            Modelica0_3Parser.ExpressionContext expression = expressions.get(i);
            final int lll = lastExpressionIndex;
            java.util.List<Modelica0_3Parser.EquationContext> elseWhenEquations = ctx.equation().stream().filter(eq -> AntlrUtils.getChildIndex(eq) < lll && AntlrUtils.getChildIndex(eq) > AntlrUtils.getChildIndex(expression)).collect(Collectors.toCollection(ArrayList::new));
            lastExpressionIndex = AntlrUtils.getChildIndex(expression);
            lastStmtNode = visitWhen_equation(expression, elseWhenEquations, lastStmtNode);
        }
        return new IfStmtNode(this.visitExpression(ctx.expression(0)), thenStmts, lastStmtNode);
    }

    private MoStmtNode visitWhen_equation(Modelica0_3Parser.ExpressionContext exp, List<Modelica0_3Parser.EquationContext> then, MoStmtNode elseStmt) {
        return visitWhen_equation(exp, then, elseStmt == null ? Collections.emptyList() : List.of(elseStmt));
    }

    private MoStmtNode visitWhen_equation(Modelica0_3Parser.ExpressionContext exp, List<Modelica0_3Parser.EquationContext> then, List<MoStmtNode> elseStmts) {
        if (elseStmts == null) elseStmts = new ArrayList<>();
        List<MoStmtNode> thenStmts = new ArrayList<>();
        for (Modelica0_3Parser.EquationContext ec : then) {
            thenStmts.add(this.visitEquation(ec));
        }
        return new IfStmtNode(this.visitExpression(exp), thenStmts, elseStmts);
    }

    private MoStmtNode parseIfWhenStmt(Modelica0_3Parser.ExpressionContext exp, List<Modelica0_3Parser.StatementContext> then, MoStmtNode elseStmt) {
        return parseIfWhenStmt(exp, then, elseStmt == null ? Collections.emptyList() : List.of(elseStmt));
    }

    private MoStmtNode parseIfWhenStmt(Modelica0_3Parser.ExpressionContext exp, List<Modelica0_3Parser.StatementContext> then, List<MoStmtNode> elseStmts) {
        if (elseStmts == null) elseStmts = new ArrayList<>();
        List<MoStmtNode> thenStmts = new ArrayList<>();
        for (Modelica0_3Parser.StatementContext ec : then) {
            thenStmts.add(this.parseStmt(ec));
        }
        return new IfStmtNode(this.visitExpression(exp), thenStmts, elseStmts);
    }

    @Override
    public MoStmtNode visitFor_equation(Modelica0_3Parser.For_equationContext ctx) {
        List<MoExprNode> rangeNodes = visitForIndices(ctx.for_indices());
        List<MoStmtNode> stmts = ctx.equation().stream().map(this::visitEquation).collect(Collectors.toList());
        // TODO 暂时获取第一个
        return new ForStmtNode(rangeNodes.get(0), new BlockStmtNode(stmts));
    }

    private List<MoExprNode> visitForIndices(Modelica0_3Parser.For_indicesContext ctx) {
        return ctx.for_index().stream().map(this::visitFor_index).collect(Collectors.toList());
    }

    @Override
    public MoExprNode visitFor_indices(Modelica0_3Parser.For_indicesContext ctx) {
        return visitFor_index(ctx.for_index(0));
    }

    @Override
    public MoExprNode visitFor_index(Modelica0_3Parser.For_indexContext ctx) {
        String name = ctx.IDENT().getText();
        MoExprNode expr = ctx.expression() == null ? null : visitExpression(ctx.expression());
        return new ForRangeExprNode(name, expr);
    }

    private MoStmtNode parseAssignmentExpr(Modelica0_3Parser.Simple_expressionContext left, Modelica0_3Parser.ExpressionContext right) {
        MoExprNode leftExpr = this.visitSimple_expression(left);
        MoExprNode rightExpr = this.visitExpression(right);
        if (leftExpr instanceof ArrayLiteralExprNode) {
            return new ExprStmtNode(ArrayWriteExprNodeGen.create(leftExpr, rightExpr));
        }
        String variableName = left.getText();
        FrameMember frameMember = this.findFrameMember(variableName);
        if(leftExpr instanceof DerNode) {
            if(frameMember == null) {
                LocalVariableFrameSlotId frameSlotId = new LocalVariableFrameSlotId(variableName, ++this.localVariablesCounter);
                int frameSlot = this.frameDescriptor.addSlot(FrameSlotKind.Illegal, frameSlotId, DeclarationKind.PARAMETER);
                frameMember = new LocalVariable(frameSlot, DeclarationKind.PARAMETER);
                this.localScopes.peek().putIfAbsent(variableName, frameMember);
            }
            return LocalVarAssignmentExprNodeGen.create(rightExpr, ((LocalVariable) frameMember).variableIndex).toStmt();
        } else {
            if (frameMember == null) {
                throw new MoException("Undefined component");
            } else if (frameMember instanceof DerMember) {
                FrameMember realFM = this.findFrameMember(((DerMember) frameMember).name);
            } else if (frameMember instanceof LocalVariable) {
                return LocalVarAssignmentExprNodeGen.create(rightExpr, ((LocalVariable) frameMember).variableIndex).toStmt();
            }
        }
        return null;
    }

    private MoStmtNode parseFuncAndShortClassStmt(Modelica0_3Parser.ElementContext ec) {
        if (ec.class_definition() != null) {
            switch (ec.class_definition().class_prefixes().getText()) {
                case "type":
                    return parseShortClassStmt(ec.class_definition().class_specifier().short_class_specifier());
                case "function":
                    return parseFuncDeclStmt(ec.class_definition().class_specifier().long_class_specifier());
                default:
                    return null;
            }
        }
        return null;
    }

    private MoStmtNode parseFuncDeclStmt(Modelica0_3Parser.Long_class_specifierContext ctx) {
        FrameDescriptor.Builder previous = this.frameDescriptor;
        this.frameDescriptor = FrameDescriptor.newBuilder();
        //arguments
        List<MoStmtNode> stmtNodes = this.parseStmtList(ctx.composition());
        FrameDescriptor frameDescriptor = this.frameDescriptor.build();
        this.frameDescriptor = previous;
        return FuncDeclStmtNodeGen.create(GlobalScopeObjectExprNodeGen.create(), ctx.IDENT(0).getText(), frameDescriptor, new UserFuncBodyStmtNode(stmtNodes), 2);
    }

    private List<MoStmtNode> parseStmtList(Modelica0_3Parser.CompositionContext composition) {
        List<MoStmtNode> stmtNodes = new ArrayList<>();
        for (Modelica0_3Parser.Element_listContext el : composition.element_list()) {
            for (Modelica0_3Parser.ElementContext ec : el.element()) {
            }
        }
        for (Modelica0_3Parser.Algorithm_sectionContext as : composition.algorithm_section()) {
            for (Modelica0_3Parser.StatementContext st : as.statement()) {
                stmtNodes.add(this.parseStmt(st));
            }
        }
        return stmtNodes;
    }

    private MoStmtNode parseStmt(Modelica0_3Parser.StatementContext ctx) {
        if (ctx.output_expression_list() != null) {
            // assign
            return null;
        } else if (ctx.component_reference() != null) {
            String name = ctx.component_reference().getText();
            MoExprNode initExpr = visitExpression(ctx.expression());
            return new ExprStmtNode(GlobalVarAssignmentExprNodeGen.create(GlobalScopeObjectExprNodeGen.create(), initExpr, name));
        } else if (ctx.if_statement() != null) {
            // if stmt
            return this.parseIfWhenStmt(ctx.if_statement());
        } else if (ctx.for_statement() != null) {
            // for stmt
            return this.parseForStmt(ctx.for_statement());
        } else if (ctx.while_statement() != null) {
            // while stmt
            return this.parseWhileStmt(ctx.while_statement());
        } else if (ctx.when_statement() != null) {
            // when stmt
            return this.parseIfWhenStmt(ctx.when_statement());
        } else {
            String first = ctx.getChild(0).getText();
            // control flow stmt
            return switch (first) {
                case "break" -> new BreakStmtNode();
                case "return" -> new ReturnStmtNode();
                default -> throw new MoException("bad stmt");
            };
        }
    }

    private MoStmtNode parseIfWhenStmt(Modelica0_3Parser.If_statementContext ctx) {
        final int endIndex = AntlrUtils.getFirstTerminalNodeChildIndex(ctx, "end");
        final int elseWhenIndex = !AntlrUtils.hasTerminalNodeChild(ctx, "elseif") ? endIndex : AntlrUtils.getFirstTerminalNodeChildIndex(ctx, "elseif");
        List<Modelica0_3Parser.StatementContext> whenEquations = ctx.statement().stream().filter(eq -> AntlrUtils.getChildIndex(eq) < elseWhenIndex).collect(Collectors.toCollection(ArrayList::new));

        List<MoStmtNode> thenStmts = new ArrayList<>();
        for (Modelica0_3Parser.StatementContext ec : whenEquations) {
            thenStmts.add(this.parseStmt(ec));
        }
        List<Modelica0_3Parser.ExpressionContext> expressions = ctx.expression();
        int lastExpressionIndex = endIndex;
        MoStmtNode lastStmtNode = null;
        for (int i = expressions.size() - 1; i > 0; i--) {
            Modelica0_3Parser.ExpressionContext expression = expressions.get(i);
            final int lll = lastExpressionIndex;
            java.util.List<Modelica0_3Parser.StatementContext> elseWhenEquations = ctx.statement().stream().filter(eq -> AntlrUtils.getChildIndex(eq) < lll && AntlrUtils.getChildIndex(eq) > AntlrUtils.getChildIndex(expression)).collect(Collectors.toCollection(ArrayList::new));
            lastExpressionIndex = AntlrUtils.getChildIndex(expression);
            lastStmtNode = parseIfWhenStmt(expression, elseWhenEquations, lastStmtNode);
        }
        return new IfStmtNode(this.visitExpression(ctx.expression(0)), thenStmts, lastStmtNode);
    }

    private MoStmtNode parseIfWhenStmt(Modelica0_3Parser.When_statementContext ctx) {
        final int endIndex = AntlrUtils.getFirstTerminalNodeChildIndex(ctx, "end");
        final int elseWhenIndex = !AntlrUtils.hasTerminalNodeChild(ctx, "elsewhen") ? endIndex : AntlrUtils.getFirstTerminalNodeChildIndex(ctx, "elsewhen");
        List<Modelica0_3Parser.StatementContext> whenEquations = ctx.statement().stream().filter(eq -> AntlrUtils.getChildIndex(eq) < elseWhenIndex).collect(Collectors.toCollection(ArrayList::new));

        List<MoStmtNode> thenStmts = new ArrayList<>();
        for (Modelica0_3Parser.StatementContext ec : whenEquations) {
            thenStmts.add(this.parseStmt(ec));
        }
        List<Modelica0_3Parser.ExpressionContext> expressions = ctx.expression();
        int lastExpressionIndex = endIndex;
        MoStmtNode lastStmtNode = null;
        for (int i = expressions.size() - 1; i > 0; i--) {
            Modelica0_3Parser.ExpressionContext expression = expressions.get(i);
            final int lll = lastExpressionIndex;
            java.util.List<Modelica0_3Parser.StatementContext> elseWhenEquations = ctx.statement().stream().filter(eq -> AntlrUtils.getChildIndex(eq) < lll && AntlrUtils.getChildIndex(eq) > AntlrUtils.getChildIndex(expression)).collect(Collectors.toCollection(ArrayList::new));
            lastExpressionIndex = AntlrUtils.getChildIndex(expression);
            lastStmtNode = parseIfWhenStmt(expression, elseWhenEquations, lastStmtNode);
        }
        return new IfStmtNode(this.visitExpression(ctx.expression(0)), thenStmts, lastStmtNode);
    }

    private MoStmtNode parseWhileStmt(Modelica0_3Parser.While_statementContext whileStatementContext) {
        return null;
    }

    private MoStmtNode parseForStmt(Modelica0_3Parser.For_statementContext forStatementContext) {
        return null;
    }

    @Override
    public MoExprNode visitExpression(Modelica0_3Parser.ExpressionContext ctx) {
        if (ctx.simple_expression() != null) {
            return visitSimple_expression(ctx.simple_expression());
        } else {
            /*SrcExp elseIfExp = null;
            for (int i = ctx.expression().size() - 1; i > 0; i -= 2) {
                SrcExp ifExp = (SrcExp) visitExpression(ctx.expression(i - 2));
                SrcExp thenExp = (SrcExp) visitExpression(ctx.expression(i - 1));
                SrcExp elseExp = elseIfExp == null ? (SrcExp) visitExpression(ctx.expression(i)) : elseIfExp;
                elseIfExp = new SrcIfExp(ifExp, thenExp, elseExp);
            }
            return elseIfExp;*/
            return null;
        }
    }

    @Override
    public MoExprNode visitSimple_expression(Modelica0_3Parser.Simple_expressionContext ctx) {
        int size = ctx.logical_expression().size();
        if (size == 1) {
            return visitLogical_expression(ctx.logical_expression(0));
        }
        MoExprNode start = visitLogical_expression(ctx.logical_expression(0));
        MoExprNode end = visitLogical_expression(ctx.logical_expression(size - 1));
        MoExprNode step = size == 2 ? null : visitLogical_expression(ctx.logical_expression(1));

        return RangeNodeGen.create(start, end, step);
    }

    @Override
    public MoExprNode visitLogical_expression(Modelica0_3Parser.Logical_expressionContext ctx) {
        if (ctx.logical_term().size() == 1) {
            return visitLogical_term(ctx.logical_term(0));
        }
        OrNode result = new OrNode(visitLogical_term(ctx.logical_term(0)), visitLogical_term(ctx.logical_term(1)));
        for (int i = 2; i < ctx.logical_term().size(); i++) {
            result = new OrNode(result, visitLogical_term(ctx.logical_term(i)));
        }
        return result;
    }

    @Override
    public MoExprNode visitLogical_term(Modelica0_3Parser.Logical_termContext ctx) {
        if (ctx.logical_factor().size() == 1) {
            return visitLogical_factor(ctx.logical_factor(0));
        }
        AndNode result = new AndNode(visitLogical_factor(ctx.logical_factor(0)), visitLogical_factor(ctx.logical_factor(1)));
        for (int i = 2; i < ctx.logical_factor().size(); i++) {
            result = new AndNode(result, visitLogical_factor(ctx.logical_factor(i)));
        }
        return result;
    }

    @Override
    public MoExprNode visitLogical_factor(Modelica0_3Parser.Logical_factorContext ctx) {
        if (AntlrUtils.hasTerminalNodeChild(ctx, "not")) {
            return NotNodeGen.create(visitRelation(ctx.relation()));
        }
        return visitRelation(ctx.relation());
    }

    @Override
    public MoExprNode visitRelation(Modelica0_3Parser.RelationContext ctx) {
        if (ctx.arithmetic_expression().size() == 1) {
            return visitArithmetic_expression(ctx.arithmetic_expression(0));
        }
        MoExprNode left = visitArithmetic_expression(ctx.arithmetic_expression(0));
        MoExprNode right = visitArithmetic_expression(ctx.arithmetic_expression(1));
        return switch (ctx.rel_op().getText()) {
            case "<" -> LesserExprNodeGen.create(left, right);
            case "<=" -> LesserOrEqualExprNodeGen.create(left, right);
            case ">" -> GreaterExprNodeGen.create(left, right);
            case ">=" -> GreaterOrEqualExprNodeGen.create(left, right);
            case "==" -> EqualityExprNodeGen.create(left, right);
            case "<>" -> InequalityExprNodeGen.create(left, right);
            default -> throw new MoException("not support operator: " + ctx.rel_op().getText());
        };
    }

    @Override
    public MoExprNode visitArithmetic_expression(Modelica0_3Parser.Arithmetic_expressionContext ctx) {
        boolean firstOp = false;
        boolean neg = false;
        if (ctx.getChild(0) instanceof Modelica0_3Parser.Add_opContext) {
            firstOp = true;
            if ("-".equals(ctx.add_op(0).getText())) {
                neg = true;
            }
        }
        if (ctx.term().size() == 1) {
            return neg ? NegNodeGen.create(visitTerm(ctx.term(0))) : visitTerm(ctx.term(0));
        }
        MoExprNode result = null;
        for (int i = 0, opIndex = firstOp ? 1 : 0; opIndex < ctx.add_op().size(); opIndex++, i++) {
            MoExprNode left = result == null ? visitTerm(ctx.term(i)) : result;
            MoExprNode right = visitTerm(ctx.term(i + 1));
            switch (ctx.add_op(opIndex).getText()) {
                case "+":
                    result = AddNodeGen.create(left, right);
                    break;
                case "-":
                    result = SubNodeGen.create(left, right);
                    break;
                case ".+":
                    // TODO missing .+
                    result = AddNodeGen.create(left, right);
                    break;
                case ".-":
                    // TODO missing .-
                    result = SubNodeGen.create(left, right);
                    break;
            }
        }
        return result;
    }

    @Override
    public MoExprNode visitTerm(Modelica0_3Parser.TermContext ctx) {
        if (ctx.factor().size() == 1) {
            return visitFactor(ctx.factor(0));
        }
        MoExprNode result = null;
        for (int i = 0; i < ctx.mul_op().size(); i++) {
            MoExprNode left = result == null ? visitFactor(ctx.factor(i)) : result;
            MoExprNode right = visitFactor(ctx.factor(i + 1));
            switch (ctx.mul_op(i).getText()) {
                case "*":
                    result = MulNodeGen.create(left, right);
                    break;
                case "/":
                    result = DivNodeGen.create(left, right);
                    break;
                case ".*":
                    // TODO missing .*
                    result = MulNodeGen.create(left, right);
                    break;
                case "./":
                    // TODO missing ./
                    result = DivNodeGen.create(left, right);
                    break;
            }
        }
        return result;
    }

    @Override
    public MoExprNode visitFactor(Modelica0_3Parser.FactorContext ctx) {
        if (ctx.primary().size() == 1) {
            return visitPrimary(ctx.primary(0));
        }
        MoExprNode left = visitPrimary(ctx.primary(0));
        String op = ctx.getChild(1).getText();
        MoExprNode right = visitPrimary(ctx.primary(1));
        return switch (op) {
            case "^" -> PowNodeGen.create(left, right);
            case ".^" ->
                // TODO missing .^
                    PowNodeGen.create(left, right);
            default -> throw new MoException("undefined operator : " + op);
        };
    }

    @Override
    public MoExprNode visitPrimary(Modelica0_3Parser.PrimaryContext ctx) {
        if (ctx.UNSIGNED_NUMBER() != null) {
            String un = ctx.UNSIGNED_NUMBER().getText();
            if (un.contains(".") || un.contains("e") || un.contains("E") || un.contains("+") || un.contains("-")) {
                return new DoubleLiteralNode(Double.parseDouble(un));
            } else {
                return new IntegerLiteralNode(Integer.parseInt(un));
            }
        } else if (ctx.STRING() != null) {
            return new StringLiteralNode(visitSTRING(ctx.STRING()));
        } else if (ctx.function_call_args() != null) {
            if (ctx.component_reference() != null) {
                return new FunctionCallExprNode(visitComponent_reference(ctx.component_reference()), visitFunctionCallArgs(ctx.function_call_args()));
            } else if (AntlrUtils.hasTerminalNodeChild(ctx, "der")) {
                if (ctx.function_call_args().function_arguments() != null && ctx.function_call_args().function_arguments().function_argument().expression() != null) {
                    return new DerNode(visitExpression(ctx.function_call_args().function_arguments().function_argument().expression()));
                }
            } else if (AntlrUtils.hasTerminalNodeChild(ctx, "initial")) {
                return new InitialNode();//(new SrcNamedAccess("initial"), (Opt<SrcFunctionArguments>) visitFunction_call_args(ctx.function_call_args()));
            }
            return visitExpression(ctx.function_call_args().function_arguments().function_argument().expression());
        } else if (ctx.component_reference() != null) {
            return visitComponent_reference(ctx.component_reference());
        } else if (ctx.output_expression_list() != null) {
            // 多返回如何处理
            List<MoExprNode> outputExpressions = visitOutputExpressionList(ctx.output_expression_list());
            return new ArrayLiteralExprNode(outputExpressions);
        } else if (ctx.expression_list() != null && !ctx.expression_list().isEmpty()) {
            return new ArrayLiteralExprNode(ctx.expression_list().stream().map(this::visitExpression_list).collect(Collectors.toList()));
        } else if (ctx.function_arguments() != null) {
            return new ArrayLiteralExprNode(visitFunctionArguments(ctx.function_arguments()));
        } else if (ctx.array_arguments() != null) {
            //TODO no parser
            throw new MoException("no example in here");
        } else if (ctx.array() != null) {
            return visitArray(ctx.array());
        } else {
            String value = ctx.getText();
            if ("true".equals(value)) {
                return new BooleanLiteralNode(true);
            } else if ("false".equals(value)) {
                return new BooleanLiteralNode(false);
            } else if ("time".equals(value)) {
                return new TimeLiteralNode();
            } else {
                throw new MoException("undefined symbol:" + value);
            }
        }
    }

    @Override
    public MoExprNode visitComponent_reference(Modelica0_3Parser.Component_referenceContext ctx) {
        String name = ctx.IDENT(0).getText();
        FrameMember frameMember = this.findFrameMember(name);
        MoExprNode refNode;
        if (frameMember == null || frameMember instanceof ClassPrototypeMember) {
            refNode = GlobalVarReferenceExprNodeGen.create(GlobalScopeObjectExprNodeGen.create(), name);
        } else {
            refNode = frameMember instanceof FunctionArgument
                    ? new ReadFunctionArgExprNode(((FunctionArgument) frameMember).argumentIndex)
                    : LocalVarReferenceExprNodeGen.create(((LocalVariable) frameMember).variableIndex);
        }
        if(ctx.array_subscripts() != null && !ctx.array_subscripts().isEmpty()) {
            int varIndex = frameMember instanceof FunctionArgument ? ((FunctionArgument) frameMember).argumentIndex
                    :((LocalVariable) frameMember).variableIndex;
            return LocalArrayReferenceExprNodeGen.create(varIndex, this.visitArray_subscripts(ctx.array_subscripts(0)));
        } else {
            return refNode;
        }

    }

    @Override
    public MoExprNode visitArray_subscripts(Modelica0_3Parser.Array_subscriptsContext ctx) {
//      TODO  return many subscript
        return visitSubscript_(ctx.subscript_(0));
    }

    @Override
    public MoExprNode visitSubscript_(Modelica0_3Parser.Subscript_Context ctx) {
        if(ctx.expression() != null) {
            return visitExpression(ctx.expression());
        }
        return UnknownSize.INSTANCE;
    }

    public List<MoExprNode> visitOutputExpressionList(Modelica0_3Parser.Output_expression_listContext ctx) {
        return ctx.expression().stream().map(this::visitExpression).collect(Collectors.toList());
    }

    @Override
    public MoExprNode visitExpression_list(Modelica0_3Parser.Expression_listContext ctx) {
        return null;
    }

    @Override
    public ArrayLiteralExprNode visitArray(Modelica0_3Parser.ArrayContext ctx) {
        if (ctx.array() != null && !ctx.array().isEmpty()) {
            return new ArrayLiteralExprNode(ctx.array().stream().map(this::visitArray).collect(Collectors.toList()));
        } else {
            return new ArrayLiteralExprNode(ctx.signed_number().stream().map(this::visitSigned_number).collect(Collectors.toList()));
        }
    }

    @Override
    public MoExprNode visitSigned_number(Modelica0_3Parser.Signed_numberContext ctx) {
        boolean neg = false;
        if ("-".equals(ctx.getChild(0).getText())) {
            neg = true;
        }
        String un = ctx.UNSIGNED_NUMBER().getText();
        if (un.contains(".") || un.contains("e") || un.contains("E") || un.contains("+") || un.contains("-")) {
            double va = Double.parseDouble(ctx.UNSIGNED_NUMBER().getText());
            if (neg) va = -va;
            return new DoubleLiteralNode(va);
        } else {
            int va = Integer.parseInt(ctx.UNSIGNED_NUMBER().getText());
            if (neg) va = -va;
            return new IntegerLiteralNode(va);
        }
    }

    public List<MoExprNode> visitFunctionCallArgs(Modelica0_3Parser.Function_call_argsContext ctx) {
        if (ctx.function_arguments() == null) return Collections.emptyList();
        return visitFunctionArguments(ctx.function_arguments());
    }

    public List<MoExprNode> visitFunctionArguments(Modelica0_3Parser.Function_argumentsContext ctx) {
        if (ctx.named_arguments() != null) {
            return visitNamedArguments(ctx.named_arguments());
        } else {
            List<MoExprNode> result = new ArrayList<>();
            result.add(visitFunction_argument(ctx.function_argument()));
            if (ctx.function_arguments_non_first() != null) {
                result.addAll(visitFunctionArgumentsNonFirst(ctx.function_arguments_non_first()));
                return result;
            } else if (ctx.for_indices() != null) {
                throw new MoException("no for index");
//                left.add(new SrcIterExp((SrcExp) visitFunction_argument(ctx.function_argument()), (List) visitFor_indices(ctx.for_indices())));
            } else {
                return result;
            }
        }
    }

    private List<MoExprNode> visitFunctionArgumentsNonFirst(Modelica0_3Parser.Function_arguments_non_firstContext ctx) {
        if (ctx.function_argument() != null) {
            List<MoExprNode> list = ctx.function_argument().stream().map(this::visitFunction_argument).collect(Collectors.toList());
            if (ctx.named_arguments() != null) list.addAll(this.visitNamedArguments(ctx.named_arguments()));
            return list;
        }
        return visitNamedArguments(ctx.named_arguments());
    }

    @Override
    public MoExprNode visitFunction_argument(Modelica0_3Parser.Function_argumentContext ctx) {
        if (ctx.expression() != null) {
            return visitExpression(ctx.expression());
        } else {
            List<MoExprNode> na = new ArrayList<>();
            if (ctx.named_arguments() != null) {
                visitNamedArguments(ctx.named_arguments());
            }
            throw new MoException("not support function name argument");
//            return new SrcPartialFunctionCall((SrcAccess) visitName(ctx.name()), (Opt) new Opt(new SrcFunctionArguments(new List(), na)));
        }
    }

    private List<MoExprNode> visitNamedArguments(Modelica0_3Parser.Named_argumentsContext ctx) {
        return ctx.named_argument().stream().map(this::visitNamed_argument).collect(Collectors.toList());
    }

    @Override
    public MoExprNode visitNamed_argument(Modelica0_3Parser.Named_argumentContext ctx) {
        String name = ctx.IDENT().getText();
        FrameMember frameMember = this.findFrameMember(name);
        MoExprNode initializerExpr = this.visitFunction_argument(ctx.function_argument());
        LocalVariable lv = (LocalVariable) frameMember;
        return LocalVarAssignmentExprNodeGen.create(initializerExpr, lv.variableIndex);
    }

    private MoStmtNode parseShortClassStmt(Modelica0_3Parser.Short_class_specifierContext ctx) {
        String className = ctx.IDENT().getText();
        String superName;
        if (ctx.name() != null) {
            superName = ctx.name().getText();
        } else {
            superName = "Enum";
        }
        ClassPrototypeObject classPrototypeObject;
        FrameMember frameMember = this.localScopes.get(0).get(superName);
        ClassPrototypeObject superClass;
        if (frameMember instanceof ClassPrototypeMember) {
            superClass = (ClassPrototypeObject) ((ClassPrototypeMember) frameMember).classPrototypeObject;
        } else {
            throw new MoException("class '" + className + "' extends unknown");
        }
        // static object & dynamic object
        classPrototypeObject = new ClassPrototypeObject(this.shape, className, superClass);
        return GlobalVarDeclStmtNodeGen.create(GlobalScopeObjectExprNodeGen.create(), new ClassDeclNode(classPrototypeObject), className, DeclarationKind.PARAMETER);
    }

    public GlobalVarDeclStmtNode visitDeclaration(Modelica0_3Parser.DeclarationContext ctx, DeclarationKind declarationKind) {
        String name = ctx.IDENT().getText();
        MoExprNode initializerExpr;
        if (ctx.modification() != null) {
            initializerExpr = visitModification(ctx.modification());
        } else {
            initializerExpr = new UndefinedLiteralNode();
        }
        return GlobalVarDeclStmtNodeGen.create(GlobalScopeObjectExprNodeGen.create(), initializerExpr, name, declarationKind);
    }

    @Override
    public MoExprNode visitModification(Modelica0_3Parser.ModificationContext ctx) {
        if (ctx.expression() != null) {
            return visitExpression(ctx.expression());
        }
        // todo class_modification = expression
        return null;
    }

    private TruffleString visitSTRING(TerminalNode ctx) {
        return TruffleString.fromJavaStringUncached(ctx.getText().replaceAll("^\"|\"$", ""), TruffleString.Encoding.UTF_16);
    }

    @Override
    public MoScopedNode visitElement(Modelica0_3Parser.ElementContext ctx) {
        if (ctx.import_clause() != null) {
            return (MoExprNode) visitImport_clause(ctx.import_clause());
        } else if (ctx.extends_clause() != null) {
            return (MoExprNode) visitExtends_clause(ctx.extends_clause());
        } else if (ctx.annotation() != null) {
            return (MoExprNode) visitAnnotation(ctx.annotation());
        } else {
            boolean hasRedeclare = AntlrUtils.hasTerminalNodeChild(ctx, "redeclare");
            boolean hasFinal = AntlrUtils.hasTerminalNodeChild(ctx, "final");
            boolean hasInner = AntlrUtils.hasTerminalNodeChild(ctx, "inner");
            boolean hasOuter = AntlrUtils.hasTerminalNodeChild(ctx, "outer");
            boolean hasReplaceable = AntlrUtils.hasTerminalNodeChild(ctx, "replaceable");
            if (ctx.class_definition() != null) {
//                MoClassNode sbc = visitClass_definition(ctx.class_definition());
//                currentChildren.add(sbc);
//                sbc.setRedeclare(hasRedeclare);
//                sbc.setFinal(hasFinal);
//                sbc.setInner(hasInner);
//                sbc.setOuter(hasOuter);
//                sbc.setReplaceable(hasReplaceable);
                if (ctx.constraining_clause() != null) {
//                    sbc.setSrcConstrainingClauseOpt(new Opt(visitConstraining_clause(ec.constraining_clause())));
                }
//                Opt<SrcComment> comment = new Opt();
//                if (ec.comment() != null) {
//                    comment = new Opt(visitComment(ec.comment()));
//                }
//                sbc.setConstrainingClauseCommentOpt(comment);
                return null;
            } else {
                List<MoStmtNode> result = visitComponentClause(ctx.component_clause());
//                result.setRedeclare(hasRedeclare);
//                result.setFinal(hasFinal);
//                result.setInner(hasInner);
//                result.setOuter(hasOuter);
//                result.setReplaceable(hasReplaceable);
//                if (ec.constraining_clause() != null) {
//                    result.setSrcConstrainingClauseOpt(new Opt(visitConstraining_clause(ec.constraining_clause())));
//                }
//                if (ec.comment() != null) {
//                    result.setSrcComment((SrcComment) visitComment(ec.comment()));
//                } else {
//                    result.setSrcComment(new SrcComment());
//                }
                return new BlockStmtNode(result);
            }
        }
//        return visitComponent_clause(ctx.component_clause());
    }

    public List<MoStmtNode> visitComponentClause(Modelica0_3Parser.Component_clauseContext ctx) {
        //VarDeclStmt
        DeclarationKind declarationKind = visitTypePrefix(ctx.type_prefix());
        Node node = visitType_specifier(ctx.type_specifier());
        List<MoStmtNode> result = new ArrayList<>();
        for(GlobalVarDeclStmtNode stmt : visitComponentList(ctx.component_list(), declarationKind)) {
            result.add(stmt);
            LocalVariableFrameSlotId frameSlotId = new LocalVariableFrameSlotId(stmt.getName(), ++this.localVariablesCounter);
            int frameSlot = this.frameDescriptor.addSlot(FrameSlotKind.Illegal, frameSlotId, declarationKind);
            this.localScopes.peek().putIfAbsent(stmt.getName(), new LocalVariable(frameSlot, declarationKind));
        }
        return result;
    }

    private DeclarationKind visitTypePrefix(Modelica0_3Parser.Type_prefixContext ctx) {
        return DeclarationKind.fromToken(ctx.getText());
    }

    @Override
    public Node visitType_specifier(Modelica0_3Parser.Type_specifierContext ctx) {
        String name = ctx.getText();
        return parseReference(name);
    }

    private MoExprNode parseReference(String name) {
        FrameMember fm = this.findFrameMember(name);
        if (fm == null || fm instanceof ClassPrototypeMember) {
            return GlobalVarReferenceExprNodeGen.create(GlobalScopeObjectExprNodeGen.create(), name);
        } else {
            return fm instanceof FunctionArgument
                    ? new ReadFunctionArgExprNode(((FunctionArgument) fm).argumentIndex)
                    : LocalVarReferenceExprNodeGen.create(((LocalVariable) fm).variableIndex);
        }
    }

    private FrameMember findFrameMember(String memberName) {
        for (Map<String, FrameMember> scope : this.localScopes) {
            FrameMember fm = scope.get(memberName);
            if (fm != null) {
                return fm;
            }
        }
        return null;
    }

    private Stack<Map<String, FrameMember>> localScopes;

    private static abstract class FrameMember {
    }

    private static final class ClassPrototypeMember extends FrameMember {
        public final ClassPrototypeObject classPrototypeObject;

        ClassPrototypeMember(ClassPrototypeObject classPrototypeObject) {
            this.classPrototypeObject = classPrototypeObject;
        }
    }

    private static final class DerMember extends FrameMember {
        public final String name;

        public DerMember(String name) {
            this.name = name;
        }
    }

    private static final class FunctionArgument extends FrameMember {
        public final int argumentIndex;

        FunctionArgument(int argumentIndex) {
            this.argumentIndex = argumentIndex;
        }
    }

    public static final class LocalVariable extends FrameMember {
        public final int variableIndex;
        public final DeclarationKind declarationKind;

        LocalVariable(int variableIndex, DeclarationKind declarationKind) {
            this.variableIndex = variableIndex;
            this.declarationKind = declarationKind;
        }
    }

    public List<GlobalVarDeclStmtNode> visitComponentList(Modelica0_3Parser.Component_listContext ctx, DeclarationKind declarationKind) {
        List<GlobalVarDeclStmtNode> varis = new ArrayList<>();
        for (Modelica0_3Parser.Component_declarationContext c : ctx.component_declaration()) {
            varis.add(visitComponentDeclaration(c, declarationKind));
        }
//        return visitComponent_declaration(ctx.component_declaration());
//        MoWriteLocalVariableNode node = MoWriteLocalVariableNodeGen.create();
        return varis;
    }

    public GlobalVarDeclStmtNode visitComponentDeclaration(Modelica0_3Parser.Component_declarationContext ctx, DeclarationKind declarationKind) {
        return visitDeclaration(ctx.declaration(), declarationKind);
    }
}
