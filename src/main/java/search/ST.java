package search;

import utils.StdIn;
import utils.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Symbol Table: 符号表
 * Created by zhanjiahan on 17-6-13.
 */
public class ST<Key extends Comparable<Key>, Value> implements Iterable<Key> {

    private TreeMap<Key, Value> st;

    public ST() {
        st = new TreeMap<>();
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called get() with null key");
        }
        return st.get(key);
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("called put() with null key");
        }
        if (value == null) {
            st.remove(key);
        } else {
            st.put(key, value);
        }
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called delete() with null key");
        }
        st.remove(key);
    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called contains() with null key");
        }
        return st.containsKey(key);
    }

    public int size() {
        return st.size();
    }

    public boolean isEmpty() {
        return st.size() == 0;
    }

    public Iterable<Key> keys() {
        return st.keySet();
    }

    @Deprecated
    @Override
    public Iterator<Key> iterator() {
        return st.keySet().iterator();
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("called min() with empty symbol table");
        }
        return st.firstKey();
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("called max() with empty symbol table");
        }
        return st.lastKey();
    }

    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called ceiling() with null key");
        }
        Key k = st.ceilingKey(key);
        if (k == null) {
            throw new NoSuchElementException("all keys are less than " + key);
        }
        return k;
    }

    public Key floor(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called floor() with null key");
        }
        Key k = st.floorKey(key);
        if (k == null) {
            throw new NoSuchElementException("all keys are greater than " + key);
        }
        return k;
    }

    public static void main(String[] args) {
        ST<String, Integer> st = new ST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        for (String s : st) {
            StdOut.println(s + " " + st.get(s) );
        }
    }



}
