import java.util.Locale;
import java.util.function.LongUnaryOperator;

// Chapter 06 · Big-O and Time Complexity — count how often loops really run.
// Run from this folder:  java ComplexityDemo.java
public class ComplexityDemo {

    // P1: one loop
    static long oneLoop(long n) {
        long count = 0;
        for (long i = 0; i < n; i++) {
            count++;
        }
        return count;
    }

    // P2: two loops one after the other
    static long twoLoopsInARow(long n) {
        long count = 0;
        for (long i = 0; i < n; i++) {
            count++;
        }
        for (long j = 0; j < n; j++) {
            count++;
        }
        return count;
    }

    // P3: nested loops, both from 0 to n - 1
    static long nested(long n) {
        long count = 0;
        for (long i = 0; i < n; i++) {
            for (long j = 0; j < n; j++) {
                count++;
            }
        }
        return count;
    }

    // P4: nested loops, the inner one starts after i (every pair once)
    static long nestedTriangle(long n) {
        long count = 0;
        for (long i = 0; i < n; i++) {
            for (long j = i + 1; j < n; j++) {
                count++;
            }
        }
        return count;
    }

    // P5: halve until nothing is left
    static long halving(long n) {
        long count = 0;
        for (long i = n; i > 0; i /= 2) {
            count++;
        }
        return count;
    }

    // P6: double until you reach n
    static long doubling(long n) {
        long count = 0;
        for (long i = 1; i < n; i *= 2) {
            count++;
        }
        return count;
    }

    // P7: a doubling loop inside a normal loop
    static long nTimesLog(long n) {
        long count = 0;
        for (long i = 0; i < n; i++) {
            for (long j = 1; j < n; j *= 2) {
                count++;
            }
        }
        return count;
    }

    // P8: the outer loop doubles, the inner loop runs i times: 1 + 2 + 4 + ... < 2n
    static long doublingOuterLinearInner(long n) {
        long count = 0;
        for (long i = 1; i < n; i *= 2) {
            for (long j = 0; j < i; j++) {
                count++;
            }
        }
        return count;
    }

    // P9: stop when i * i passes n
    static long squareRootLoop(long n) {
        long count = 0;
        for (long i = 1; i * i <= n; i++) {
            count++;
        }
        return count;
    }

    // P10: harmonic loop: j jumps by i, so n/1 + n/2 + ... + n/n steps
    static long harmonic(long n) {
        long count = 0;
        for (long i = 1; i <= n; i++) {
            for (long j = i; j <= n; j += i) {
                count++;
            }
        }
        return count;
    }

    // P11: two pointers walking towards each other: every step moves one pointer
    static long twoPointers(long n) {
        long count = 0;
        long left = 0;
        long right = n - 1;
        while (left < right) {
            if ((left + right) % 3 == 0) {  // any rule that moves one pointer
                left++;
            } else {
                right--;
            }
            count++;
        }
        return count;
    }

    // P12: sliding window: each element enters once and leaves at most once
    static long slidingWindow(long n) {
        long count = 0;
        long left = 0;
        long windowSum = 0;
        for (long right = 0; right < n; right++) {
            windowSum += right % 7;          // element "right" enters
            count++;
            while (windowSum > 20) {         // shrink: element "left" leaves
                windowSum -= left % 7;
                left++;
                count++;
            }
        }
        return count;
    }

    // P13: three nested loops
    static long tripleNested(long n) {
        long count = 0;
        for (long i = 0; i < n; i++) {
            for (long j = 0; j < n; j++) {
                for (long k = 0; k < n; k++) {
                    count++;
                }
            }
        }
        return count;
    }

    // P14: an inner loop with a fixed size (100) is just a constant
    static long constantInner(long n) {
        long count = 0;
        for (long i = 0; i < n; i++) {
            for (int j = 0; j < 100; j++) {
                count++;
            }
        }
        return count;
    }

    // Characters copied when building a string of length n with s = s + "x".
    static long stringPlusCopies(long n) {
        long copied = 0;
        for (long length = 1; length <= n; length++) {
            copied += length;                // a brand-new string of this length is made
        }
        return copied;
    }

    // Elements copied by a growable array that doubles its capacity when full.
    static long doublingArrayCopies(long n) {
        long copies = 0;
        long capacity = 1;
        for (long size = 0; size < n; size++) {
            if (size == capacity) {
                copies += size;              // move everything into a bigger array
                capacity *= 2;
            }
        }
        return copies;
    }

    // Linear search: how many comparisons until we find the target?
    static int linearSearchComparisons(int[] a, int target) {
        int comparisons = 0;
        for (int x : a) {
            comparisons++;
            if (x == target) {
                break;
            }
        }
        return comparisons;
    }

    static String fmt(String pattern, Object... values) {
        return String.format(Locale.ROOT, pattern, values);
    }

    static String num(long x) {
        return fmt("%,d", x);
    }

    static String approx(double x) {
        if (x > 1e18) {
            return "huge";
        }
        if (x < 1e6) {
            return fmt("%,.0f", x);
        }
        return fmt("%.1e", x);
    }

    static void say(String line) {
        System.out.println(line);
    }

    static final long[] SIZES = {16, 1_024, 1_000_000};

    // One table row. The real loop runs only while n <= maxRealN;
    // bigger inputs use the formula instead and get a *.
    static void row(String id, String name, String formula, LongUnaryOperator loop,
                    LongUnaryOperator exact, long maxRealN) {
        String[] cells = new String[SIZES.length];
        for (int k = 0; k < SIZES.length; k++) {
            long n = SIZES[k];
            if (n <= maxRealN) {
                cells[k] = num(loop.applyAsLong(n));
            } else {
                long value = exact.applyAsLong(n);
                String shown = value >= 1_000_000_000_000L ? approx(value) : num(value);
                cells[k] = shown + "*";
            }
        }
        say(fmt("   %-3s %-26s %-10s %6s %15s %17s", id, name, formula,
                cells[0], cells[1], cells[2]));
    }

    static void row(String id, String name, String formula, LongUnaryOperator loop) {
        row(id, name, formula, loop, loop, Long.MAX_VALUE);
    }

    public static void main(String[] args) {
        say("1) How many times does the body run?  (* = too slow to run: formula)");
        say(fmt("   %-3s %-26s %-10s %6s %15s %17s", "", "loop pattern", "formula",
                "n=16", "n=1,024", "n=1,000,000"));
        row("P1", "one loop", "n", ComplexityDemo::oneLoop);
        row("P2", "two loops in a row", "2n", ComplexityDemo::twoLoopsInARow);
        row("P3", "nested, both to n", "n^2", ComplexityDemo::nested, n -> n * n, 1_024);
        row("P4", "nested, j starts at i+1", "n(n-1)/2", ComplexityDemo::nestedTriangle,
                n -> n * (n - 1) / 2, 1_024);
        row("P5", "i = i / 2 until 0", "log2(n)+1", ComplexityDemo::halving);
        row("P6", "i = i * 2 while i < n", "log2(n)", ComplexityDemo::doubling);
        row("P7", "doubling loop inside loop", "n log2(n)", ComplexityDemo::nTimesLog);
        row("P8", "outer doubles, inner to i", "< 2n",
                ComplexityDemo::doublingOuterLinearInner);
        row("P9", "while i * i <= n", "sqrt(n)", ComplexityDemo::squareRootLoop);
        row("P10", "harmonic: j += i", "~ n ln n", ComplexityDemo::harmonic);
        row("P11", "two pointers", "n - 1", ComplexityDemo::twoPointers);
        row("P12", "sliding window", "<= 2n", ComplexityDemo::slidingWindow);
        row("P13", "three nested loops", "n^3", ComplexityDemo::tripleNested,
                n -> n * n * n, 16);
        row("P14", "inner loop of 100", "100n", ComplexityDemo::constantInner);

        say("");
        say("2) When n doubles from 1,000 to 2,000, the work is multiplied by:");
        double n1 = 1_000;
        double n2 = 2_000;
        double log1 = Math.log(n1) / Math.log(2);
        double log2 = Math.log(n2) / Math.log(2);
        say(fmt("   O(1): 1.00   O(log n): %.2f   O(n): %.2f   O(n log n): %.2f",
                log2 / log1, n2 / n1, (n2 * log2) / (n1 * log1)));
        say(fmt("   O(n^2): %.2f   O(n^3): %.2f   O(2^n): 2^1000 (a 302-digit number!)",
                (n2 * n2) / (n1 * n1), (n2 * n2 * n2) / (n1 * n1 * n1)));

        say("");
        say("3) Steps for typical input sizes (a computer does about 10^8 per second)");
        String t = "   %-7s %6s %8s %8s %8s %8s %8s %8s %8s";
        say(fmt(t, "n", "log n", "sqrt n", "n", "n log n", "n^2", "n^3", "2^n", "n!"));
        long[] sizes = {10, 20, 500, 5_000, 1_000_000, 1_000_000_000_000L};
        String[] labels = {"10", "20", "500", "5,000", "10^6", "10^12"};
        for (int k = 0; k < sizes.length; k++) {
            long n = sizes[k];
            double lg = Math.log(n) / Math.log(2);
            double factorial = 1;
            for (int i = 2; i <= n && factorial < 1e19; i++) {
                factorial *= i;
            }
            say(fmt(t, labels[k], approx(lg), approx(Math.sqrt(n)), approx(n),
                    approx(n * lg), approx((double) n * n), approx((double) n * n * n),
                    approx(Math.pow(2, n)), approx(factorial)));
        }

        say("");
        say("4) Hidden costs: building a string of n characters");
        long[] lengths = {1_000, 100_000};
        for (long n : lengths) {
            say(fmt("   n = %,7d   s = s + \"x\" copies %,13d chars",
                    n, stringPlusCopies(n)));
            say(fmt("   %13s   StringBuilder copies %,11d chars", "",
                    n + doublingArrayCopies(n)));
        }

        say("");
        say("5) Amortized O(1): an array that doubles when full (like ArrayList)");
        long[] adds = {16, 1_000, 1_000_000};
        for (long n : adds) {
            long copies = doublingArrayCopies(n);
            say(fmt("   %,9d adds -> %,9d copies in total = %.2f copies per add",
                    n, copies, (double) copies / n));
        }
        say(fmt("   growing by just 1 each time instead: %,d copies for 1,000,000 adds",
                1_000_000L * 999_999L / 2));

        say("");
        say("6) Linear search in 1,000 numbers: comparisons, best to worst case");
        int[] data = new int[1_000];
        for (int i = 0; i < data.length; i++) {
            data[i] = i * 3;
        }
        say("   target at the front  : " + linearSearchComparisons(data, 0));
        say("   target in the middle : " + linearSearchComparisons(data, 1_500));
        say("   target at the end    : " + linearSearchComparisons(data, 2_997));
        say("   target missing       : " + linearSearchComparisons(data, 7));
    }
}