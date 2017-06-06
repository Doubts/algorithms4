package fundamentals;

import utils.In;
import utils.StdIn;
import utils.StdOut;

/**
 * Created by zhanjiahan on 17-5-31.
 * WhiteList : 读取整数数组,转化为排序好的集合
 */
public class WhiteList {
    private WhiteList() {}

    public static void main(String[] args) {
        In in = new In(args[0]);
        int[] whiteList = in.readAllInts();

        StaticsSETofInts staticsSETofInts = new StaticsSETofInts(whiteList);

        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (!staticsSETofInts.contain(key)) {
                StdOut.println(key);
            }
        }
    }
}
