package utils;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by zhanjiahan on 17-5-26.
 * Java I/O工具,能够从命令行和文件读取,参照algs4所写
 */
public final class StdIn {

    private static Scanner scanner;

    // 编码类型 utf-8
    private static final String CHARSET_NAME = "UTF-8";

    // 假设 language = English, country = China for consistency with System.out
    private static final Locale LOCALE = Locale.CHINESE;

    // 默认分隔符,字符匹配
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}");
    private static final Pattern EMPTY_PATTERN = Pattern.compile("");
    private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");


    // 似有构造方法,无法实例化,所有方法为static
    private StdIn() {
    }

    // 标准输入是否还有未读完的数据
    public static boolean isEmpty() {
        return !scanner.hasNext();
    }

    public static boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public static boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public static String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no line more");
        }
        return line;
    }

    public static char readChar() {
        try {
            scanner.useDelimiter(EMPTY_PATTERN);
            String ch = scanner.next();
            assert ch.length() == 1 : "读取字符出错";
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return ch.charAt(0);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no chat");
        }
    }

    public static String readAll() {
        if (!scanner.hasNextLine()) {
            return "";
        }
        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
        scanner.useDelimiter(EMPTY_PATTERN);
        return result;
    }

    public static String readString() {
        try {
            return scanner.next();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no String more");
        }
    }

    public static int readInt() {
        try {
            return scanner.nextInt();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no int more");
        }
    }

    public static long readLong() {
        try {
            return scanner.nextLong();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no long more");
        }
    }

    public static double readDouble() {
        try {
            return scanner.nextDouble();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no double more");
        }
    }

    public static float readFloat() {
        try {
            return scanner.nextFloat();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no float more");
        }
    }

    public static short readShort() {
        try {
            return scanner.nextShort();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no short more");
        }
    }

    public static byte readByte() {
        try {
            return scanner.nextByte();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no byte more");
        }
    }

    public static boolean readBoolean() {
        try {
            return scanner.nextBoolean();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no boolean more");
        }
    }

    public static String[] readAllStrings() {
        String[] tokens = WHITESPACE_PATTERN.split(readAll());
        if (tokens.length == 0 || tokens[0].length() > 0) {
            return tokens;
        }

        // 如果第一个字符为空,则排除
        String[] decapitokens = new String[tokens.length - 1];
        for (int i = 0; i < tokens.length - 1; i++) {
            decapitokens[i] = tokens[i+1];
        }
        return decapitokens;
    }

    public static String[] readAllLines() {
        ArrayList<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static int[] readAllInts() {
        String[] fields = readAllStrings();
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vals[i] = Integer.parseInt(fields[i]);
        }
        return vals;
    }

    public static long[] readAllLongs() {
        String[] fields = readAllStrings();
        long[] vals = new long[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vals[i] = Long.parseLong(fields[i]);
        }
        return vals;
    }

    public static double[] readAllDoubles() {
        String[] fields = readAllStrings();
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vals[i] = Double.parseDouble(fields[i]);
        }
        return vals;
    }

    public static float[] readAllFloats() {
        String[] fields = readAllStrings();
        float[] vals = new float[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vals[i] = Float.parseFloat(fields[i]);
        }
        return vals;
    }

    // 执行一次
    static {
        resync();
    }

    /**
     *  如果StdIn发生改变,使用该函数重新构造
     */
    private static void resync() {
        setScanner(new Scanner(new BufferedInputStream(System.in), CHARSET_NAME));
    }

    private static void setScanner(Scanner scanner) {
        StdIn.scanner = scanner;
        StdIn.scanner.useLocale(LOCALE);
    }

    public static void main(String[] args) {
        String s = StdIn.readString();

        int a = StdIn.readInt();

        boolean b = StdIn.readBoolean();

        double d = StdIn.readDouble();

        System.out.println("String: " + s + "  int: " + a + "  boolean: " + b + "  double: " + d);
    }


}