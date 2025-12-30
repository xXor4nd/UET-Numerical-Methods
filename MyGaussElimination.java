public class MyGaussElimination {
    private static double[] solveGaussElimination(double[][] A, double[] b) {
        int n = b.length;
        double[][] augmentedMatrix = new double[n][n + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = A[i][j];
            }
            augmentedMatrix[i][n] = b[i];
        }

        // Khu xuoi O(n^3)
        for (int i = 0; i < n; ++i) {
            int pivot = i;
            for (int j = i + 1; j < n; ++j) {
                if (Math.abs(augmentedMatrix[j][i]) > Math.abs(augmentedMatrix[pivot][i])) {
                    pivot = j;
                }
            }

            double[] tmp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[pivot];
            augmentedMatrix[pivot] = tmp;

            if (Math.abs(augmentedMatrix[i][i]) < 1e-15) continue;

            for (int j = i + 1; j < n; ++j) {
                double factor = augmentedMatrix[j][i] / augmentedMatrix[i][i];
                for (int k = i; k <= n; ++k) {
                    augmentedMatrix[j][k] -= augmentedMatrix[i][k] * factor;
                }
            }
        }

        // The nguoc O(n^2)
        double[] res = new double[n];
        for (int i = n - 1; i >= 0; --i) {
            res[i] = augmentedMatrix[i][n];
            for (int j = i + 1; j < n; ++j) {
                res[i] -= augmentedMatrix[i][j] * res[j];
            }
            res[i] /= augmentedMatrix[i][i];
        }
        return res;
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
            for (int j = 0; j < n; j++) {
                sum += A[i][j] * trueX[j];
            }
            b[i] = sum;
        }

        long start = System.nanoTime();
        double[] x = solveGaussElimination(A, b);
        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        System.out.printf("%-5s | %-20s | %-20s\n", "STT", "Gia tri x[i]", "Sai so tuyet doi");
        System.out.println("------------------------------------------------------------");

        if (x != null) {
            for (int i = 0; i < n; i++) {
                double error = Math.abs(x[i] - trueX[i]);
                System.out.printf("%-5d | %-20.15f | %-20.5e\n", i, x[i], error);
            }
            System.out.println("------------------------------------------------------------");
            System.out.printf("Thoi gian chay: %.4f ms\n", timeMs);
        }
    }
}