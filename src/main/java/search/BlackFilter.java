package search;

import utils.In;
import utils.StdIn;
import utils.StdOut;

/**
 * BlackFilter: Black Filter
 * Created by jhzhan on 6/14/17.
 */
public class BlackFilter {
    private BlackFilter() {}

    public static void manin(String[] args) {
        SET<String> set = new SET<>();

        In in = new In(args[0]);
        while (!in.isEmpty()) {
            String word = in.readString();
            set.add(word);
        }

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if ("exit".equals(word)) {
                break;
            }
            if (!set.contains(word)) {
                StdOut.println(word);
            }
        }
    }
}
