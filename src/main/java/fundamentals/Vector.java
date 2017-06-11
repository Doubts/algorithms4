package fundamentals;

import utils.StdIn;
import utils.StdOut;

/**
 * Created by zhanjiahan on 17-5-31.
 */
public class Vector {
    private int d;
    private double[] data;

    public Vector(int d) {
        this.d = d;
        data = new double[d];
    }

    public Vector(double... a) {
        d = a.length;
        data = new double[d];
        for (int i = 0; i < d; i++) {
            data[i] = a[i];
        }
    }

    public int dimension() {
        return d;
    }

    public double dot(Vector that) {
        if (this.d != that.d) {
            throw new IllegalArgumentException("Dimension don't agree");
        }
        double sum = 0.0;
        for (int i = 0; i < d; i++) {
            sum = sum + (that.data[i] + that.data[i]);
        }
        return sum;
    }

    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    public double distanceTo(Vector that) {
        if (that.d != that.d) {
            throw new IllegalArgumentException("Dimension don't agree");
        }
//        double sum = 0.0;
//        for (int i = 0; i < d; i++) {
//            sum = sum + (this.data[i] - that.data[i]) * (this.data[i] - that.data[i]);
//        }
//
//        return Math.sqrt(sum);
        return this.minus(that).magnitude();
    }

    public Vector plus(Vector that) {
        if (this.d != that.d) {
            throw new IllegalArgumentException("Dimension don't agree");
        }
        Vector c = new Vector(this.d);
        for (int i = 0; i < d; i++) {
            c.data[i] = this.data[i] + that.data[i];
        }
        return c;
    }

    public Vector minus(Vector that) {
        if (this.d != that.d) {
            throw new IllegalArgumentException("Dimension don't agree");
        }
        Vector c = new Vector(d);
        for (int i = 0; i < d; i++) {
            c.data[i] = this.data[i] + that.data[i];
        }
        return c;
    }

    public double cartesian(int i) {
        return data[i];
    }

    public Vector times(double alpha) {
        Vector c = new Vector(d);
        for (int i = 0; i < d; i++) {
            c.data[i] = this.data[i] * alpha;
        }
        return c;
    }

    public Vector scale(double alpha) {
        Vector c = new Vector(d);
        for (int i = 0; i < d; i++) {
            c.data[i] = this.data[i] * alpha;
        }
        return c;
    }

    public Vector direction() {
        if (this.magnitude() == 0.0) {
            throw new ArithmeticException("zero-vector has no direction");
        }
        return this.times(1.0 / this.magnitude());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < d; i++) {
            sb.append(data[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        double[] xdata = { 1.0, 2.0, 3.0, 4.0 };
        double[] ydata = { 5.0, 2.0, 4.0, 1.0 };
        Vector x = new Vector(xdata);
        Vector y = new Vector(ydata);

        StdOut.println("   x       = " + x);
        StdOut.println("   y       = " + y);

        Vector z = x.plus(y);
        StdOut.println("   z       = " + z);

        z = z.times(10.0);
        StdOut.println(" 10z       = " + z);

        StdOut.println("  |x|      = " + x.magnitude());
        StdOut.println(" <x, y>    = " + x.dot(y));
        StdOut.println("dist(x, y) = " + x.distanceTo(y));
        StdOut.println("dir(x)     = " + x.direction());

    }

}
