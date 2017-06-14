package search;

import utils.StdIn;
import utils.StdOut;

/**
 * SparseVector: a sparse vector, implementing using a symbol table
 * Created by jhzhan on 6/14/17.
 */
public class SparseVector {
    private int d; // dimension
    private ST<Integer, Double> st; // the vector, represented by index-value pairs

    public SparseVector(int d) {
        this.d = d;
        st = new ST<>();
    }

    public void put(int i, double value) {
        if (i < 0 || i > d) {
            throw new IllegalArgumentException("Illegal Index");
        }
        if (value == 0.0) {
            st.delete(i);
        } else {
            st.put(i, value);
        }
    }

    public Double get(int i) {
        if (i < 0 || i > d) {
            throw new IllegalArgumentException("Illegal Index");
        }
        if (st.contains(i)) {
            return st.get(i);
        } else {
            return 0.0;
        }
    }

    public int nnz() {
        return st.size();
    }

    public int dimension() {
        return d;
    }

    public double dot(SparseVector that) {
        if (this.d != that.d) {
            throw new IllegalArgumentException("Vector lengths disagree");
        }

        double sum = 0.0;
        if (this.st.size() <= that.st.size()) {
            for (int i : this.st.keys()) {
                if (that.st.contains(i)) {
                    sum += that.get(i) * that.get(i);
                }
            }
        } else {
            for (int i : that.st.keys()) {
                if (this.st.contains(i)) {
                    sum += that.get(i) * this.get(i);
                }
            }
        }

        return sum;
    }

    public double dot(double[] that) {
        double sum = 0.0;
        for (int i : this.st.keys()) {
            sum += this.get(i) * that[i];
        }
        return sum;
    }

    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    public SparseVector scale(double alpha) {
        SparseVector c = new SparseVector(d);
        for (int i : this.st.keys()) {
            c.put(i, alpha * this.get(i));
        }
        return c;
    }

    public SparseVector plus(SparseVector that) {
        if (this.d != that.d) {
            throw new IllegalArgumentException("Vector lengths disagree");
        }
        SparseVector c = new SparseVector(d);
        for (int i : this.st.keys()) {
            c.put(i, this.get(i));
        }
        for (int i : that.st.keys()) {
            c.put(i, c.get(i) + that.get(i));
        }

        return c;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : st.keys()) {
            sb.append("(").append(i).append(", ").append(st.get(i)).append(") ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SparseVector a = new SparseVector(10);
        SparseVector b = new SparseVector(10);

        a.put(3, 0.50);
        a.put(9, 0.75);
        a.put(6, 0.11);
        a.put(6, 0.00);
        b.put(3, 0.60);
        b.put(4, 0.90);

        StdOut.println("a = " + a);
        StdOut.println("b = " + b);
        StdOut.println("a dot b = " + a.dot(b));
        StdOut.println("a + b = " + a.plus(b));
    }
}
