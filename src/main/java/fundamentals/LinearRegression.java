package fundamentals;

/**
 * LinearRegression: Linear Regression
 * Created by zhanjiahan on 17-6-8.
 */
public class LinearRegression {
    private final double intercept; // 截距
    private final double slope; // 坡度, 夹角
    private final double r2;
    private final double svar0, svar1;

    public LinearRegression(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("array lengths are not equal");
        }
        int n = x.length;

        // first pass
        double sumx = 0.0;
        double sumy = 0.0;
        double sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            sumx += x[i];
            sumx2 += x[i] * x[i];
            sumy += y[i];
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }

        slope = xybar / xxbar;
        intercept = ybar - slope * xbar;

        // more statistics analysis
        double rss = 0.0;
        double ssr = 0.0;
        for (int i = 0; i < n; i++) {
            double fit = slope * x[i] + intercept;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }

        int degreesOfFreedom = n - 2;
        r2 = ssr / yybar;
        double svar = rss / degreesOfFreedom;
        svar1 = svar / xxbar;
        svar0 = svar / n + xbar * xbar * svar1;
    }

    public double intercept() {
        return intercept;
    }

    public double slope() {
        return slope;
    }

    public double R2() {
        return r2;
    }

    public double interceptStdErr() {
        return Math.sqrt(svar0);
    }

    public double slopeStdErr() {
        return Math.sqrt(svar1);
    }

    public double predict(double x) {
        return slope * x + intercept;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%.2f n + %.2f", slope(), intercept()));
        sb.append(" (R^2 = ").append(String.format("%.3f, R2())" + R2()));
        return sb.toString();
    }
}
