package fundamentals;

import utils.StdIn;
import utils.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Queue: 队列
 * Created by zhanjiahan on 17-6-7.
 */
public class Queue<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    private static class Node<Item> {
        Item item;
        Node<Item> next;
    }

    public Queue() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
//        return n == 0;
        return first == null;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        Node<Item> oldlast = last;
        last = new Node<>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null; // to avoid loitering
        }
        n--;
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

    public Iterator<Item> iterator() {
        return new ListIterator(first);
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
            if (isEmpty()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            } else if (!queue.isEmpty()) {
                StdOut.print(queue.dequeue() + " ");
            }
        }
        StdOut.println("(" + queue.size() + " left on queue.");
    }
}
