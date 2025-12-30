public class MyDiscreteLeastSquare {
    public static double[] solveLeastSquarePolynomial(double[] x, double[] y, int degree) {
        int m = x.length;
        int n = degree;
        if (m < n + 1) {
            System.err.printf("So diem de tao da thuc bac %d it nhat phai la %d diem\n", n, n + 1);
        }
        double[][] A = new double[n + 1][n + 1];
        double[] b = new double[n + 1];

        // O(n^2 * m)
        for (int j = 0; j <= n; j++) {
            for (int k = 0; k <= n; ++k) {
                double sum = 0;
                for (int i = 0; i < m; ++i) {
                    sum += Math.pow(x[i], j + k);
                }
                A[j][k] = sum;
            }
            double sum = 0;
            for (int i = 0; i < m; ++i) {
                sum += y[i] * Math.pow(x[i], j);
            }
            b[j] = sum;
        }

        return solveGaussElimination(A, b);
    }

    private static double[] solveGaussElimination(double[][] A, double[] b) {
        int n = b.length;
        double[][] augmentedMatrix = new double[n][n + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = A[i][j];
            }
            augmentedMatrix[i][n] = b[i];
        }

        // O(n^3)
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

            for (int j = i + 1; j < n; ++j) {
                double factor = augmentedMatrix[j][i] / augmentedMatrix[i][i];
                for (int k = i; k <= n; ++k) {
                    augmentedMatrix[j][k] -= augmentedMatrix[i][k] * factor;
                }
            }
        }

        // the nguoc O(n^2)
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

    public static double evaluatePolynomial(double[] coefficients, double x) {
        double res = 0;
        for (int i = 0; i < coefficients.length; i++) {
            res += coefficients[i] * Math.pow(x, i);
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 30;
        double[] x = new double[n];
        double[] y = new double[n];

        // y = 3x^2 + 2x + 1
        for (int i = 0; i < n; i++) {
            x[i] = i;
            y[i] = 3 * x[i] * x[i] + 2 * x[i] + 1;
        }

        int[] testDegrees = {1, 2, 3};

        System.out.printf("%-10s | %-50s | %-25s | %-10s\n", "Bac", "Da thuc tim duoc", "Tong binh phuong sai so", "Time (ms)");

        for (int degree : testDegrees) {
            long start = System.nanoTime();
            double[] coeffs = solveLeastSquarePolynomial(x, y, degree);
            long end = System.nanoTime();

            // tong binh phuong sai so
            double sse = 0;
            for (int i = 0; i < n; i++) {
                double diff = evaluatePolynomial(coeffs, x[i]) - y[i];
                sse += diff * diff;
            }

            StringBuilder poly = new StringBuilder("P(x) = ");
            for (int i = 0; i < coeffs.length; i++) {
                poly.append(String.format("%.2f*x^%d ", coeffs[i], i));
                if (i < coeffs.length - 1) poly.append("+ ");
            }

            System.out.printf("%-10d | %-50s | %-25e | %-10.4f\n",
                    degree, poly, sse, (end - start) / 1_000_000.0);
        }
    }
}
