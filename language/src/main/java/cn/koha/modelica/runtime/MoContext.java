package cn.koha.modelica.runtime;

import cn.koha.modelica.MoClassNode;
import cn.koha.modelica.MoLanguage;
import cn.koha.modelica.builtins.MoBuiltinNode;
import cn.koha.modelica.common.ShapesAndPrototypes;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.ContextReference;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public final class MoContext {
    public final DynamicObject globalScopeObject;
    public final ShapesAndPrototypes shapesAndPrototypes;
    public final ModelicaFunctionObject emptyFunction;
    public MoContext(DynamicObject globalScopeObject, ShapesAndPrototypes shapesAndPrototypes, ModelicaFunctionObject emptyFunction) {
        this.globalScopeObject = globalScopeObject;
        this.shapesAndPrototypes = shapesAndPrototypes;
        this.emptyFunction = emptyFunction;
    }
    private MoLanguage lang;
    private TruffleLanguage.Env env;
    private MoClassNode core;
    private final Path stdLibHome = Path.of("libs", "MSL323");
    private Map<String, MoClassNode> stdLibModules = new HashMap<>();
    public static ContextReference<MoContext> REF = ContextReference.create(MoLanguage.class);
    public static MoContext get(Node node) {
        return REF.get(node);
    }

    public void initialize(){
//        installBuiltins();
//        initialCore();
//        initialStdLibs();
    }
    public void installBuiltins() {
//        installBuiltin(MoEnumerationNodeFactory.getInstance());
    }
    public void initialCore() {
        try {
            ClassLoader cl = MoContext.class.getClassLoader();
            Source source = Source.newBuilder(MoLanguage.ID, env.getInternalTruffleFile(cl.getResource("core.mo").toURI())).build();
            CallTarget target = env.parseInternal(source);
            core = (MoClassNode) target.call();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
    public void installBuiltin(NodeFactory<? extends MoBuiltinNode> factory) {
        RootCallTarget target = lang.lookupBuiltin(factory);
        if(target.getRootNode() instanceof MoClassNode) {
            TruffleString name = ((MoClassNode) target.getRootNode()).name;
        }
    }
    public void initialStdLibs() {
        try {
            ClassLoader cl = MoContext.class.getClassLoader();
            URI uri = cl.getResource("MSL323").toURI();
            env.getInternalTruffleFile(uri).visit(new FileVisitor<TruffleFile>() {
                @Override
                public FileVisitResult preVisitDirectory(TruffleFile dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(TruffleFile file, BasicFileAttributes attrs) throws IOException {
                    if(!MoLanguage.MIME_TYPE.equals(file.detectMimeType())) return FileVisitResult.CONTINUE;
                    System.out.println("Handler files: " + file.getPath());
                    Source source = Source.newBuilder(MoLanguage.ID, file).build();
                    MoClassNode module = (MoClassNode) env.parseInternal(source).call();
                    stdLibModules.put(module.name.toString(), module);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(TruffleFile file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(TruffleFile dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            }, 20);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public TruffleLanguage.Env getEnv() {
        return env;
    }

    public MoLanguage getLang() {
        return lang;
    }
}
