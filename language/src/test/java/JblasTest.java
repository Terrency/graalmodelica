import org.jblas.DoubleMatrix;
import org.jblas.Solve;
import org.jblas.benchmark.Main;
import org.junit.Assert;
import org.junit.Test;

public class JblasTest {

    @Test
    public void testLib() {
        Main.main(new String[]{});
    }
    @Test
    public void createAndAddTwoDoubleMatricies() {
        int n = 3;
        DoubleMatrix a = new DoubleMatrix(n, n, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
        DoubleMatrix b = DoubleMatrix.ones(n, n).mul(2.0);
        DoubleMatrix expected = new DoubleMatrix(n, n, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0);
        DoubleMatrix actual = a.add(b);
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void firstSolver() {
        int n = 3;
        DoubleMatrix a = new DoubleMatrix(n, n, 1.0, 1.0, 2.0, 2.0, 4.0, -3.0, 3.0, 6.0, -5.0);
        DoubleMatrix b = new DoubleMatrix(n, 1, 9.0, 1.0, 0.0);
        DoubleMatrix expected = new DoubleMatrix(n, 1, 1.0, 2.0, 3.0);
        // exercise
        DoubleMatrix actual = Solve.solve(a, b);
        // assert
        Assert.assertEquals(expected, actual);
    }
}
