import java.util.function.Function;

public class MyRootFindingMethods {
    // f(x) = x^10 - x - 1
    static Function<Double, Double> f = x -> Math.pow(x, 10) - x - 1;
    // f'(x) = 10x^9 - 1
    static Function<Double, Double> df = x -> 10 * Math.pow(x, 9) - 1;

    public static void main(String[] args) {
        double a = 1.0, b = 2.0, tol = 1e-10;

        System.out.printf("%-18s | %-15s | %-10s | %-15s | %-15s\n",
                "Phương pháp", "Nghiệm", "Vòng lặp", "Sai số thực tế", "Thời gian (ns)");
        System.out.println("-".repeat(85));

        solveBisection(a, b, tol);
        solveNewton(2.0, tol);
        solveSecant(a, b, tol);
        solveRegulaFalsi(a, b, tol);
    }

    public static void solveBisection(double a, double b, double tol) {
        long start = System.nanoTime();
        int iters = 0;
        double c = a + (b - a) / 2;
        double oldC;

        while ((b - a) / 2.0 >= tol && iters < 1000) {
            oldC = c;
            if (f.apply(a) * f.apply(c) < 0) {
                b = c;
            }
            else {
                a = c;
            }
            c = a + (b - a) / 2;
            iters++;
            if ((b - a) / 2.0 < tol) {
                long end = System.nanoTime();
                printRow("Chia đôi", c, iters, Math.abs(c - oldC), end - start);
                break;
            }
        }
    }

    public static void solveNewton(double a, double tol) {
        long start = System.nanoTime();
        int iters = 0;
        double b = a;
        double error = Double.MAX_VALUE;

        while (error > tol && iters < 1000) {
            double c = b - f.apply(b) / df.apply(b);
            error = Math.abs(c - b);
            b = c;
            iters++;
        }
        long end = System.nanoTime();
        printRow("Newton", b, iters, error, end - start);
    }

    public static void solveSecant(double a, double b, double tol) {
        long start = System.nanoTime();
        int iters = 0;
        double error = Double.MAX_VALUE;
        double c;

        while (error > tol && iters < 1000) {
            double fa = f.apply(a);
            double fb = f.apply(b);

            c = b - fb * (b - a) / (fb - fa);
            error = Math.abs(c - b);

            a = b;
            b = c;
            iters++;
        }
        long end = System.nanoTime();
        printRow("Dây cung", b, iters, error, end - start);
    }

    public static void solveRegulaFalsi(double a, double b, double tol) {
        long start = System.nanoTime();
        int iters = 0;
        double c = a, oldC;
        double error = Double.MAX_VALUE;

        while (error > tol && iters < 1000) {
            oldC = c;
            double fa = f.apply(a);
            double fb = f.apply(b);

            c = b - fb * (b - a) / (fb - fa);
            error = Math.abs(c - oldC);

            if (f.apply(a) * f.apply(c) < 0) {
                b = c;
            }
            else {
                a = c;
            }
            iters++;
        }
        long end = System.nanoTime();
        printRow("Điểm sai", c, iters, error, end - start);
    }

    private static void printRow(String name, double res, int it, double err, long time) {
        System.out.printf("%-18s | %-15.10f | %-10d | %-15.2e | %-15d\n",
                name, res, it, err, time);
    }
}