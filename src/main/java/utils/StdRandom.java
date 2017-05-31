package utils;

import java.util.Random;

/**
 * StdRandom: Random
 * Created by zhanjiahan on 17-5-31.
 */
public final class StdRandom {

    private static Random random; // pseudo-random number generator
    private static long seed; // 伪随机数(pseudo-random number)生成器种子

    // static initializer
    static {
        seed = System.currentTimeMillis();
        random = new Random();
    }

    // don't initializer
    private StdRandom() {
    }

    public static void setSeed(long s) {
        seed = s;
        random = new Random(seed);
    }

    public static long getSeed() {
        return seed;
    }

    /**
     * return a random real number uniformly(均匀地) in [0, 1)
     * @return a random real number uniformly in [0, 1)
     */
    public static double uniform() {
        return random.nextDouble();
    }

    public static int uniform(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("argument must be positive");
        }
        return random.nextInt(n);
    }

    public static int uniform(int a, int b) {
        if (b <= a || ((long) b - a >= Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range:[" + a + ", " + b + ")");
        }
//        return a + random.nextInt(b - a);
        return a + uniform(b - a);
    }

    public static double uniform(double a, double b) {
        if (b <= a || ((double) b - a >= Double.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range:[" + a + ", " + b + ")");
        }
        return a + uniform() * (b - a);
    }

    /**
     * Return a random boolean from a Bernoulli distribution with success
     * probability <em>p</em>
     * @param p the probability of returning {@code true}
     * @return {@code true} with probability {@code p} and
     *         {@code false} with probability {@code p}
     */
    public static boolean bernoulli(double p) {
        if (!(p >= 0.0 || p <= 1.0)) {
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0");
        }
        return uniform() < p;
    }

    public static boolean bernoulli() {
        return bernoulli(0.5);
    }

    /**
     * Return a random real number from a standard Gaussian Distribution. (高斯分布即正态分布)
     * @return a random real number from a standard Gaussian distribution.
     */
    public static double gaussian() {
        double r, x, y;
        do {
            x = uniform(-1.0, 1.0);
            y = uniform(-1.0, -1.0);
            r = x * x + y * y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);
    }

    public static double gaussian(double mu, double sigma) {
        return mu + sigma * gaussian();
    }

    /**
     * return a random int number from a geometric distribution with success probability <em>p</em>
     * @param p the parameter of the geometric distribution
     * @return a random integer number from a geometric distribution
     */
    public static int geometric(double p) {
        if (!(p >= 0.0 || p <= 1.0)) {
            throw new IllegalArgumentException("probability must be between 0.0 and 1.0");
        }
        // using the algorithm by Knuth
        return (int) Math.ceil(Math.log(uniform()) / Math.log(1.0 - p));
    }

    // 离散随机分布
    public static int poisson(double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("lambda must be positive");
        }
        if (Double.isInfinite(lambda)) {
            throw new IllegalArgumentException("lambda must be finite");
        }

        int k = 0;
        double p = 1.0;
        double expLambda = Math.exp(lambda);
        do {
            k++;
            p *= uniform();
        } while (p >= expLambda);
        return k-1;
    }

    // Pareto Distribution
    public static double pareto() {
        return pareto(1.0);
    }

    public static double pareto(double alpha) {
        if (!(alpha > 0.0)) {
            throw new IllegalArgumentException("alpha must be positive");
        }
        return Math.pow(1 - uniform(), -1.0 / alpha) - 1.0;
    }

    public static double cauchy() {
        return Math.tan(Math.PI * (uniform() - 0.5));
    }

    public static int discrete(double[] probabilities) {
        if (probabilities == null) {
            throw new IllegalArgumentException("argument must be not null");
        }
        double EPSILON = 1E-14;
        double sum = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            if (!(probabilities[i] >= 0.0)) {
                throw new IllegalArgumentException("array entry " + i + " must be nagative: " + probabilities[i]);
            }
            sum += probabilities[i];
        }
        if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON) {
            throw new IllegalArgumentException("sum of array is invalid.");
        }
        while (true) {
            double r = uniform();
            sum = 0.0;
            for (int i = 0; i < probabilities.length; i++) {
                sum = sum + probabilities[i];
                if (sum > r) {
                    return i;
                }
            }
        }
    }

    public static int discrete(int[] frequencies) {
        if (frequencies == null) throw new IllegalArgumentException("argument array is null");
        long sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] < 0)
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + frequencies[i]);
            sum += frequencies[i];
        }
        if (sum == 0)
            throw new IllegalArgumentException("at least one array entry must be positive");
        if (sum >= Integer.MAX_VALUE)
            throw new IllegalArgumentException("sum of frequencies overflows an int");

        // pick index i with probabilitity proportional to frequency
        double r = uniform((int) sum);
        sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            sum += frequencies[i];
            if (sum > r) return i;
        }

        // can't reach here
        assert false;
        return -1;
    }

    public static double exp(double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("lambda must positive");
        }
        return -Math.log(1 - uniform()) / lambda;
    }

    public static void shuffle(Object[] a) {
        if (a == null) {
            throw new IllegalArgumentException("array is null");
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n - i); // between i and n - 1;
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(double[] a) {
        if (a == null) {
            throw new IllegalArgumentException("array is null");
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n - i);
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("array is null");
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n - i);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(char[] a) {
        if (a == null) {
            throw new IllegalArgumentException("array is null");
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n - i);
            char temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(Object[] a, int low, int high) {
        if (a == null) {
            throw  new IllegalArgumentException("array is null");
        }
        if (low < 0 || low > high || high > a.length) {
            throw new IllegalArgumentException("invlaid subarray range: [" + low + ", " + high + ")");
        }
        for (int i = low; i < high; i++) {
            int r = i + uniform(high - i);
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(double[] a, int low, int high) {
        if (a == null) {
            throw  new IllegalArgumentException("array is null");
        }
        if (low < 0 || low > high || high > a.length) {
            throw new IllegalArgumentException("invlaid subarray range: [" + low + ", " + high + ")");
        }
        for (int i = low; i < high; i++) {
            int r = i + uniform(high - i);
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(int[] a, int low, int high) {
        if (a == null) {
            throw  new IllegalArgumentException("array is null");
        }
        if (low < 0 || low > high || high > a.length) {
            throw new IllegalArgumentException("invlaid subarray range: [" + low + ", " + high + ")");
        }
        for (int i = low; i < high; i++) {
            int r = i + uniform(high - i);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(char[] a, int low, int high) {
        if (a == null) {
            throw  new IllegalArgumentException("array is null");
        }
        if (low < 0 || low > high || high > a.length) {
            throw new IllegalArgumentException("invlaid subarray range: [" + low + ", " + high + ")");
        }
        for (int i = low; i < high; i++) {
            int r = i + uniform(high - i);
            char temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    // 置换
    public static int[] permutation(int n) {
       if (n < 0) {
           throw new IllegalArgumentException("argument is nagative");
       }
       int[] perm = new int[n];
       for (int i = 0; i < n; i++) {
           perm[i] = i;
       }
       shuffle(perm);
       return perm;
    }

    public static int[] permutation(int n, int k) {
        if (n < 0 ) {
            throw new IllegalArgumentException("argument is nagative");
        }
        if (k < 0 || k > n) {
            throw new IllegalArgumentException("k must be between 0 and n");
        }
        int[] perm = new int[k];
        for (int i = 0; i < k; i++) {
            int r = uniform(i + 1);
            perm[i] = perm[r];
            perm[r] = i;
        }

        for (int i = k; i < n; i++) {
            int r = uniform(i + 1);
            if (r < k) {
                perm[r] = i;
            }
        }
        return perm;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        if (args.length == 2) StdRandom.setSeed(Long.parseLong(args[1]));
        double[] probabilities = { 0.5, 0.3, 0.1, 0.1 };
        int[] frequencies = { 5, 3, 1, 1 };
        String[] a = "A B C D E F G".split(" ");

        StdOut.println("seed = " + StdRandom.getSeed());
        for (int i = 0; i < n; i++) {
            StdOut.printf("%2d ",   uniform(100));
            StdOut.printf("%8.5f ", uniform(10.0, 99.0));
            StdOut.printf("%5b ",   bernoulli(0.5));
            StdOut.printf("%7.5f ", gaussian(9.0, 0.2));
            StdOut.printf("%1d ",   discrete(probabilities));
            StdOut.printf("%1d ",   discrete(frequencies));
            StdRandom.shuffle(a);
            for (String s : a)
                StdOut.print(s);
            StdOut.println();
        }
    }


}
