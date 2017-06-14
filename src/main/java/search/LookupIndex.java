package search;

import fundamentals.Queue;
import utils.In;
import utils.StdIn;
import utils.StdOut;

/**
 * LookupIndex: Lookup Index
 * Created by jhzhan on 6/14/17.
 */
public class LookupIndex {
    private LookupIndex() {}

    public static void main(String[] args) {
        String filename = args[0];
        String separator = args[1];
        In in = new In(filename);

        ST<String, Queue<String>> st = new ST<>();
        ST<String, Queue<String>> ts = new ST<>();

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(separator);
            String key = fields[0];
            for (int i = 1; i < fields.length; i++) {
                String val = fields[i];
                if (!st.contains(key)) {
                    st.put(key, new Queue<String>());
                }
                if (!ts.contains(key)) {
                    ts.put(val, new Queue<>());
                }
                st.get(key).enqueue(val);
                ts.get(val).enqueue(key);
            }
        }

        StdOut.println("Done indexing");

        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if ("exit".equals(query)) {
                break;
            }
            if (st.contains(query)) {
                for (String val : st.get(query)) {
                    StdOut.println(" " + val);
                }
            }
            if (ts.contains(query)) {
                for (String keys : ts.get(query)) {
                    StdOut.println(" " + keys);
                }
            }
        }
    }
}
