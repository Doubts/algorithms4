package sorting;

import utils.In;
import utils.StdIn;
import utils.StdOut;

/**
 * Multiway: Multiway merge
 * Created by zhanjiahan on 17-6-12.
 */
public class Multiway {
    private Multiway() {}

    private static void merge(In[] streams) {
        int n = streams.length;
        IndexMinPQ<String> pq = new IndexMinPQ<>(n);
        for (int i = 0; i < n; i++) {
            if (!streams[i].isEmpty()) {
                pq.insert(i, streams[i].readString());
            }
        }

        while (!pq.isEmpty()) {
            StdOut.print(pq.delMin() + " ");
            int i = pq.delMin();
            if (!streams[i].isEmpty()) {
                pq.insert(i, streams[i].readString());
            }
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        int n = args.length;
        In[] streams = new In[n];
        for (int i = 0; i < n; i++) {
            streams[i] = new In(args[i]);
        }
        merge(streams);
    }
}
