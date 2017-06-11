package fundamentals;

import utils.StdIn;
import utils.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ResizingArrayBag: 背包的可变数组实现
 * Created by zhanjiahan on 17-6-7.
 */
public class ResizingArrayBag<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    public ResizingArrayBag() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void add(Item item) {
        if (n == a.length) {
            resize(2 * a.length);
        }
        a[n++] = item;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0 ;i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return a[i++];
        }
    }

    public static void main(String[] args) {
        ResizingArrayBag<String> bag = new ResizingArrayBag<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }
        StdOut.println("bag size = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }
    }
}
