package cn.koha.modelica.launcher;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Language;
import org.graalvm.polyglot.Source;
import org.jline.reader.*;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;

import java.io.Closeable;
import java.io.IOException;

public class MoLanguageShell implements Closeable {
    private final Context context;
    private final Terminal terminal;
    private LineReader reader;
    private Language language;
    private final History history = new DefaultHistory();
    private String input = "";
    public MoLanguageShell(Context context, Terminal terminal){
        this.context = context;
        this.terminal = terminal;
        this.language = this.context.getEngine().getLanguages().get("mo");
        resetLineReader();
    }
    public int runRepl(){
        printHeader();
        printUsage();
        while(true) {
            try {
                input += reader.readLine(prompt()) + "\n";
                if(handleBuiltins() || eval()) {
                    reader.getHistory().add(input);
                    input = "";
                }
            } catch (UserInterruptException | EndOfFileException e){
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }
    private boolean handleBuiltins() {
        final String trimmedInput = input.trim();
        if(trimmedInput.isEmpty()) {
            return true;
        }
        return false;
    }
    public void printHeader() {
        println("Graal Modelica Language Shell v1.0");
        println("Copyright (c) 2024, Modelica");
    }
    public void printUsage() {
        println("Usage: ");
        println("  Type ? for help");
    }
    private void resetLineReader() {
        reader = LineReaderBuilder.builder().appName("GraalVM Modelica Shell " + context.getEngine().getVersion())
                .history(history).build();
        reader.getWidgets().put("CHANGE_LANGUAGE_WIDGET", () -> { throw new RuntimeException(); });
    }
    private String prompt() {
        return createPrompt(language);
    }
    private static String createPrompt(Language language) {
        return String.format("%s> ", language.getId());
    }
    private static String createBufferPrompt(String prompt) {
        StringBuilder b = new StringBuilder();
        for(int i=0; i< prompt.length() - 2; i++) {
            b.append(" ");
        }
        return b.append("+ ").toString();
    }
    private boolean eval() throws IOException {
        Source source = Source.newBuilder(language.getId(), input, "<shell>").interactive(true).build();
        context.eval(source);
        return true;
    }
    private void println(String s) {
        terminal.writer().println(s);
    }
    private void print(String s) {
        terminal.writer().print(s);
    }
    @Override
    public void close() throws IOException {
        terminal.close();
    }
}
