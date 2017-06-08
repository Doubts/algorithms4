package sorting;

import utils.StdIn;
import utils.StdOut;

/**
 * BinaryInsetion: binary insertion
 * Created by zhanjiahan on 17-6-8.
 */
public class BinaryInsertion {
    private BinaryInsertion() {}

    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            Comparable v = a[i];
            int lo = 0;
            int hi = i;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (less(v, a[mid])) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }

            for (int j = i; j > lo; --j) {
                a[j] = a[j--];
            }
            a[lo] = v;
        }

        assert isSorted(a);
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length);
    }
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++) {
            if (less(a[i], a[i-1])) {
                return false;
            }
        }
        return true;
    }

    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        BinaryInsertion.sort(a);
        show(a);
    }
}
