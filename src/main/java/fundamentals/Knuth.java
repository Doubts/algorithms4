package fundamentals;

import utils.StdIn;
import utils.StdOut;
import utils.StdRandom;

/**
 * Created by zhanjiahan on 17-5-31.
 * Knuth: Knuth 洗牌
 */
public class Knuth {
    private Knuth() {}

    public static void shuffle(Object[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = (int) (Math.random() * (i + 1));
            Object swap = a[i];
            a[i] = a[r];
            a[r] = swap;
        }
    }

    public static void shuffleAlternative(Object[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r =i + (int) (Math.random() * (n - i));
            Object swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Knuth.shuffle(a);
        for (String s : a) {
            StdOut.println(s);
        }
    }
}
