package fundamentals;

import utils.StdIn;
import utils.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedBag: 背包的链表实现
 * Created by zhanjiahan on 17-6-7.
 */
public class LinkedBag<Item> implements Iterable<Item> {
    private Node first;
    private int n;

    private class Node {
        Item item;
        Node next;
    }

    public LinkedBag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void add(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = null;
        oldfirst.next = first;
        n++;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        @Override
        public boolean hasNext() {
            return current != null;
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
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        LinkedBag<String> bag = new LinkedBag<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }
        StdOut.println("bag size = " + bag.size());
        for (String s : bag) {
            StdOut.print(s);
        }
    }
}
