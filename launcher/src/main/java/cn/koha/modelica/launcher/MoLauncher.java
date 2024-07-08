package cn.koha.modelica.launcher;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.shadowed.org.jline.terminal.Terminal;
import org.graalvm.shadowed.org.jline.terminal.TerminalBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

public final class MoLauncher {

    private static final String MO = "mo";
    private static String mainClassName = null;
    private static boolean version = false;
    private static boolean shell = false;
    /**
     * The main entry point.
     */
    public static void main(String[] args) throws IOException {
        Source source;
        Map<String, String> options = new HashMap<>();
        boolean launcherOutput = true;
        for (String arg : args) {
            if (arg.equals("--disable-launcher-output")) {
                launcherOutput = false;
            } else if (parseOption(options, arg)) {
                continue;
            } else {
                if (mainClassName == null) {
                    mainClassName = arg;
                }
            }
        }
        final Context.Builder contextBuilder = Context.newBuilder().options(options);
        contextBuilder.allowAllAccess(true);
        setupContextBuilder(contextBuilder);
        if(version) {
            printVersion();
            exit(0);
        }
        if(shell) {
            runShell(contextBuilder);
        } else {
            if (mainClassName == null) {
                // @formatter:off
                source = Source.newBuilder(MO, new InputStreamReader(System.in), "<stdin>").interactive(!launcherOutput).build();
                // @formatter:on
            } else {
                source = Source.newBuilder(MO, new File(mainClassName)).interactive(!launcherOutput).build();
            }

            exit(executeSource(source, System.in, System.out, options, launcherOutput));
        }
    }

    private static void setupContextBuilder(Context.Builder builder) {
        builder.err(System.err);
        builder.out(System.out);
    }

    private static int executeSource(Source source, InputStream in, PrintStream out, Map<String, String> options, boolean launcherOutput) {
        Context context;
        PrintStream err = System.err;
        try {
            context = Context.newBuilder(MO).in(in).out(out).options(options).allowAllAccess(true).build();
        } catch (IllegalArgumentException e) {
            err.println(e.getMessage());
            return 1;
        }

        if (launcherOutput) {
            out.println("== running on " + context.getEngine());
        }

        try {
            Value result = context.eval(source);
//            if (context.getBindings(MO).getMember("main") == null) {
//                err.println("No function main() defined in MO source file.");
//                return 1;
                // modelica mo file not have main function
//            }
            if (launcherOutput && !result.isNull()) {
                out.println(result.toString());
            }
            return 0;
        } catch (PolyglotException ex) {
            if (ex.isInternalError()) {
                // for internal errors we print the full stack trace
                ex.printStackTrace();
            } else {
                err.println(ex.getMessage());
            }
            return 1;
        } finally {
            context.close();
        }
    }

    private static boolean parseOption(Map<String, String> options, String arg) {
        if (arg.length() <= 2 || !arg.startsWith("--")) {
            return false;
        }
        int eqIdx = arg.indexOf('=');
        String key;
        String value;
        if (eqIdx < 0) {
            key = arg.substring(2);
            value = null;
        } else {
            key = arg.substring(2, eqIdx);
            value = arg.substring(eqIdx + 1);
        }

        if (value == null) {
            value = "true";
        }
        if(arg.equals("version")) {
            version = true;
            return true;
        } else if(key.equals("shell")) {
            shell = true;
            return true;
        }
        int index = key.indexOf('.');
        String group = key;
        if (index >= 0) {
            group = group.substring(0, index);
        }
        options.put(key, value);
        return true;
    }
    private static void runShell(Context.Builder builder){
        try(Context context = builder.build();
        MoLanguageShell shell = new MoLanguageShell(context, createSystemTerminal())) {
            shell.runRepl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Terminal createSystemTerminal() throws IOException {
        return TerminalBuilder.builder().jansi(true).jna(false).system(true).signalHandler(Terminal.SignalHandler.SIG_IGN).build();
    }
    private static void runScript(){}
    private static void printVersion(){
        System.out.println("graal modelica version v1.0 ");
    }
}
