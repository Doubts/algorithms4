package fundamentals;

import java.util.Arrays;

/**
 * Created by zhanjiahan on 17-5-31.
 * StaticsSETofInts : 整数集合,排序,二分查找
 */
public class StaticsSETofInts {
    private int[] a;

    public StaticsSETofInts(int[] keys) {
        a = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            a[i] = keys[i];
        }
        Arrays.sort(a);

        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == a[i + 1]) {
                throw new IllegalArgumentException("Argument array contains ");
            }
        }
    }

    public boolean contain(int key) {
        return rank(key) != -1;
    }

    private int rank(int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] > key) {
                hi = mid - 1;
            } else if (a[mid] < key) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }


}
