package search;

import utils.In;
import utils.StdOut;

/**
 * WhiteFilter: Whiter Filter
 * Created by jhzhan on 6/14/17.
 */
public class WhiteFilter {
    private WhiteFilter() {}

    public static void main(String[] args) {
        SET<String> set = new SET<>();

        In in = new In(args[0]);
        while (!in.isEmpty()) {
            String word = in.readString();
//            if (word.equals("exit")) {
//                break;
//            }
            if (set.contains(word)) {
                StdOut.println(word);
            }
        }
    }
}
