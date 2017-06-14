package search;

import utils.In;
import utils.StdIn;
import utils.StdOut;

/**
 * LookupCSV: Lookup csv
 * Created by jhzhan on 6/14/17.
 */
public class LookupCSV {
    private LookupCSV() {}

    public static void main(String[] args) {
        int keyField = Integer.parseInt(args[1]);
        int valField = Integer.parseInt(args[2]);

        ST<String, String> st = new ST<>();

        In in = new In(args[0]);
        while(in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            String key = tokens[keyField];
            String val = tokens[valField];
            st.put(key, val);
        }

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if ("exit".equals(s)) {
                break;
            }
            if (st.contains(s)) {
                StdOut.println(st.get(s));
            } else {
                StdOut.println(s + ": Not Found");
            }
        }
    }
}
