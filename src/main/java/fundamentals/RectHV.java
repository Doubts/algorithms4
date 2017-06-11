package fundamentals;

import utils.StdDraw;

/**
 * RectHV: Rectangle
 * Created by zhanjiahan on 17-6-7.
 */
public final class RectHV {
    private final double xmin, ymin;
    private final double xmax, ymax;

    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        if (Double.isNaN(xmin) || Double.isNaN(xmax)) {
            throw new IllegalArgumentException("x-coordinate cannot be NaN");
        }
        if (Double.isNaN(ymin) || Double.isNaN(ymax)) {
            throw new IllegalArgumentException("y-coordinate cannot be NaN");
        }
        if (xmin > xmax || ymin > ymax) {
            throw new IllegalArgumentException("Invalid rectangle");
        }
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    public double xmin() {
        return xmin;
    }
    public double ymin() {
        return ymin;
    }
    public double xmax() {
        return xmax;
    }
    public double ymax() {
        return ymax;
    }

    public double width() {
        return xmax - xmin;
    }
    public double height() {
        return ymax - ymin;
    }

    public boolean intersects(RectHV that) {
        return (this.xmin <= that.xmin && this.xmax >= that.xmax && this.ymin <= that.ymin && this.ymax >= that.ymax);
    }

    public boolean contains(Point2D p) {
        return (p.x() >= this.xmin) && (p.x() <= this.xmax) &&
                (p.y() >= this.ymin) && (p.x() <= this.ymax);
    }

    public double distanceTo(Point2D p) {
        return Math.sqrt(this.distanceSquaredTo(p));
    }

    public double distanceSquaredTo(Point2D p) {
        double dx = 0.0;
        double dy = 0.0;
        if (p.x() < xmin) dx = p.x() - xmin;
        else if (p.x() > xmax) dx = xmax - p.x();
        if (p.y() < ymin) dy = p.y() - ymin;
        else if (p.y() > ymax) dy = ymax - p.y();
        return dx*dx + dy*dy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        RectHV that = (RectHV) obj;
        if (this.xmin != that.xmin) return false;
        if (this.ymin != that.ymin) return false;
        if (this.xmax != that.xmax) return false;
        if (this.ymax != that.ymax) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash1 = ((Double) xmin).hashCode();
        int hash2 = ((Double) ymin).hashCode();
        int hash3 = ((Double) xmax).hashCode();
        int hash4 = ((Double) ymax).hashCode();
        return 31*(31*(31*hash1 + hash2) + hash3) + hash4;
    }

    @Override
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }

    public void draw() {
        StdDraw.line(xmin, ymin, xmax, ymin);
        StdDraw.line(xmin, ymin, xmin, ymax);
        StdDraw.line(xmin, ymax, xmax, ymax);
        StdDraw.line(xmax, ymax, xmax, ymin);
    }


}
