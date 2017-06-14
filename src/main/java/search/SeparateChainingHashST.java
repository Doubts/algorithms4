package search;

import fundamentals.Queue;
import utils.StdIn;
import utils.StdOut;

/**
 * SepatateChainingHashST: Separate Chaining Hash ST
 * Created by jhzhan on 6/14/17.
 */
public class SeparateChainingHashST<Key, Value>{
    private static final int INIT_CAPACITY = 4;

    private int n; // number of key-value pairs
    private int m; // hash table size
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    public SeparateChainingHashST(int m) {
        this.m = m;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
        for (int i = 0; i < m; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<>();
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m = temp.m;
        this.n = temp.n;
        this.st = temp.st;
    }

    private int hash(Key key) {
        return ((key.hashCode()) & 0x7fffffff) % m;
    }

    private int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called contains() with a null key");
        }
        return get(key) != null;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called get() with a null key");
        }
        int i = hash(key);
        return st[i].get(key);
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (value == null) {
            delete(key);
            return;
        }
        if (n >= 10 * m) {
            resize(2 * m);
        }

        int i = hash(key);
        if (!contains(key)) {
            n++;
        }
        st[i].put(key, value);
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        int i = hash(key);
        if (st[i].contains(key)) {
            n--;
        }
        st[i].delete(key);

        if (m > INIT_CAPACITY && n <= 2 * m) {
            resize(m / 2);
        }
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }

    public static void main(String[] args) {
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            if ("exit".equals(key)) {
                break;
            }
            st.put(key, i);
        }

        for (String key : st.keys()) {
            StdOut.println(key + " " + st.get(key));
        }
    }


}
