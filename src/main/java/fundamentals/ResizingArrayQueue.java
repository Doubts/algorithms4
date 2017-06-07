package fundamentals;

import utils.StdIn;
import utils.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ResizingArrayQueue: 队列的可变数组实现
 * Created by zhanjiahan on 17-6-7.
 */
public class ResizingArrayQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int first;
    private int last;
    private int n;

    public ResizingArrayQueue() {
        a = (Item[]) new Object[2];
        first = 0;
        last = 0;
        n = 0;
    }

    public boolean isEmpty() {
//        return (first == last);
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (n == a.length) resize(2 * a.length);
        a[last++] = item;
        if (last == a.length) last = 0; // wrap around
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        Item item = a[first];
        a[first] = null;
        n--;
        first++;
        if (first == a.length) first = 0;
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return item;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < a.length; i++) {
            temp[i] = a[(first + i) % a.length];  // The key code
        }
        a = temp;
        first = 0;
        last = n;
    }

    public Item peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        return a[first];
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
            Item item = a[(i + first) % a.length];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            } else if (!item.isEmpty()) {
                StdOut.print(queue.dequeue() +" ");
            }
        }
        StdOut.println("(" + queue.size() + "left on queue");
    }
}
