public class MyNewtonInterpolation {
    // O(n^2)
    public static double[][] computeDividedDifferences(double[] x, double[] y) {
        int n = x.length;
        double[][] table = new double[n][n];

        for (int i = 0; i < n; i++) {
            table[i][0] = y[i];
        }

        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                table[i][j] = (table[i + 1][j - 1] - table[i][j - 1]) / (x[i + j] - x[i]);
            }
        }

        return table;
    }

    public static double interpolate(double[] x, double[][] table, double xValue) {
        int n = x.length;

        double result = table[0][0];
        double term = 1.0;

        for (int i = 1; i < n; i++) {
            term *= (xValue - x[i - 1]);
            result += table[0][i] * term;

            if (Math.abs(table[0][i] * term) < 1e-15) {
                break;
            }
        }

        return result;
    }

    // O(n^2 + kn)
    public static void main(String[] args) {
        int numPoints = 50;
        double[] x = new double[numPoints];
        double[] y = new double[numPoints];

        for (int i = 0; i < numPoints; i++) {
            x[i] = i * 2.0 * Math.PI / (numPoints - 1);
            y[i] = Math.sin(x[i]);
        }

        double[][] table = computeDividedDifferences(x, y);

        double[] testPoints = {Math.PI / 2, Math.PI, 3 * Math.PI / 2, 0.1, 2 * Math.PI - 0.1, Math.PI / 4};
        String[] labels = {"π/2", "π", "3π/2", "0.1", "2π - 0.1", "π/4"};

        System.out.println("KẾT QUẢ NỘI SUY NEWTON (n = 50)");
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("%-10s | %-12s | %-12s | %-12s | %-12s%n", "Điểm (x)", "KQ Nội suy", "KQ Thực tế", "Sai số", "Thời gian (ns)");
        System.out.println("---------------------------------------------------------------------------");

        for (int i = 0; i < testPoints.length; i++) {
            double tx = testPoints[i];

            long startTime = System.nanoTime();
            double interpolatedY = interpolate(x, table, tx);
            long endTime = System.nanoTime();

            double actualY = Math.sin(tx);
            double error = Math.abs(interpolatedY - actualY);
            long duration = (endTime - startTime);

            System.out.printf("%-10s | %-12.8f | %-12.8f | %-12.2e | %-12d%n",
                    labels[i], interpolatedY, actualY, error, duration);
        }
    }
}
