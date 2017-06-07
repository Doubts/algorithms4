package fundamentals;

import utils.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Interval1D: Interval1D
 * Created by zhanjiahan on 17-6-7.
 */
public final class Interval1D {
    public static final Comparator<Interval1D> MIN_ENDPOINT_ORDER = new MinEndpointComparator();
    public static final Comparator<Interval1D> MAX_ENDPOINT_ORDER = new MaxEndpointComparator();
    public static final Comparator<Interval1D> LENGTH_ORDER = new LengthComparator();

    private final double min;
    private final double max;

    public Interval1D(double min, double max) {
        if (Double.isNaN(min) || Double.isNaN(max)) {
            throw new IllegalArgumentException("Endpoint cannot be NaN");
        }
        if (Double.isInfinite(min) || Double.isInfinite(max)) {
            throw new IllegalArgumentException("Endpoint must be infinite");
        }
        if (min > max) {
            throw new IllegalArgumentException("Invalid endpoint");
        }

        if (min == 0.0) this.min = 0.0;
        else this.min = min;
        if (max == 0.0) this.max = 0.0;
        else this.max = max;
    }

    public double min() {
        return min;
    }
    public double max() {
        return max;
    }

    public boolean intersects(Interval1D that) {
        if (this.max < that.min) return false;
        if (that.max < this.min) return false;
        return true;
    }

    public boolean contains(double x) {
        return (x >= min) && (x <= max);
    }

    public double length() {
        return max - min;
    }

    @Override
    public String toString() {
        return "[" + min + ", " + max + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        Interval1D that = (Interval1D) obj;
        return (this.min == that.min) && (this.max == that.max);
    }

    @Override
    public int hashCode() {
        int hash1 = ((Double) min).hashCode();
        int hash2 = ((Double) max).hashCode();
        return 31 * hash1 + hash2;
    }


    private static class MinEndpointComparator implements Comparator<Interval1D> {
        @Override
        public int compare(Interval1D a, Interval1D b) {
            if (a.min < b.min) return -1;
            else if (a.min > b.min) return 1;
            else if (a.max < b.max) return -1;
            else if (a.max > b.max) return 1;
            else return 0;
        }
    }

    private static class MaxEndpointComparator implements Comparator<Interval1D> {
        @Override
        public int compare(Interval1D a, Interval1D b) {
            if (a.max < b.max) return -1;
            else if (a.max > b.max) return 1;
            else if (a.min < b.min) return -1;
            else if (a.min > b.min) return 1;
            else return 0;
        }
    }

    private static class LengthComparator implements Comparator<Interval1D> {
        @Override
        public int compare(Interval1D a, Interval1D b) {
            if (a.length() < b.length()) return -1;
            else if (a.length() > b.length()) return 1;
            else return 0;
        }
    }

    private static void printInterval1D(Interval1D[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        Interval1D[] interval1DS = new Interval1D[4];
        interval1DS[0] = new Interval1D(15.0, 33.0);
        interval1DS[1] = new Interval1D(45.0, 60.0);
        interval1DS[2] = new Interval1D(20.0, 70.0);
        interval1DS[3] = new Interval1D(46.0, 55.0);

        StdOut.println("Unsorted");
        printInterval1D(interval1DS);

        StdOut.println("Min endpoint order");
        Arrays.sort(interval1DS, MIN_ENDPOINT_ORDER);
        printInterval1D(interval1DS);

        StdOut.println("Max endpoint order");
        Arrays.sort(interval1DS, MAX_ENDPOINT_ORDER);
        printInterval1D(interval1DS);

        StdOut.println("Length order");
        Arrays.sort(interval1DS, LENGTH_ORDER);
        printInterval1D(interval1DS);
    }

}
