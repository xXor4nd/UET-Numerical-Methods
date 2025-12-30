public class MyLUFactorization {

    public static class LUResult {
        public double[][] L;
        public double[][] U;

        public LUResult(double[][] L, double[][] U) {
            this.L = L;
            this.U = U;
        }
    }

    public static LUResult factorize(double[][] A) {
        int n = A.length;
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];

        for (int i = 0; i < n; i++) {
            L[i][i] = 1.0;
            for (int j = i; j < n; j++) {
                double sum = 0;
                for (int k = 0; k < i; k++) sum += L[i][k] * U[k][j];
                U[i][j] = A[i][j] - sum;
            }
            for (int j = i + 1; j < n; j++) {
                double sum = 0;
                for (int k = 0; k < i; k++) sum += L[j][k] * U[k][i];
                L[j][i] = (A[j][i] - sum) / U[i][i];
            }
        }
        return new LUResult(L, U);
    }

    public static double[] solveLU(double[][] A, double[] b) {
        LUResult result = factorize(A);
        int n = A.length;

        // Ly = b
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) sum += result.L[i][j] * y[j];
            y[i] = b[i] - sum;
        }

        // Ux = y
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) sum += result.U[i][j] * x[j];
            x[i] = (y[i] - sum) / result.U[i][i];
        }
        return x;
    }

    public static void main(String[] args) {
        int n = 50;
        double[][] A = new double[n][n];
        double[] b = new double[n];
        double[] trueX = new double[n];

        for (int i = 0; i < n; i++) {
            trueX[i] = 2.0;
            for (int j = 0; j < n; j++) {
                A[i][j] = (i == j) ? 1000.0 : (i + j + 1.0);
            }
        }

        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) sum += A[i][j] * trueX[j];
            b[i] = sum;
        }

        long start = System.nanoTime();
        LUResult lu = factorize(A);
        double[] x = solveLU(A, b);
        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        System.out.println("Ma tran L (5x5 goc tren):");
        printSmallMatrix(lu.L, 5);
        System.out.println("\nMa tran U (5x5 goc tren):");
        printSmallMatrix(lu.U, 5);

        System.out.printf("\n%-5s | %-20s | %-20s\n", "STT", "Gia tri x[i]", "Sai so tuyet doi");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            double error = Math.abs(x[i] - trueX[i]);
            System.out.printf("%-5d | %-20.15f | %-20.5e\n", i, x[i], error);
        }
        System.out.println("------------------------------------------------------------");
        System.out.printf("Thoi gian chay: %.4f ms\n", timeMs);
    }

    private static void printSmallMatrix(double[][] M, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%10.4f ", M[i][j]);
            }
            System.out.println();
        }
    }
}