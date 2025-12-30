public class MyCubicSplineInterpolation {

    public static class SplineCoefficients {
        double[] a, b, c, d, h;
        double[] x, y;

        SplineCoefficients(double[] a, double[] b, double[] c, double[] d,
                           double[] h, double[] x, double[] y) {
            this.a = a; this.b = b; this.c = c; this.d = d;
            this.h = h; this.x = x; this.y = y;
        }
    }

    // tìm index i sao cho x[i] <= t <= x[i+1]
    private static int findSegmentIndex(double[] x, double t) {
        int n = x.length;

        if (t < x[0]) return 0;
        if (t >= x[n - 1]) return n - 2;

        int low = 0;
        int high = n - 1;
        int res = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (x[mid] <= t) {
                res = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return Math.min(res, n - 2);
    }

    public static double evaluate(double t, SplineCoefficients spline) {
        int i = findSegmentIndex(spline.x, t);

        if (i >= spline.x.length - 1) {
            i = spline.x.length - 2;
        }
        if (i < 0) {
            i = 0;
        }

        double dt = t - spline.x[i];
        return spline.a[i] * dt * dt * dt +
                spline.b[i] * dt * dt +
                spline.c[i] * dt +
                spline.d[i];
    }

    // O(n) - Thomas algorithm
    public static double[] solveTridiagonal(double[] lower, double[] diag, double[] upper, double[] rhs) {
        int n = rhs.length;
        double[] cPrime = new double[n];
        double[] dPrime = new double[n];
        double[] x = new double[n];
        cPrime[0] = upper[0] / diag[0];
        dPrime[0] = rhs[0] / diag[0];
        for (int i = 1; i < n; i++) {
            double m = diag[i] - lower[i] * cPrime[i - 1];
            cPrime[i] = upper[i] / m;
            dPrime[i] = (rhs[i] - lower[i] * dPrime[i - 1]) / m;
        }
        x[n - 1] = dPrime[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            x[i] = dPrime[i] - cPrime[i] * x[i + 1];
        }
        return x;
    }

    public static SplineCoefficients computeCubicSpline(double[] x, double[] y) {
        int n = x.length - 1;
        double[] h = new double[n];
        for (int i = 0; i < n; i++) h[i] = x[i + 1] - x[i];
        int size = n + 1;
        double[] A_lower = new double[size], A_diag = new double[size], A_upper = new double[size], rhs = new double[size];
        A_diag[0] = -(h[0] + h[1]); A_upper[0] = h[0]; rhs[0] = 0;
        for (int i = 1; i < n; i++) {
            A_lower[i] = h[i] / 3.0;
            A_diag[i] = 2.0 * (h[i] + h[i - 1]) / 3.0;
            A_upper[i] = h[i - 1] / 3.0;
            rhs[i] = (y[i + 1] - y[i]) / h[i] - (y[i] - y[i - 1]) / h[i - 1];
        }
        A_lower[n] = h[n - 2]; A_diag[n] = -(h[n - 2] + h[n - 1]); rhs[n] = 0;
        double[] b = solveTridiagonal(A_lower, A_diag, A_upper, rhs);
        double[] a = new double[n], c = new double[n], d = new double[n];
        for (int i = 0; i < n; i++) {
            a[i] = (b[i + 1] - b[i]) / (3.0 * h[i]);
            c[i] = (y[i + 1] - y[i]) / h[i] - h[i] * (b[i + 1] + 2.0 * b[i]) / 3.0;
        }
        System.arraycopy(y, 0, d, 0, n);
        return new SplineCoefficients(a, b, c, d, h, x, y);
    }

    public static void main(String[] args) {
        int n = 1000;
        double startRange = 0, endRange = 10.0;
        double[] x = new double[n];
        double[] y = new double[n];

        double h = (endRange - startRange) / (n - 1);
        for (int i = 0; i < n; i++) {
            x[i] = startRange + i * h;
            y[i] = Math.sin(x[i]);
        }

        long startTime = System.nanoTime();
        SplineCoefficients spline = computeCubicSpline(x, y);
        long endTime = System.nanoTime();

        System.out.printf("%-5s | %-12s | %-20s | %-20s\n", "STT", "x_eval", "Gia tri Spline", "Sai so tuyet doi");

        double maxError = 0;
        for (int i = 0; i < n - 1; i++) {
            double xEval = x[i] + h / 2.0;
            double splineVal = evaluate(xEval, spline);
            double realVal = Math.sin(xEval);
            double error = Math.abs(splineVal - realVal);
            maxError = Math.max(maxError, error);

            if (i < 10 || i >= n - 11) {
                System.out.printf("%-5d | %-12.6f | %-20.12f | %-20.5e\n", i, xEval, splineVal, error);
            } else if (i == 10) {
                System.out.println(" ...  |     ...      |         ...          |         ...");
            }
        }

        System.out.printf("Thoi gian tinh toan: %.4f ms\n", (endTime - startTime) / 1_000_000.0);
        System.out.printf("Sai so lon nhat: %.5e\n", maxError);
    }
}