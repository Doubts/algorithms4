package search;

import utils.StdIn;
import utils.StdOut;

/**
 * DeDup: Read in a list of words from standard input and print out each word, removing any duplicates.
 * Created by jhzhan on 6/14/17.
 */
public class DeDup {

    private DeDup() {}

    public static void main(String[] args) {
        SET<String> set = new SET<>();

        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            if (key.equals("exit")) {
                break;
            }
            if (!set.contains(key)) {
                set.add(key);
                StdOut.println(key);
            }
        }
    }
}
