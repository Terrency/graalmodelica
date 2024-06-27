import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.junit.Test;

import java.io.IOException;

public class PolyglotTest {
    Context ctx = Context.newBuilder().allowAllAccess(true).build();
    @Test
    public void test() {
        Context c = Context.newBuilder("mo").allowAllAccess(true).build();
        Value getpid;
        if(System.getProperty("os.name").startsWith("Windows")) {
            getpid = c.eval("mo", "load 'Kernel32.dll'").getMember("GetCurrentProcessId").invokeMember("bind", "():UINT32");
        } else {
            getpid = c.eval("mo", "load 'libc.so.6'").getMember("getpid").invokeMember("bind", "():UINT32");
        }
        int pid = getpid.execute().asInt();
        System.out.println(pid);
    }
    @Test
    public void testJs() {
        Value v = ctx.eval("js", "x => x+1");
        int x = v.execute(51).asInt();
        System.out.println(x);
    }
    @Test
    public void testMo() {
        Value v = ctx.eval("mo", "function add input x; input y; output z; equation z = x + y; end add; ");
        int x = v.execute().asInt();
        System.out.println(x);
    }
    @Test
    public void testBouncingBall() throws IOException {
        Source.Builder builder = Source.newBuilder("mo", PolyglotTest.class.getClassLoader().getResource("BouncingBall.mo") );
        Value v = ctx.eval(builder.build());
        int x = v.execute().asInt();
        System.out.println(x);
    }
    @Test
    public void testFilter() throws IOException {
        Source.Builder builder = Source.newBuilder("mo", PolyglotTest.class.getClassLoader().getResource("Modelica_Blocks_examples_Filter.mo") );
        Value v = ctx.eval(builder.build());
        int x = v.execute().asInt();
        System.out.println(x);
    }
}
