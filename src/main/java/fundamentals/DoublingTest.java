package fundamentals;

import utils.StdOut;
import utils.StdRandom;

/**
 * DoublingTest: doubling test
 * Created by zhanjiahan on 17-6-8.
 */
public class DoublingTest {
    private static final int MAXMUM_INTER = 1000000;

    private DoublingTest() {}

    public static double timeTrial(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform(-MAXMUM_INTER, MAXMUM_INTER);
        }
        Stopwatch timer = new Stopwatch();
        ThreeSum.count(a);
        // fast three sum
//        ThreeSumFast.count(a)
        return timer.elapsedTime();
    }

    public static void main(String[] args) {
        for (int n = 250; true; n += n) {
            double time = timeTrial(n);
            StdOut.printf("%7d %7.1f\n", n, time);
        }
    }
}
