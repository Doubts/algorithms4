package fundamentals;

import utils.StdIn;
import utils.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedStack: 栈的链表实现
 * Created by zhanjiahan on 17-6-7.
 */
public final class LinkedStack<Item> implements Iterable<Item> {

    private int n;
    private Node first;

    private class Node {
        private Item item;
        private Node next;
    }

    public LinkedStack() {
        first = null;
        n = 0;
        assert check();
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void push(Item item) {
        Node node = new Node();
        node.item = item;
        node.next = first;
        first = node;
        n++;
        assert check();
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        Item item = first.item;
        first = first.next;
        n--;
        assert check();
        return item;
    }

    public Item peak() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        return first.item;
    }

    private boolean check() {
        if (n < 0) {
            return false;
        }
        if (n == 0) {
            if (first != null) {
                return false;
            }
        } else if (n == 1) {
            if (first == null) return false;
            if (first.next != null) return false;
        } else {
            if (first == null) return false;
            if (first.next == null) return false;
        }

        int numberOfNodes = 0;
        for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != n) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item).append(" ");
        }
        return sb.toString();
    }

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
        LinkedStack<String> stack = new LinkedStack<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                stack.push(item);
            } else if (!stack.isEmpty()) {
                    StdOut.println(stack.pop() + " ");
            }

            StdOut.println("(" + stack.size() + " left on stack)");
        }
    }

}
