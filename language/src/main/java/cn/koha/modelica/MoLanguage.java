package cn.koha.modelica;

import cn.koha.modelica.builtins.MoBuiltinNode;
import cn.koha.modelica.common.ShapesAndPrototypes;
import cn.koha.modelica.nodes.expr.functions.ReadFunctionArgExprNode;
import cn.koha.modelica.nodes.expr.functions.built_in.BuiltInFunctionBodyExprNode;
import cn.koha.modelica.nodes.expr.functions.built_in.method.CharAtMethodBodyExprNodeFactory;
import cn.koha.modelica.nodes.root.BuiltInFuncRootNode;
import cn.koha.modelica.nodes.root.StmtBlockRootNode;
import cn.koha.modelica.nodes.stmts.blocks.BlockStmtNode;
import cn.koha.modelica.parser.ModelicaTruffleVisitor;
import cn.koha.modelica.runtime.*;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.source.Source;
//import com.oracle.truffle.nfi.api.SignatureLibrary;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@TruffleLanguage.Registration(id = MoLanguage.ID, name = "MO", defaultMimeType = MoLanguage.MIME_TYPE, version = "3.0.0", characterMimeTypes = MoLanguage.MIME_TYPE, dependentLanguages = {"nfi"}, contextPolicy = TruffleLanguage.ContextPolicy.SHARED, fileTypeDetectors = MoFileDetector.class, //
        website = "https://www.graalvm.org/graalvm-as-a-platform/implement-language/")
@ProvidedTags({StandardTags.CallTag.class, StandardTags.StatementTag.class, StandardTags.RootTag.class, StandardTags.RootBodyTag.class, StandardTags.ExpressionTag.class, DebuggerTags.AlwaysHalt.class, StandardTags.ReadVariableTag.class, StandardTags.WriteVariableTag.class})
public class MoLanguage extends TruffleLanguage<MoContext> {
    public static final String ID = "mo";
    public static final String MIME_TYPE = "application/x-mo";
    private static final Source BUILTIN_SOURCE = Source.newBuilder(MoLanguage.ID, "", "MO builtin").build();
    private final Map<NodeFactory<? extends MoBuiltinNode>, RootCallTarget> builtinTargets = new ConcurrentHashMap<>();
    private final Shape rootShape = Shape.newBuilder().build();
    private final Shape arrayShape = Shape.newBuilder().layout(ArrayObject.class).build();
    private final ObjectPrototype objectPrototype = new ObjectPrototype(this.rootShape);
    private final ClassPrototypeObject functionPrototype = new ClassPrototypeObject(this.rootShape, "Function", this.objectPrototype);
    private static final LanguageReference<MoLanguage> REFERENCE = LanguageReference.create(MoLanguage.class);
    public static MoLanguage get(Node node) {
        return REFERENCE.get(node);
    }
    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        /*Source source = request.getSource();
        Env env = getCurrentContext(this.getClass()).getEnv();
        String nfiSource = source.getCharacters().toString();
        CallTarget target = env.parseInternal(Source.newBuilder("nfi", "load 'Kernel32.dll'", null).build());
        TruffleObject lib = (TruffleObject) target.call();
        TruffleObject symbol = (TruffleObject) InteropLibrary.getUncached().readMember(lib, "GetCurrentProcessId");

        TruffleObject gpid = (TruffleObject) SignatureLibrary.getUncached().bind(env.parseInternal(Source.newBuilder("nfi", "():SINT32", null).build()).call(), symbol);
        String pid = InteropLibrary.getUncached().execute(gpid).toString();
        System.out.println(pid);
        return env.parseInternal(Source.newBuilder("nfi", nfiSource, null).build());*/
        Env env = getCurrentContext(this.getClass()).getEnv();
//        MSVCRTLibrary lib = new MSVCRTLibrary(env);
//        lib.calloc(1, 1);
//        Kernel32Library kernel32 = new Kernel32Library(env);
//        System.out.println(kernel32.getCurrentProcessId());
//        LapackLibrary lapack = new LapackLibrary(getCurrentContext(this.getClass()).getEnv());
//        int flag = lapack.dgelsv(3, 1, new double[]{1, 2, 3, 2, -1, 0, 3, 1, -1}, Math.max(1, 3), new int[]{0, 0, 0}, new double[]{9, 8, 3}, 3, 0);
//        System.out.println(flag);
        Source source = request.getSource();
        return ModelicaTruffleVisitor.parse(this, source, this.rootShape, this.objectPrototype).getCallTarget();
    }

    @Override
    protected MoContext createContext(Env env) {
        DynamicObjectLibrary objectLibrary = DynamicObjectLibrary.getUncached();
        return new MoContext(
                this.createGlobalScopeObject(objectLibrary),
                this.createShapesAndPrototypes(objectLibrary),
                new ModelicaFunctionObject(
                        this.rootShape,
                        this.functionPrototype,
                        new StmtBlockRootNode(
                                this,
                                FrameDescriptor.newBuilder().build(),
                                new BlockStmtNode(Collections.emptyList())
                        ).getCallTarget(),
                        0
                )
        );
    }
    public RootCallTarget lookupBuiltin(NodeFactory<? extends MoBuiltinNode> factory) {
        return builtinTargets.get(factory);
    }

    @Override
    protected void initializeContext(MoContext context) throws Exception {
        context.initialize();
    }

    private DynamicObject createGlobalScopeObject(DynamicObjectLibrary objectLibrary) {
        return new GlobalScopeObject(this.rootShape);
    }
    private ShapesAndPrototypes createShapesAndPrototypes(DynamicObjectLibrary dynamicObjectLibrary) {
        ClassPrototypeObject arrayPrototype = new ClassPrototypeObject(this.rootShape, "Array", this.objectPrototype);
        return new ShapesAndPrototypes(this.rootShape, this.arrayShape, this.objectPrototype, this.functionPrototype,
                arrayPrototype, this.createStringPrototype(dynamicObjectLibrary));
    }
    private ClassPrototypeObject createStringPrototype(DynamicObjectLibrary objectLibrary) {
        ClassPrototypeObject stringPrototype = new ClassPrototypeObject(this.rootShape, "String", this.objectPrototype);
        objectLibrary.putConstant(stringPrototype, "charAt", this.defineBuiltInMethod(CharAtMethodBodyExprNodeFactory.getInstance()), 0);
        return stringPrototype;
    }
    private ModelicaFunctionObject defineBuiltInFunction(NodeFactory<? extends BuiltInFunctionBodyExprNode> nodeFactory) {
        return new ModelicaFunctionObject(this.rootShape, this.functionPrototype,
                this.createCallTarget(nodeFactory, true), nodeFactory.getExecutionSignature().size());
    }
    private ModelicaFunctionObject defineBuiltInMethod(NodeFactory<? extends BuiltInFunctionBodyExprNode> nodeFactory) {
        return new ModelicaFunctionObject(this.rootShape, this.functionPrototype,
                // built-in method implementation Nodes already have an argument for `this`,
                // so there's no need to offset the method arguments
                this.createCallTarget(nodeFactory, /* offsetArguments */ false),
                // we always add an extra argument for 'this' inside FunctionDispatchNode,
                // but built-in methods already have 'this' in their specializations -
                // for that reason, we make the FunctionObject have one argument less than the specializations take
                nodeFactory.getExecutionSignature().size() - 1);
    }
    private CallTarget createCallTarget(NodeFactory<? extends BuiltInFunctionBodyExprNode> nodeFactory,
                                        boolean offsetArguments) {
        int argumentCount = nodeFactory.getExecutionSignature().size();
        ReadFunctionArgExprNode[] functionArguments = IntStream.range(0, argumentCount)
                .mapToObj(i -> new ReadFunctionArgExprNode(offsetArguments ? i + 1 : i))
                .toArray(ReadFunctionArgExprNode[]::new);
        var rootNode = new BuiltInFuncRootNode(this,
                nodeFactory.createNode((Object) functionArguments));
        return rootNode.getCallTarget();
    }
}
