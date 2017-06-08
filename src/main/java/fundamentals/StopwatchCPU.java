package fundamentals;

import utils.StdOut;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * StopwatchCPU: stop watch cpu
 * Created by zhanjiahan on 17-6-8.
 */
public class StopwatchCPU {
    private static final double NANOSECOND_PER_SECOND = 1000000000;

    private final ThreadMXBean threadTimer;
    private final long start;

    public StopwatchCPU() {
        threadTimer = ManagementFactory.getThreadMXBean();
        start = threadTimer.getCurrentThreadCpuTime();
    }

    public double elapsedTime() {
        long now = threadTimer.getCurrentThreadCpuTime();
        return (now - start) / NANOSECOND_PER_SECOND;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StopwatchCPU timer1 = new StopwatchCPU();
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum += Math.sqrt(i);
        }
        double time1 = timer1.elapsedTime();
        StdOut.printf("%e (%.2f seconds)\n", sum, time1);

        StopwatchCPU timer2 = new StopwatchCPU();
        double sum2 = 0.0;
        for (int i = 0; i < n; i++) {
            sum2 += Math.sqrt(i);
        }
        double time2 = timer1.elapsedTime();
        StdOut.printf("%e (%.2f seconds)", sum2, time2);
    }

}
