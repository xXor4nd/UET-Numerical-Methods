import java.util.Arrays;

public class MyGaussSeidel {
    private static void solveGaussSeidel(double[][] A, double[] b, double[] x, double tolerance, int maxIterations) {
        int n = A.length;
        double[] trueX = new double[n];
        Arrays.fill(trueX, 1.0);

        long start = System.nanoTime();
        int iterations = 0;
        boolean converged = false;

        for (int m = 0; m < maxIterations; m++) {
            double maxError = 0.0;
            for (int i = 0; i < n; i++) {
                double oldXi = x[i];
                double sum = 0.0;

                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        sum += A[i][j] * x[j];
                    }
                }
                x[i] = (b[i] - sum) / A[i][i];

                double diff = Math.abs(x[i] - oldXi);
                if (diff > maxError) maxError = diff;
            }

            if (maxError < tolerance) {
                iterations = m + 1;
                converged = true;
                break;
            }
        }
        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        System.out.printf("%-5s | %-20s | %-20s\n", "STT", "Gia tri x[i]", "Sai so tuyet doi");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            if (i < 10 || i >= n - 10) {
                double absError = Math.abs(x[i] - trueX[i]);
                System.out.printf("%-5d | %-20.15f | %-20.5e\n", i, x[i], absError);
            } else if (i == 10) {
                System.out.println("  ...  |         ...          |         ...");
            }
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf("Trang thai: %s sau %d vong lap\n", (converged ? "Hoi tu" : "Khong hoi tu"), iterations);
        System.out.printf("Thoi gian chay: %.4f ms\n", timeMs);
    }

    public static void main(String[] args) {
        int n = 2000;
        double[][] A = new double[n][n];
        double[] b = new double[n];
        double[] x0 = new double[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    A[i][j] = 2 * n;
                } else {
                    A[i][j] = 1.0;
                }
            }
            double rowSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += A[i][j];
            }
            b[i] = rowSum;
        }
        solveGaussSeidel(A, b, x0, 1e-10, 1000);
    }
}