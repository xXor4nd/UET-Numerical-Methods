public class MyLagrangeInterpolation {
    public static double interpolate(double[] x, double[] y, double xValue) {
        int n = x.length;
        double res = 0.0;

        // O(n^2)
        for (int k = 0; k < n; k++) {
            double term = y[k];
            for (int j = 0; j < n; j++) {
                if (k != j) {
                    term = term * (xValue - x[j]) / (x[k] - x[j]);
                }
            }
            res += term;
        }
        return res;
    }

    // O(kn^2)
    public static void main(String[] args) {
        int numPoints = 50;
        double[] x = new double[numPoints];
        double[] y = new double[numPoints];

        for (int i = 0; i < numPoints; i++) {
            x[i] = i * 2.0 * Math.PI / (numPoints - 1);
            y[i] = Math.sin(x[i]);
        }

        double[] testPoints = {Math.PI / 2, Math.PI, 3 * Math.PI / 2, 0.1, 2 * Math.PI - 0.1, Math.PI / 4};
        String[] labels = {"π/2", "π", "3π/2", "0.1", "2π - 0.1", "π/4"};

        System.out.println("KẾT QUẢ NỘI SUY LAGRANGE (n = 50)");
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("%-10s | %-12s | %-12s | %-12s | %-12s%n", "Điểm (x)", "KQ Nội suy", "KQ Thực tế", "Sai số", "Thời gian (ns)");
        System.out.println("---------------------------------------------------------------------------");

        for (int i = 0; i < testPoints.length; i++) {
            double tx = testPoints[i];

            long startTime = System.nanoTime();
            double interpolatedY = interpolate(x, y, tx);
            long endTime = System.nanoTime();

            double actualY = Math.sin(tx);
            double error = Math.abs(interpolatedY - actualY);
            long duration = (endTime - startTime);

            System.out.printf("%-10s | %-12.8f | %-12.8f | %-12.2e | %-12d%n",
                    labels[i], interpolatedY, actualY, error, duration);
        }
    }
}