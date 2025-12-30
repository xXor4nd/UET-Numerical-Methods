import java.util.function.Function;

public class SimpsonIntegral {
    public static double calculateSimpsonIntegral(
            Function<Double, Double> f, double a, double b, int n) {
        double res = 0;
        double delta = (b - a) / n;
        double deltaDivideSix = delta / 6;

        for (int i = 1; i <= n; i++) {
            double fa = f.apply(a + (i - 1) * delta);
            double fb = f.apply(a + i * delta);
            double fm = f.apply(a + i * delta - delta / 2);
            res += (fa + fb + 4 * fm);
        }

        return res * deltaDivideSix;
    }

    public static void main(String[] args) {
        int n = 1000;

        System.out.println("=== TÍCH PHÂN KHÔNG CÓ NGUYÊN HÀM SƠ CẤP ===\n");

        // 1. e^(x²) - Hàm Gaussian
        System.out.println("1. ∫ e^(x²) dx");
        System.out.println("   Từ 0 đến 1: " +
                calculateSimpsonIntegral(x -> Math.exp(x * x), 0, 1, n));
        System.out.println("   Từ 0 đến 2: " +
                calculateSimpsonIntegral(x -> Math.exp(x * x), 0, 2, n));
        System.out.println("   Liên quan đến hàm erf(x) - Error Function\n");

        // 2. e^(-x²) - Hàm phân phối chuẩn
        System.out.println("2. ∫ e^(-x²) dx");
        System.out.println("   Từ 0 đến 1: " +
                calculateSimpsonIntegral(x -> Math.exp(-x * x), 0, 1, n));
        System.out.println("   Từ -∞ đến ∞: √π ≈ 1.7724538509 (công thức Gaussian)");
        System.out.println("   Cơ sở của phân phối chuẩn trong thống kê\n");

        // 3. sin(x)/x - Hàm sinc
        System.out.println("3. ∫ sin(x)/x dx");
        System.out.println("   Từ 0 đến π: " +
                calculateSimpsonIntegral(x -> x == 0 ? 1 : Math.sin(x) / x, 0, Math.PI, n));
        System.out.println("   Từ 0 đến ∞: π/2 ≈ 1.5707963268 (Si(∞))");
        System.out.println("   Quan trọng trong xử lý tín hiệu\n");

        // 4. 1/ln(x) - Tích phân logarit
        System.out.println("4. ∫ 1/ln(x) dx");
        System.out.println("   Từ 2 đến 10: " +
                calculateSimpsonIntegral(x -> 1 / Math.log(x), 2, 10, n));
        System.out.println("   Từ 2 đến 100: " +
                calculateSimpsonIntegral(x -> 1 / Math.log(x), 2, 100, n));
        System.out.println("   Liên quan đến hàm Li(x) - Logarithmic Integral\n");

        // 5. sqrt(1 + x³)
        System.out.println("5. ∫ √(1 + x³) dx");
        System.out.println("   Từ 0 đến 1: " +
                calculateSimpsonIntegral(x -> Math.sqrt(1 + x * x * x), 0, 1, n));
        System.out.println("   Từ 0 đến 2: " +
                calculateSimpsonIntegral(x -> Math.sqrt(1 + x * x * x), 0, 2, n));
        System.out.println("   Tích phân elliptic\n");

        // 6. sin(x²) - Fresnel sine integral
        System.out.println("6. ∫ sin(x²) dx");
        System.out.println("   Từ 0 đến 1: " +
                calculateSimpsonIntegral(x -> Math.sin(x * x), 0, 1, n));
        System.out.println("   Từ 0 đến π: " +
                calculateSimpsonIntegral(x -> Math.sin(x * x), 0, Math.PI, n));
        System.out.println("   Tích phân Fresnel - dùng trong quang học\n");

        // 7. cos(x²) - Fresnel cosine integral
        System.out.println("7. ∫ cos(x²) dx");
        System.out.println("   Từ 0 đến 1: " +
                calculateSimpsonIntegral(x -> Math.cos(x * x), 0, 1, n));
        System.out.println("   Từ 0 đến 2: " +
                calculateSimpsonIntegral(x -> Math.cos(x * x), 0, 2, n));
        System.out.println("   Tích phân Fresnel - hiện tượng nhiễu xạ\n");

        // 8. e^x / x
        System.out.println("8. ∫ e^x/x dx");
        System.out.println("   Từ 1 đến 2: " +
                calculateSimpsonIntegral(x -> Math.exp(x) / x, 1, 2, n));
        System.out.println("   Từ 1 đến 5: " +
                calculateSimpsonIntegral(x -> Math.exp(x) / x, 1, 5, n));
        System.out.println("   Liên quan đến hàm Ei(x) - Exponential Integral\n");

        // 9. sqrt(sin(x))
        System.out.println("9. ∫ √(sin(x)) dx");
        System.out.println("   Từ 0 đến π/2: " +
                calculateSimpsonIntegral(x -> Math.sqrt(Math.sin(x)), 0, Math.PI / 2, n));
        System.out.println("   Từ 0 đến π: " +
                calculateSimpsonIntegral(x -> Math.sqrt(Math.sin(x)), 0, Math.PI, n));
        System.out.println("   Tích phân elliptic\n");

        // 10. 1/sqrt(1 - x⁴)
        System.out.println("10. ∫ 1/√(1 - x⁴) dx");
        System.out.println("   Từ 0 đến 0.5: " +
                calculateSimpsonIntegral(x -> 1 / Math.sqrt(1 - x * x * x * x), 0, 0.5, n));
        System.out.println("   Từ 0 đến 0.9: " +
                calculateSimpsonIntegral(x -> 1 / Math.sqrt(1 - x * x * x * x), 0, 0.9, n));
        System.out.println("   Tích phân elliptic hoàn chỉnh\n");

        // 11. x^x
        System.out.println("11. ∫ x^x dx");
        System.out.println("   Từ 0 đến 1: " +
                calculateSimpsonIntegral(x -> Math.pow(x, x), 0.001, 1, n)); // Tránh 0^0
        System.out.println("   Từ 0 đến 2: " +
                calculateSimpsonIntegral(x -> Math.pow(x, x), 0.001, 2, n));
        System.out.println("   Sophmore's dream - không có công thức đẹp\n");

        // 12. sin(sin(x))
        System.out.println("12. ∫ sin(sin(x)) dx");
        System.out.println("   Từ 0 đến π: " +
                calculateSimpsonIntegral(x -> Math.sin(Math.sin(x)), 0, Math.PI, n));
        System.out.println("   Từ 0 đến 2π: " +
                calculateSimpsonIntegral(x -> Math.sin(Math.sin(x)), 0, 2 * Math.PI, n));
        System.out.println("   Hàm Bessel\n");

        System.out.println("\n=== KIỂM TRA ĐỘ CHÍNH XÁC ===");
        System.out.println("Tính ∫ sin(sin(x)) dx với các giá trị n khác nhau:");
        for (int ni : new int[]{10, 50, 100, 500, 1000, 5000}) {
            double result = calculateSimpsonIntegral(x -> Math.sin(Math.sin(x)), 0, 1, ni);
            System.out.printf("   n = %5d: %.10f\n", ni, result);
        }
    }
}