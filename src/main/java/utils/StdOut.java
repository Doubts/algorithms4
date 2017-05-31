package utils;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * StdOut: Output
 * Created by zhanjiahan on 17-5-26.
 */
public final class StdOut {

    // 输出
    private static PrintWriter out;

    private static final Locale LOCALE = Locale.CHINESE;
    private static final String CHARSET_NAME = "UTF-8";

    // 在调用任何方法之前调用
    static {
        try {
            out = new PrintWriter(new OutputStreamWriter(System.out, CHARSET_NAME), true);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("不支持的编码形式");
        }
    }

    // 不允许实例化
    private StdOut() {

    }

    public static void close() {
        out.close();
    }

    public static void println() {
        out.println();
    }

    public static void println(Object o) {
        out.println(o);
    }

    public static void println(boolean x) {
        out.println(x);
    }

    public static void println(char x) {
        out.println(x);
    }

    public static void println(double x) {
        out.println(x);
    }

    public static void println(float x) {
        out.println(x);
    }

    public static void println(int x) {
        out.println(x);
    }

    public static void println(long x) {
        out.println(x);
    }

    public static void println(short x) {
        out.println(x);
    }

    public static void println(byte x) {
        out.println(x);
    }

    public static void print() {
        out.flush();
    }

    public static void print(Object x) {
        out.print(x);
        out.flush();
    }

    public static void print(byte x) {
        out.print(x);
        out.flush();
    }

    public static void print(char x) {
        out.print(x);
        out.flush();
    }

    public static void print(short x) {
        out.print(x);
        out.flush();
    }

    public static void print(int x) {
        out.print(x);
        out.flush();
    }

    public static void print(long x) {
        out.print(x);
        out.flush();
    }

    public static void print(float x) {
        out.print(x);
        out.flush();
    }

    public static void print(double x) {
        out.print(x);
        out.flush();
    }

    public static void print(boolean x) {
        out.print(x);
        out.flush();
    }

    public static void printf(String format, Object... args) {
        out.printf(LOCALE, format, args);
        out.flush();
    }

    public static void printf(Locale locale, String format, Object... args) {
        out.printf(locale, format, args);
        out.flush();
    }

    public static void main(String[] args) {
        StdOut.println("Test");
        StdOut.println(17);
        StdOut.println(true);
        StdOut.printf("%.6f\n", 1.0/7.0);
    }
}
