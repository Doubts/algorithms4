package fundamentals;

import utils.In;
import utils.StdOut;

import java.util.Arrays;

/**
 * ThreeSumFast: three sum fast
 * Created by zhanjiahan on 17-6-8.
 */
public class ThreeSumFast {
    private ThreeSumFast() {}

    private static boolean containsDuplicates(int[] a) { // a sorted
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == a[i++]) {
                return true;
            }
        }
        return false;
    }

    private static void printAll(int[] a) {
        int n = a.length;
        Arrays.sort(a);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int k = Arrays.binarySearch(a, -(a[i] + a[j]));
                if (k > j) {
                    StdOut.println(a[i] + " " + a[j] + " " + a[k]);
                }
            }
        }
    }

    private static int count(int[] a) {
        int n = a.length;
        int count = 0;
        Arrays.sort(a);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int k = Arrays.binarySearch(a, -(a[i] + a[j]));
                if (k > j) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
//        In in = new In(args[0]);
//        int[] a = in.readAllInts();
        int[] a = {5, 6, 89, 3, -4, 1, 7, -3, 0};
        int count = count(a);
        StdOut.println(count);
        printAll(a);
    }
}
