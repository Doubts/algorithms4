package fundamentals;

import utils.StdIn;
import utils.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedQueue: 队列的链表实现
 * Created by zhanjiahan on 17-6-7.
 */
public class LinkedQueue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

    private class Node {
        Item item;
        Node next;
    }

    public LinkedQueue() {
        first = null;
        last = null;
        n = 0;
        assert check();
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        n++;
        assert check();
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        n--;
        assert check();
        return item;
    }

    public Item peak() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        return first.item;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item).append(' ');
        }
        return sb.toString();
    }

    private boolean check() {
        if (n < 0) {
            return false;
        }
        if (n == 0) {
            if (first != null || last != null) {
                return false;
            }
        } else if(n == 1) {
            if (first == null || last == null) {
                return false;
            }
            if (first != last) return false;
            if (first.next != null) return false;
        } else {
            if (first == null || last == null) {
                return false;
            }
            if (first == last) {
                return false;
            }
            if (first.next == null) {
                return false;
            }
            if (last.next != null) {
                return false;
            }

            int numberOfNodes = 0;
            for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) return false;

            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) {
                return false;
            }
        }
        return true;
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
        LinkedQueue<String> queue = new LinkedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            } else if (!queue.isEmpty()) {
                StdOut.print(queue.dequeue() + " ");
            }
        }
        StdOut.println("(" + queue.size() + " left on queue");
    }

}
