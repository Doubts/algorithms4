package search;

import utils.In;
import utils.StdIn;
import utils.StdOut;

import java.io.File;

/**
 * FileIndex: File Index
 * Created by jhzhan on 6/14/17.
 */
public class FileIndex {
    private FileIndex() {}

    public static void main(String[] args) {
        ST<String, SET<File>> st = new ST<>();

        StdOut.println("Indexing File");

        for (String filename : args) {
            StdOut.println(" " + filename);
            File file = new File(filename);
            In in = new In(file);
            while (!in.isEmpty()) {
                String word = in.readString();
                if (!st.contains(word)) {
                    st.put(word, new SET<>());
                }
                SET<File> set =st.get(word);
                set.add(file);
            }
        }

        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if ("exit".equals(query)) {
                break;
            }
            if (st.contains(query)) {
                SET<File> set = st.get(query);
                for (File file : set) {
                    StdOut.println(" " + file.getName());
                }
            }
        }
    }
}
