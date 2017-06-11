package fundamentals;

import utils.StdDraw;
import utils.StdIn;
import utils.StdOut;
import utils.StdRandom;

/**
 * Interval2D: Interval2D
 * Created by zhanjiahan on 17-6-7.
 */
public final class Interval2D {

    private final Interval1D x;
    private final Interval1D y;

    public Interval2D(Interval1D x, Interval1D y) {
        this.x = x;
        this.y = y;
    }

    public boolean intersects(Interval2D that) {
        if (!this.x.intersects(that.x)) return false;
        if (!this.y.intersects(that.y)) return false;
        return true;
    }

    public boolean contain(Point2D p) {
//        if (!this.x.contains(p.x())) return false;
//        if (!this.y.contains(p.y())) return false;
//        return true;
        return this.x.contains(p.x()) && this.y.contains(p.y());
    }

    public double area() {
        return x.length() * y.length();
    }

    public String toString() {
        return x + " x " + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        Interval2D that = (Interval2D) obj;
        return this.x.equals(that.x) && this.y.equals(that.y);
    }

    @Override
    public int hashCode() {
        int hash1 = x.hashCode();
        int hash2 = y.hashCode();
        return 31 * hash1 +hash2;
    }

    public void draw() {
        double xc = (x.min() + x.max()) / 2.0;
        double yc = (y.min() + y.max()) / 2.0;
        StdDraw.rectangle(xc, yc, x.length() / 2.0, y.length() / 2.0);
    }

    public static void main(String[] args) {
//        double xmin = Double.parseDouble(args[0]);
//        double xmax = Double.parseDouble(args[1]);
//        double ymin = Double.parseDouble(args[2]);
//        double ymax = Double.parseDouble(args[3]);
//        int trials = Integer.parseInt(args[4]);

        double xmin = 50.0;
        double xmax = 50.0;
        double ymin = 50.0;
        double ymax = 50.0;
        int trials = 60;

        Interval1D xInterval = new Interval1D(xmin, xmax);
        Interval1D yInterval = new Interval1D(ymin, ymax);
        Interval2D box = new Interval2D(xInterval, yInterval);
        box.draw();


        Counter counter = new Counter("hits");
        for (int t = 0; t < trials; t++) {
            double x = StdRandom.uniform(0.0, 100.0);
            double y = StdRandom.uniform(0.0, 100.0);
            Point2D p2 = new Point2D(x, y);

            if (box.contain(p2)) {
                counter.increment();
            } else {
                box.draw();
            }
        }
        StdOut.println(counter);
        StdOut.printf("box area = %.2f\n", box.area());
    }

}
