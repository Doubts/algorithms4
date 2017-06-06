package fundamentals;

import utils.StdIn;
import utils.StdOut;

/**
 * Created by zhanjiahan on 17-5-31.
 * Average: 序列的均值
 */
public class Average {
    private Average() {

    }

    public static void main(String[] args) {
        int count = 0;
        double sum = 0.0;
        while (!StdIn.isEmpty()) {
            double value = StdIn.readDouble();
            sum += value;
            count++;
        }
        StdOut.printf("Average is %.2f", sum / count);
    }
}