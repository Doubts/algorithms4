package sorting;

import utils.StdIn;
import utils.StdOut;

/**
 * InsertionX: insertion x
 * Created by zhanjiahan on 17-6-8.
 */
public class InsertionX {
    private InsertionX() {}

    public static void sort(Comparable[] a) {
        int exchange = 0;
        int n = a.length;
        for (int i = n - 1; i > 0; i--) {
            if (less(a[i], a[i-1])) {
                exch(a, i, i-1);
                exchange++;
            }
        }
        if (exchange == 0) return;

        for (int i = 2; i < n; i++) {
            Comparable v = a[i];
            int j = i;
            while (less(v, a[j-1])) {
                a[j] = a[j-1];
                j--;
            }
            a[j] = v;
        }
        assert isSorted(a);
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length);
    }
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
//        String[] a = StdIn.readAllStrings();
        String[] a = {"nice", "zhan", "jia", "han", "CUMTB", "come"};
        InsertionX.sort(a);
        show(a);
    }
}