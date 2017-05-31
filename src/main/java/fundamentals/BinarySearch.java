package fundamentals;

import utils.In;
import utils.StdArrayIO;
import utils.StdIn;
import utils.StdOut;

import java.util.Arrays;

/**
 * Created by zhanjiahan on 17-5-31.
 * Binary Search
 */
public class BinarySearch {
    public static int indexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] < key) {
                lo = mid + 1;
            } else if (a[mid] > key) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        int[] a = StdArrayIO.readInt1D();
//        Arrays.sort(a);
//        StdOut.println(indexOf(a, 3));

        // read the integers from a file
        In in = new In(args[0]);
        int[] whiteList = in.readAllInts();

        Arrays.sort(whiteList);
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (BinarySearch.indexOf(whiteList, key) == -1) {
                StdOut.println(key);
            }
        }
    }
}
