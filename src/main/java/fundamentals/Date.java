package fundamentals;

import utils.StdIn;
import utils.StdOut;

/**
 * Date: date
 * Created by zhanjiahan on 17-6-6.
 */
public final class Date implements Comparable<Date> {

    private static final int[] DAYS = {0, 31, 29, 31, 30, 31, 30, 31, 31,30, 31, 30, 31};

    private final int month;
    private final int day;
    private final int year;

    public Date(int month, int day, int year) {
        if (!isValid(month, day, year)) {
            throw new IllegalArgumentException("Invalid Date");
        }
        this.month = month;
        this.day = day;
        this.year = year;
    }

    //  Initializes new date specified as a string in form MM/DD/YYYY.
    public Date(String date) {
        String[] fields = date.split("/");
        if (fields.length != 3) {
            throw new IllegalArgumentException("Invalid date String");
        }
        month = Integer.parseInt(fields[0]);
        day = Integer.parseInt(fields[1]);
        year = Integer.parseInt(fields[2]);

        if (!isValid(month, day, year)) {
            throw new IllegalArgumentException("Invalid date");
        }
    }

    public int month() {
        return month;
    }
    public int day() {
        return day;
    }
    public int year() {
        return year;
    }

    public Date next() {
        if (isValid(month, day + 1, year)) return new Date(month, day + 1,year);
        else if (isValid(month + 1, 1, year)) return new Date(month + 1, 1, year);
        else return new Date(1, 1, year + 1);
    }

    public boolean isAfter(Date that) {
        return this.compareTo(that) > 0;
    }

    public boolean isBefore(Date that) {
        return  this.compareTo(that) < 0;
    }

    @Override
    public int compareTo(Date that) {
        if (this.year > that.year) return 1;
        if (this.year < that.year) return -1;
        if (this.month > that.month) return 1;
        if (this.month < that.month) return -1;
        if (this.day > that.day) return 1;
        if (this.day < that.day) return -1;
        return 0;
    }

    private boolean isValid(int m, int d, int y) {
        if (m < 1 || m > 12) return false;
        if (d < 1 || d > DAYS[m]) return false;
        if (m == 2 && d == 29 && !isLeapYear(y)) {
            return false;
        }
        return true;
    }

    private boolean isLeapYear(int y) {
        if (y % 400 == 0) return true;
        if (y % 100 == 0)  return false;
        return y % 4 == 0;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)  return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        Date that = (Date) obj;
//        return this.compareTo(that) == 0;
        return (this.year == that.year) && (this.month == that.month) && (this.day == that.day);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + month;
        hash = 31 * hash + day;
        hash = 31 * hash + year;
        return hash;
    }

    public static void main(String[] args) {
        Date today = new Date(6, 6, 2017);
        StdOut.println(today);
        for (int i = 0; i < 10; i++) {
            today = today.next();
            StdOut.println(today);
        }

        StdOut.println();
        StdOut.println(today.isAfter(today.next()));
        StdOut.println(today.isAfter(today));
        StdOut.println(today.next().isAfter(today));
        StdOut.println();

        Date birthday = new Date(10, 26, 1971);
        StdOut.println(birthday);
        for (int i = 0; i < 10; i++) {
            birthday = birthday.next();
            StdOut.println(birthday);
        }

    }
}
