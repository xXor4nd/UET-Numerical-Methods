public class MyQR {
    public static double[][][] qrDecomposition(double[][] A) {
        int n = A.length;
        double[][] Q = new double[n][n];
        double[][] R = new double[n][n];

        for (int j = 0; j < n; j++) {
            double[] v = new double[n];
            for (int i = 0; i < n; i++) {
                v[i] = A[i][j];
            }

            for (int i = 0; i < j; i++) {
                // r[i][j] = qi * aj
                R[i][j] = dotProduct(Q, i, A, j);
                // v = v - r[i][j] * qi
                for (int k = 0; k < n; k++) {
                    v[k] -= R[i][j] * Q[k][i];
                }
            }

            R[j][j] = norm(v);

            for (int i = 0; i < n; i++) {
                if (R[j][j] > 1e-15) {
                    Q[i][j] = v[i] / R[j][j];
                } else {
                    Q[i][j] = 0;
                }
            }
        }
        return new double[][][]{Q, R};
    }

    public static double[][] runQR(double[][] A, int maxSteps) {
        int n = A.length;
        double[][] Ak = copy(A);

        for (int step = 0; step < maxSteps; step++) {
            double[][][] qr = qrDecomposition(Ak);
            double[][] Q = qr[0];
            double[][] R = qr[1];

            Ak = multiply(R, Q);
        }
        return Ak;
    }

    private static double dotProduct(double[][] Q, int colQ, double[][] A, int colA) {
        double dot = 0;
        for (int i = 0; i < Q.length; i++) {
            dot += Q[i][colQ] * A[i][colA];
        }
        return dot;
    }

    private static double norm(double[] v) {
        double sum = 0;
        for (double val : v) {
            sum += val * val;
        }
        return Math.sqrt(sum);
    }

    private static double[][] multiply(double[][] R, double[][] Q) {
        int n = R.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += R[i][k] * Q[k][j];
                }
            }
        }
        return result;
    }

    private static double[][] copy(double[][] A) {
        int n = A.length;
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, res[i], 0, n);
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 100;
        double[][] A = new double[n][n];

        // ma tran doi xung: A[i][j] = min(i+1, j+1)
        double originalTrace = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = Math.min(i + 1, j + 1);
            }
            originalTrace += A[i][i];
        }

        long start = System.currentTimeMillis();
        double[][] Ak = runQR(A, 150);
        long end = System.currentTimeMillis();

        double calculatedTrace = 0;
        for (int i = 0; i < n; i++) {
            calculatedTrace += Ak[i][i];
        }

        System.out.println("thời gian thực thi: " + (end - start) + " ms");
        System.out.printf("sai số tuyệt đối: %.2e\n", Math.abs(calculatedTrace - originalTrace));

        System.out.println("\n10 trị riêng lớn nhất:");
        for (int i = 0; i < 10; i++) {
            System.out.printf("lambda[%d]: %.6f\n", i + 1, Ak[i][i]);
        }
    }
}