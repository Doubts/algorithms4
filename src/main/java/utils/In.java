package utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by zhanjiahan on 17-5-31.
 * In: read from file
 */
public final class In {

    // assume Unicode UTF-8 encoding
    private static final String CHARSET_NAME = "UTF-8";

    // assume language = English, country = US for consistency with System.out.
    private static final Locale LOCALE = Locale.CHINESE;

    // the default token separator; we maintain the invariant that this value
    // is held by the scanner's delimiter between calls
    private static final Pattern WHITESPACE_PATTERN
            = Pattern.compile("\\p{javaWhitespace}+");

    // makes whitespace characters significant
    private static final Pattern EMPTY_PATTERN
            = Pattern.compile("");

    // used to read the entire input. source:
    // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
    private static final Pattern EVERYTHING_PATTERN
            = Pattern.compile("\\A");

    //// end: section (1 of 2) of code duplicated from In to StdIn.

    private Scanner scanner;

    public In() {
        scanner = new Scanner(new BufferedInputStream(System.in), CHARSET_NAME);
        scanner.useLocale(LOCALE);
    }

    public In(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket argument is null");
        }
        try {
            InputStream inputStream = socket.getInputStream();
            scanner = new Scanner(new BufferedInputStream(inputStream), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("can't open " + socket, ioe);
        }
    }

    public In(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url argument is null");
        }
        try {
            URLConnection site = url.openConnection();
            InputStream inputStream = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(inputStream), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("can't open " + url, ioe);
        }
    }

    public In(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file argument is null");
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fileInputStream), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (FileNotFoundException fnfe) {
            throw new IllegalArgumentException("can't find file " + file, fnfe);
        }
    }

    public In(String name) {
        if (name == null) {
            throw new IllegalArgumentException("argument is null");
        }
        File file = new File(name);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fileInputStream), CHARSET_NAME);
                scanner.useLocale(LOCALE);
            } catch (FileNotFoundException fnfe) {
                throw new IllegalArgumentException("can't find file " + file, fnfe);
            }
        }

        URL url = getClass().getResource(name);
        if (url == null) {
            try {
                url = new URL(name);
            } catch (MalformedURLException me) {
                throw new IllegalArgumentException("can't get a url form the name " + name, me);
            }
        }
        try {
            URLConnection site = url.openConnection();
            InputStream inputStream = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(inputStream), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("can't open " + name, ioe);
        }
    }

    public In(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("scanner argument is null");
        }
        this.scanner = scanner;
    }

    public boolean exists() {
        return scanner != null;
    }

    public boolean isEmpty() {
        return !scanner.hasNext();
    }

    public boolean hasNextLine() {
        return !scanner.hasNextLine();
    }

    public boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        } catch (NoSuchElementException nsee) {
            line = null;
        }
        return line;
    }

    public char readChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        try {
            String ch = scanner.next();
            assert ch.length() == 1 : "Internal (Std)In readChar error";
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return ch.charAt(0);
        } catch (NoSuchElementException nsee) {
            throw new NoSuchElementException("attempts to read a 'char' value from input stream, but there are no more tokens available");        }
    }

    public String readAll() {
        if (!scanner.hasNextLine())
            return "";

        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
        // not that important to reset delimeter, since now scanner is empty
        scanner.useDelimiter(WHITESPACE_PATTERN); // but let's do it anyway
        return result;
    }


    public String readString() {
        try {
            return scanner.next();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'String' value from input stream, but there are no more tokens available");
        }
    }

    public int readInt() {
        try {
            return scanner.nextInt();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read an 'int' value from input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read an 'int' value from input stream, but there are no more tokens available");
        }
    }

    public double readDouble() {
        try {
            return scanner.nextDouble();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'double' value from input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read a 'double' value from input stream, but there are no more tokens available");
        }
    }

    public float readFloat() {
        try {
            return scanner.nextFloat();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'float' value from input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read a 'float' value from input stream, but there are no more tokens available");
        }
    }

    public long readLong() {
        try {
            return scanner.nextLong();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'long' value from input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read a 'long' value from input stream, but there are no more tokens available");
        }
    }

    public short readShort() {
        try {
            return scanner.nextShort();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'short' value from input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read a 'short' value from input stream, but there are no more tokens available");
        }
    }

    public byte readByte() {
        try {
            return scanner.nextByte();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'byte' value from input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read a 'byte' value from input stream, but there are no more tokens available");
        }
    }

    public boolean readBoolean() {
        try {
            String token = readString();
            if ("true".equalsIgnoreCase(token))  return true;
            if ("false".equalsIgnoreCase(token)) return false;
            if ("1".equals(token))               return true;
            if ("0".equals(token))               return false;
            throw new InputMismatchException("attempts to read a 'boolean' value from input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'boolean' value from input stream, but there are no more tokens available");
        }
    }

    public String[] readAllStrings() {
        // we could use readAll.trim().split(), but that's not consistent
        // since trim() uses characters 0x00..0x20 as whitespace
        String[] tokens = WHITESPACE_PATTERN.split(readAll());
        if (tokens.length == 0 || tokens[0].length() > 0)
            return tokens;
        String[] decapitokens = new String[tokens.length-1];
        for (int i = 0; i < tokens.length-1; i++)
            decapitokens[i] = tokens[i+1];
        return decapitokens;
    }

    public String[] readAllLines() {
        ArrayList<String> lines = new ArrayList<String>();
        while (hasNextLine()) {
            lines.add(readLine());
        }
        return lines.toArray(new String[lines.size()]);
    }


    public int[] readAllInts() {
        String[] fields = readAllStrings();
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Integer.parseInt(fields[i]);
        return vals;
    }

    public long[] readAllLongs() {
        String[] fields = readAllStrings();
        long[] vals = new long[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Long.parseLong(fields[i]);
        return vals;
    }

    public double[] readAllDoubles() {
        String[] fields = readAllStrings();
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Double.parseDouble(fields[i]);
        return vals;
    }

    ///// end: section (2 of 2) of code duplicated from In to StdIn */

    public void close() {
        scanner.close();
    }


    public static void main(String[] args) {
        In in;
        String urlName = "http://introcs.cs.princeton.edu/stdlib/InTest.txt";

        // read from a URL
        System.out.println("readAll() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            System.out.println(in.readAll());
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

        // read one line at a time from URL
        System.out.println("readLine() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

        // read one string at a time from URL
        System.out.println("readString() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            while (!in.isEmpty()) {
                String s = in.readString();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();


        // read one line at a time from file in current directory
        System.out.println("readLine() from current directory");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("./InTest.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();


        // read one line at a time from file using relative path
        System.out.println("readLine() from relative path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("../stdlib/InTest.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

        // read one char at a time
        System.out.println("readChar() from file");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("InTest.txt");
            while (!in.isEmpty()) {
                char c = in.readChar();
                System.out.print(c);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println();

        // read one line at a time from absolute OS X / Linux path
        System.out.println("readLine() from absolute OS X / Linux path");
        System.out.println("---------------------------------------------------------------------------");
        in = new In("/n/fs/introcs/www/java/stdlib/InTest.txt");
        try {
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();


        // read one line at a time from absolute Windows path
        System.out.println("readLine() from absolute Windows path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("G:\\www\\introcs\\stdlib\\InTest.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
            System.out.println();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

    }

}
