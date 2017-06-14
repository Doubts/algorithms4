package search;

import utils.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * SET: an ordered set of comparable keys
 * Created by jhzhan on 6/14/17.
 */
public class SET<Key extends Comparable<Key>> implements Iterable<Key> {
    private TreeSet<Key> set;


    public SET() {
        set = new TreeSet<>();
    }

    public SET(SET<Key> x) {
        set = new TreeSet<>(x.set);
    }

    public void add(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called add() with null key");
        }
        set.add(key);
    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called contains() with null key");
        }
        return set.contains(key);
    }

    public int size() {
        return set.size();
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called delete() with null key");
        }
        set.remove(key);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Iterator<Key> iterator() {
        return set.iterator();
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("called max() with empty set");
        }
        return set.last();
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("called min() with empty set");
        }
        return set.first();
    }

    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called ceiling() with null key");
        }
        Key k = set.ceiling(key);
        if (k == null) {
            throw new NoSuchElementException("all keys are less than " + key);
        }
        return k;
    }

    public Key floor(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called floor() with null key");
        }
        Key k = set.floor(key);
        if (k == null) {
            throw new NoSuchElementException("all keys are greater than " + key);
        }
        return k;
    }

    public SET<Key> union(SET<Key> that) {
        if (that == null) {
            throw new IllegalArgumentException("called union() with null argument");
        }
        SET<Key> c = new SET<>();
        for (Key x : this) {
            c.add(x);
        }
        for (Key y : that) {
            c.add(y);
        }
        return c;
    }

    public SET<Key> intersects(SET<Key> that) {
        if (that == null) {
            throw new IllegalArgumentException("called intersects() with a null argument");
        }
        SET<Key> c = new SET<>();
        if (this.size() < that.size()) {
            for (Key x : this) {
                c.add(x);
            }
        } else {
            for (Key x : that) {
                c.add(x);
            }
        }
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() !=  this.getClass()) {
            return false;
        }
        SET that = (SET) obj;

        return this.set.equals(that.set);
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported because sets are mutable");
    }

    @Override
    public String toString() {
        String s = set.toString();
        return "{" + s.substring(1, s.length() - 1) + "}";
    }

    public static void main(String[] args) {
        SET<String> set = new SET<>();
        StdOut.println("set = " + set);

        set.add("www.cs.princeton.edu");
        set.add("www.cs.princeton.edu");
        set.add("www.princeton.edu");
        set.add("www.math.princeton.edu");
        set.add("www.yale.edu");
        set.add("www.amazon.edu");
        set.add("www.simpsons.edu");
        set.add("www.stanford.edu");
        set.add("www.google.com");
        set.add("www.ibm.com");
        set.add("www.apple.com");
        set.add("www.slashdot.com");
        set.add("www.whitehouse.com");
        set.add("www.espn.com");
        set.add("www.snopes.com");
        set.add("www.movies.com");
        set.add("www.cnn.com");
        set.add("www.iitb.com");
        set.add("www.cumtb.edu.cn");

        StdOut.println(set.contains("www.cs.princeton.edu"));
        StdOut.println(set.contains("www.harvardsucks.com"));
        StdOut.println(set.contains("www.simpsons.com"));
        StdOut.println();

        StdOut.println("ceiling(www.simpsonr.com) = " + set.ceiling("www.simpsonr.com"));
        StdOut.println("ceiling(www.simpsons.com) = " + set.ceiling("www.simpsons.com"));
        StdOut.println("ceiling(www.simpsont.com) = " + set.ceiling("www.simpsont.com"));
        StdOut.println("floor(www.simpsonr.com)   = " + set.floor("www.simpsonr.com"));
        StdOut.println("floor(www.simpsons.com)   = " + set.floor("www.simpsons.com"));
        StdOut.println("floor(www.simpsont.com)   = " + set.floor("www.simpsont.com"));
        StdOut.println();

        StdOut.println("set = " + set);
        StdOut.println();

        for (String s : set) {
            StdOut.println(s);
        }

        StdOut.println();
        SET<String> set2 = new SET<>(set);
        StdOut.println(set.equals(set2));

    }

}
