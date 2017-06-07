package fundamentals;

import utils.StdIn;
import utils.StdOut;

import java.util.Iterator;

/**
 * Bag: 背包
 * Created by zhanjiahan on 17-6-7.
 */
public class Bag<Item> implements Iterable<Item> {

    private Node<Item> first;
    private int n;

    private static class Node<Item> {
        Item item;
        Node next;
    }

    public Bag() {
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
        Node<Item> oldfirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public Iterator<Item> iterator() {
        return new ListIteraor<>(first);
    }

    private class ListIteraor<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIteraor(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current == null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Bag<String> bag = new Bag<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }

        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }
    }
}
