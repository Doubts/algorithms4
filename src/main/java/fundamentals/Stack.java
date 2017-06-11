package fundamentals;

import utils.StdIn;
import utils.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Stack: 栈
 * Created by zhanjiahan on 17-6-7.
 */
public class Stack<Item> implements Iterable<Item> {

    private Node<Item> first;
    private int n;

    private static class Node<Item> {
        Item item;
        Node<Item> next;
    }

    public Stack() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void push(Item item) {
        Node oldfirst = first;
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("stack underflow");
        }
        Item item = first.item;
        first = first.next;
        n--;
        return item;
    }

    public Item peak() {
        if (isEmpty()) {
            throw new NoSuchElementException("stack underflow");
        }
        return first.item;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item).append(' ');
        }
        return sb.toString();
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

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
        Stack<String> stack = new Stack<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                stack.push(item);
            } else if (!stack.isEmpty()) {
                StdOut.print(stack.pop() +" ");
            }
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}
