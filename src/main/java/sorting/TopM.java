package sorting;

import fundamentals.Stack;
import fundamentals.Transaction;
import utils.StdIn;
import utils.StdOut;

/**
 * TopM: priority queue client
 * Created by zhanjiahan on 17-6-12.
 */
public class TopM {
    private TopM() {}

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        MinPQ<Transaction> pq = new MinPQ<>(m + 1);

        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();
            System.out.println(line);
            Transaction transaction = new Transaction(line);
            pq.insert(transaction);

            if (pq.size() > m) {
                pq.delMin();
            }
        }

        Stack<Transaction> stack = new Stack<>();
        for (Transaction transaction : pq) {
            stack.push(transaction);
        }
        for (Transaction transaction : stack) {
            StdOut.println(transaction);
        }

    }
}
