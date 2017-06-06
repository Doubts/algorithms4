package fundamentals;

import utils.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Transaction: transaction
 * Created by zhanjiahan on 17-6-6.
 */
public final class Transaction implements Comparable<Transaction> {
    private final String who;
    private final Date when;
    private final double amount;

    public Transaction(String who, Date when, double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("Amount can't be NaN or Infinite");
        }
        this.who = who;
        this.when = when;
        this.amount = amount;
    }

    public Transaction(String transaction) {
        String[] a = transaction.split("\\s+");
        who = a[0];
        when = new Date(a[1]);
        amount = Double.parseDouble(a[2]);
        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("Amount can't be NaN or Infinite");
        }
    }

    public String who() {
        return who;
    }
    public Date when() {
        return when;
    }
    public double amount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%-10s %10s %8.2f", who, when, amount);
    }

    @Override
    public int compareTo(Transaction that) {
        return Double.compare(this.amount, that.amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        Transaction that = (Transaction) obj;
        return (this.who.equals(that.who)) && (this.when.equals(that.when)) && (this.amount == that.amount);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + who.hashCode();
        hash = 31 * hash + when.hashCode();
        hash = 31 * hash + ((Double) amount).hashCode();
        return hash;
    }

    public static class WhoOrder implements Comparator<Transaction> {
        @Override
        public int compare(Transaction v, Transaction w) {
            return v.who.compareTo(w.who);
        }
    }

    public static class WhenOrder implements Comparator<Transaction> {
        @Override
        public int compare(Transaction v, Transaction w) {
            return v.when.compareTo(w.when);
        }
    }

    public static class HowMuchOrder implements Comparator<Transaction> {
        @Override
        public int compare(Transaction v, Transaction w) {
            return Double.compare(v.amount, w.amount);
        }
    }

    private static void printArray(Transaction[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        Transaction[] tr = new Transaction[4];
        tr[0] = new Transaction("Turing  6/17/1990  644.80");
        tr[1] = new Transaction("Tarjan 3/26/2002 4121.85");
        tr[2] = new Transaction("Knuth    6/24/1999 288.34");
        tr[3] = new Transaction("zhanjiahan 6/6/2017 98.34");

        StdOut.println("Unordered");
        for (int i = 0; i < tr.length; i++) {
            StdOut.println(tr[i]);
        }
        StdOut.println();

        StdOut.println("Sorted by date");
        Arrays.sort(tr, new Transaction.WhenOrder());
        for (int i = 0; i < tr.length; i++) {
            StdOut.println(tr[i]);
        }
        StdOut.println();

        StdOut.println("Sorted by name");
        Arrays.sort(tr, new Transaction.WhoOrder());
        printArray(tr);


        StdOut.println("Sorted by amount");
        Arrays.sort(tr, new Transaction.HowMuchOrder());
        printArray(tr);
    }
}
