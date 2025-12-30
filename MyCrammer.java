public class MyCrammer {
    public static double[] solveCramer(double[][] A, double[] b) {
        int n = b.length;
        double[] res = new double[n];
        double detA = calculateDet(A);

        if (Math.abs(detA) < 1e-10) {
            System.err.println("He vo nghiem hoac vo so nghiem");
            return null;
        }

        // O(n^4)
        for (int i = 0; i < n; i++) {
            double[][] Ai = replaceColumn(A, b, i);
            double detAi = calculateDet(Ai);
            res[i] = detAi / detA;
        }

        return res;
    }

    private static double[][] replaceColumn(double[][] A, double[] b, int colToReplace) {
        int n = A.length;
        double[][] res = new double[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (j == colToReplace) {
                    res[i][j] = b[i];
                } else {
                    res[i][j] = A[i][j];
                }
            }
        }

        return res;
    }

    // O(n^3)
    private static double calculateDet(double[][] A) {
        int n = A.length;
        double[][] temp = new double[n][n];

        for (int i = 0; i < n; i++)
            System.arraycopy(A[i], 0, temp[i], 0, n);

        double det = 1.0;

        for (int i = 0; i < n; i++) {
            int pivot = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(temp[j][i]) > Math.abs(temp[pivot][i])) {
                    pivot = j;
                }
            }

            if (pivot != i) {
                double[] swap = temp[i];
                temp[i] = temp[pivot];
                temp[pivot] = swap;
                det *= -1;
            }

            if (Math.abs(temp[i][i]) < 1e-12) return 0;

            det *= temp[i][i];

            for (int j = i + 1; j < n; j++) {
                double factor = temp[j][i] / temp[i][i];
                for (int k = i; k < n; k++) {
                    temp[j][k] -= factor * temp[i][k];
                }
            }
        }
        return det;
    }

//    private static double[][] getSubMatrix(double[][] A, int rowToDelete, int colToDelete) {
//        int n = A.length;
//        double[][] res = new double[n - 1][n - 1];
//
//        int newRow = 0;
//        for (int i = 0; i < n; ++i) {
//            if (i == rowToDelete) {
//                continue;
//            }
//            int newCol = 0;
//            for (int j = 0; j < n; ++j) {
//                if (j == colToDelete) {
//                    continue;
//                }
//                res[newRow][newCol] = A[i][j];
//                newCol++;
//            }
//            newRow++;
//        }
//        return res;
//    }

    public static void main(String[] args) {
        int n = 30;
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
        double[] x = solveCramer(A, b);
        long end = System.nanoTime();
        double timeMs = (end - start) / 1e6;

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
