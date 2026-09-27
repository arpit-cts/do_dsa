import java.util.Locale;

// Chapter 04 · Logarithms — small runnable demos.
// Run from this folder:  java Logarithms.java
public class Logarithms {

    // How many times can n be halved (integer division) before it becomes 1?
    // For n >= 1 this is floor(log2 n).
    static int halvingsToOne(long n) {
        int steps = 0;
        while (n > 1) {
            n = n / 2;
            steps++;
        }
        return steps;
    }

    // How many doublings take 1 up to at least n? For n >= 1 this is ceil(log2 n).
    static int doublingsToReach(long n) {
        int steps = 0;
        long value = 1;
        while (value < n) {
            value = value * 2;
            steps++;
        }
        return steps;
    }

    // floor(log2 n) for n >= 1, read from the position of the highest 1-bit.
    static int floorLog2(long n) {
        return 63 - Long.numberOfLeadingZeros(n);
    }

    // Bits needed to write n in binary (n >= 1).
    static int bitLength(long n) {
        return floorLog2(n) + 1;
    }

    // Decimal digits of n (n >= 1), counted safely with a loop.
    static int digitCount(long n) {
        int digits = 0;
        while (n > 0) {
            n = n / 10;
            digits++;
        }
        return digits;
    }

    // Worst-case checks of binary search on n sorted items.
    // After checking the middle, at most size / 2 items are left.
    static int binarySearchWorstChecks(long n) {
        int checks = 0;
        long size = n;
        while (size > 0) {
            checks++;
            size = size / 2;
        }
        return checks;
    }

    // Rounds of the loop  for (i = 1; i < n; i *= factor).
    static int multiplyLoopRounds(long n, int factor) {
        int rounds = 0;
        for (long i = 1; i < n; i *= factor) {
            rounds++;
        }
        return rounds;
    }

    static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    static String fmt(String pattern, Object... values) {
        return String.format(Locale.ROOT, pattern, values);
    }

    static void say(String line) {
        System.out.println(line);
    }

    public static void main(String[] args) {
        say("1) Halvings, doublings, bits and digits");
        say(fmt("   %-27s %9s %9s %5s %7s",
                "n", "halvings", "doublings", "bits", "digits"));
        long[] examples = {1, 2, 8, 10, 16, 100, 1_000, 1_000_000,
                1_000_000_000L, 1_000_000_000_000_000_000L};
        for (long n : examples) {
            say(fmt("   %,-27d %9d %9d %5d %7d", n, halvingsToOne(n),
                    doublingsToReach(n), bitLength(n), digitCount(n)));
        }
        say("   halvings = floor(log2 n), doublings = ceil(log2 n)");

        say("");
        say("2) Log rules, checked with exact powers of 2");
        say(fmt("   log2(8 * 32)    = %d   log2(8) + log2(32)    = %d + %d  = %d",
                floorLog2(8 * 32), floorLog2(8), floorLog2(32),
                floorLog2(8) + floorLog2(32)));
        say(fmt("   log2(1024 / 16) = %d   log2(1024) - log2(16) = %d - %d = %d",
                floorLog2(1024 / 16), floorLog2(1024), floorLog2(16),
                floorLog2(1024) - floorLog2(16)));
        say(fmt("   log2(8^3)       = %d   3 * log2(8)           = 3 * %d  = %d",
                floorLog2(8 * 8 * 8), floorLog2(8), 3 * floorLog2(8)));

        say("");
        say("3) Change of base: log2(n) / log10(n) is always the same number");
        long[] bases = {100, 1_000_000, 1_000_000_000_000L};
        for (long n : bases) {
            double a = log2(n);
            double b = Math.log10(n);
            say(fmt("   n = %,-18d log2 = %6.2f   log10 = %5.2f   ratio = %.2f",
                    n, a, b, a / b));
        }

        say("");
        say("4) Rounds of  for (i = 1; i < n; i *= k)  with n = 1000");
        int[] factors = {2, 3, 10};
        for (int k : factors) {
            say(fmt("   k = %2d: %2d rounds", k, multiplyLoopRounds(1000, k)));
        }

        say("");
        say("5) Binary search, worst-case number of checks");
        long[] sizes = {16, 1_000, 1_000_000, 1_000_000_000L};
        for (long n : sizes) {
            say(fmt("   n = %,-14d -> %2d checks", n, binarySearchWorstChecks(n)));
        }

        say("");
        say("6) The floating-point trap");
        double bad = Math.log(1000) / Math.log(10);
        say("   Math.log(1000) / Math.log(10) = " + bad);
        say("   (int) that + 1                = " + ((int) bad + 1)
                + "   <- wrong: 1000 has 4 digits");
        say("   Math.log10(1000)              = " + Math.log10(1000));
        say("   digitCount(1000)              = " + digitCount(1000)
                + "   <- the loop is always right");
        say("   Math.log(0) = " + Math.log(0) + ",  Math.log(-1) = "
                + Math.log(-1));

        say("");
        say("7) log2(n!) grows like n * log2(n)");
        int[] ns = {10, 1_000, 1_000_000};
        for (int n : ns) {
            double logFactorial = 0;
            for (int i = 2; i <= n; i++) {
                logFactorial += log2(i);
            }
            double nLogN = n * log2(n);
            say(fmt("   n = %,-10d log2(n!) = %,15.1f   n*log2(n) = %,15.1f",
                    n, logFactorial, nLogN));
        }

        say("");
        say("8) How slowly log2(n) grows  (one # = 2 steps)");
        long[] growth = {10, 1_000, 1_000_000, 1_000_000_000L,
                1_000_000_000_000_000_000L};
        for (long n : growth) {
            double steps = log2(n);
            String bar = "#".repeat((int) Math.round(steps / 2));
            say(fmt("   n = %-27s %5.1f  %s", fmt("%,d", n), steps, bar));
        }
    }
}
