package fundamentals;

import utils.StdOut;
import utils.StdRandom;

/**
 * Created by zhanjiahan on 17-5-31.
 * RandomSeq: 生成随机序列
 */
public class RandomSeq {
    private RandomSeq() {
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        if (args.length == 1) {
            for (int i = 0; i < n; i++) {
                double random = StdRandom.uniform();
                StdOut.println(random);
            }
        } else if (args.length == 3) {
            for (int i = 0; i < n; i++) {
                double lo = Double.parseDouble(args[1]);
                double hi = Double.parseDouble(args[2]);
                double random = StdRandom.uniform(lo, hi);
                StdOut.printf("%.2f\n", random);
            }
        } else {
            throw new IllegalArgumentException("Invalid number of argument");
        }
    }
}
