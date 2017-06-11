package fundamentals;

import utils.StdIn;
import utils.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ResizingArrayStack: 栈的可变数组实现
 * Created by zhanjiahan on 17-6-7.
 */
public class ResizingArrayStack<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    public ResizingArrayStack() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp =(Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public void push(Item item) {
        if (n == a.length) {
            resize(a.length * 2);
        }
        a[n++] = item;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        Item res = a[n - 1];
        a[n - 1] = null;  // 防止对象游离 to avoid loitering
        n--;
        if (n > 0 && n < a.length / 4) {
            resize(a.length / 2);
        }
        return res;
    }

    public Item peak() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is emptuy");
        }
        return a[n - 1];
    }

    public Iterator<Item> iterator() {
        return new ReverseArraysIterator(n);
    }

    private class ReverseArraysIterator implements Iterator<Item> {
        private int i;

        public ReverseArraysIterator(int n) {
            i = n - 1;
        }

        @Override
        public boolean hasNext() {
            return i >= 0;
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
            return a[i--];
        }
    }

    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.push(item);
            else if (!stack.isEmpty()) StdOut.println(stack.pop() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack");

    }
}
