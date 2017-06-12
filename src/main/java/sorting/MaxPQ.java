package sorting;

import utils.StdIn;
import utils.StdOut;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MaxPQ: max heap priority queue
 * Created by zhanjiahan on 17-6-12.
 */
public class MaxPQ<Key> implements Iterable<Key> {
    private Key[] pq;
    private int n;
    private Comparator comparator;

    public MaxPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity];
        n = 0;
    }
    public MaxPQ() {
        this(1);
    }

    public MaxPQ(int initCapacity, Comparator comparator) {
        pq = (Key[]) new Object[initCapacity];
        n = 0;
        this.comparator = comparator;
    }
    public MaxPQ(Comparator comparator) {
        this(1, comparator);
    }

    public MaxPQ(Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Object[n + 1];
        for (int i = 0; i < n; i++) {
            pq[i + 1] = keys[i];
        }
        for (int i = n / 2; i >= 1; i--) {
            sink(i);
        }

        assert isMaxHeap();
    }

    public boolean isEmpty() {
        return n == 0;
    }
    public int size() {
        return n;
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return pq[1];
    }
    public void resize(int capacity) {
        assert capacity > n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public void insert(Key x) {
        if (n == pq.length) {
            resize(2 * pq.length);
        }
        pq[++n] = x;
        swim(n);

        assert isMaxHeap();
    }
    public Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Key max = pq[1];
        exch(1, n);
        sink(1);
        pq[n--] = null;
        if (n > 0 && n <= pq.length / 2) {
            resize(pq.length / 2);
        }

        assert isMaxHeap();
        return max;
    }

    /**
     * Helper functions to restore the heap invariant
     */
    private void sink(int k) {
        while (2 * k <= n) {
            int i = k;
            int j = k * 2;
            if (j <= n && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }
    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    /**
     * Helper functions to compares and swap
     */
    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
        } else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }
    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }
    private boolean isMaxHeap(int k) {
        while (k > n) return true;
        int left = k * 2;
        int right = k * 2 + 1;
        if (left <= n && greater(left, k)) return false;
        if (right <= n && greater(right, k)) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }


    @Override
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }
    private class HeapIterator implements Iterator<Key> {
        private MaxPQ<Key> copy;

        public HeapIterator() {
            if (comparator == null) {
                copy = new MaxPQ<>(size());
            } else {
                copy = new MaxPQ<>(size(), comparator);
            }
            for (int i = 1; i <= n; i++) {
                copy.insert(pq[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        @Override
        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.delMax();
        }
    }

    public static void main(String[] args) {
        MaxPQ<String> pq = new MaxPQ<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                pq.insert(item);
            } else if (!pq.isEmpty()) {
                StdOut.println(pq.delMax() + " ");
            }
        }

        StdOut.println("(" + pq.size() + " left on pq)");
    }


}
