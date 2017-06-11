package fundamentals;

import utils.StdOut;
import utils.StdRandom;

/**
 * DoublingRatio: doubling ratio
 * Created by zhanjiahan on 17-6-8.
 */
public class DoublingRatio {
    private static final int MAXIMUM_INTEGER = 1000000;

    private DoublingRatio() {}

    public static double timeTrial(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform(-MAXIMUM_INTEGER, MAXIMUM_INTEGER);
        }
        Stopwatch timer = new Stopwatch();
        ThreeSum.count(a);
        // fast three su,
//        ThreeSumFast.count(a);
        return timer.elapsedTime();
    }

    public static void main(String[] args) {
        double pre = timeTrial(125);
        for (int n = 250; true; n += n) {
            double time = timeTrial(n);
            StdOut.printf("%7d %7.1f %5.1f\n", n, time, time/pre);
            pre = time;
        }
    }
}
