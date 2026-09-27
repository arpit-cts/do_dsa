import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

// Chapter 08 · Divisors and the Square Root Trick — runnable demos.
// Run from this folder:  java Divisors.java
public class Divisors {

    static long steps; // counts loop rounds, to compare the slow and the fast way

    // Slow way: try every d from 1 to n. About n steps.
    static List<Long> divisorsSlow(long n) {
        List<Long> result = new ArrayList<>();
        steps = 0;
        for (long d = 1; d <= n; d++) {
            steps++;
            if (n % d == 0) {
                result.add(d);
            }
        }
        return result;
    }

    // Fast way: divisors come in pairs (d, n / d), so stop once d * d > n.
    // About sqrt(n) steps.
    static List<Long> divisorsFast(long n) {
        List<Long> small = new ArrayList<>();
        List<Long> big = new ArrayList<>();
        steps = 0;
        for (long d = 1; d * d <= n; d++) {
            steps++;
            if (n % d == 0) {
                small.add(d);
                if (d != n / d) {        // the middle pair of a square counts once
                    big.add(n / d);
                }
            }
        }
        Collections.reverse(big);        // big partners come out largest first
        small.addAll(big);
        return small;
    }

    static int countDivisors(long n) {
        int count = 0;
        for (long d = 1; d * d <= n; d++) {
            if (n % d == 0) {
                count += (d == n / d) ? 1 : 2;
            }
        }
        return count;
    }

    // Sum of the divisors that are smaller than n (the "proper" divisors).
    static long sumOfProperDivisors(long n) {
        long sum = 0;
        for (long d = 1; d * d <= n; d++) {
            if (n % d == 0) {
                sum += d;
                long partner = n / d;
                if (partner != d) {
                    sum += partner;
                }
            }
        }
        return sum - n;                  // n itself is not a proper divisor
    }

    static boolean isPerfect(long n) {
        return n > 1 && sumOfProperDivisors(n) == n;
    }

    // LeetCode 1492: the k-th smallest divisor of n, or -1 if there is none.
    static long kthFactor(long n, int k) {
        List<Long> all = divisorsFast(n);
        return k <= all.size() ? all.get(k - 1) : -1;
    }

    // How many multiples of k lie in [left, right]? (left >= 1)
    static long countMultiples(long left, long right, long k) {
        return right / k - (left - 1) / k;
    }

    // Divisor counts of every number 1..n: each d visits its own multiples.
    // About n ln n steps in total.
    static int[] divisorCountsUpTo(int n) {
        int[] count = new int[n + 1];
        steps = 0;
        for (int d = 1; d <= n; d++) {
            for (int m = d; m <= n; m += d) {
                count[m]++;
                steps++;
            }
        }
        return count;
    }

    static int digitSum(long n) {
        int sum = 0;
        while (n > 0) {
            sum += (int) (n % 10);
            n /= 10;
        }
        return sum;
    }

    // Integer square root: the biggest r with r * r <= n.
    static long isqrt(long n) {
        long r = (long) Math.sqrt((double) n);
        while (r * r > n) {
            r--;
        }
        while ((r + 1) * (r + 1) <= n) {
            r++;
        }
        return r;
    }

    // LeetCode 1362 idea: the factor pair of x with the smallest difference.
    // Walk down from sqrt(x): the first divisor found gives the closest pair.
    static long[] closestPair(long x) {
        for (long d = isqrt(x); d >= 1; d--) {
            if (x % d == 0) {
                return new long[] {d, x / d};
            }
        }
        return new long[] {1, x};
    }

    static String fmt(String pattern, Object... values) {
        return String.format(Locale.ROOT, pattern, values);
    }

    static void say(String line) {
        System.out.println(line);
    }

    public static void main(String[] args) {
        say("1) Divisors of 36 come in pairs (d, 36 / d)");
        for (long d = 1; d * d <= 36; d++) {
            if (36 % d == 0) {
                String note = d == 36 / d ? "   <- the middle: d = 36 / d = sqrt(36)"
                        : "";
                say(fmt("   %2d x %2d = 36%s", d, 36 / d, note));
            }
        }

        say("");
        say("2) Slow way (d = 1..n) vs fast way (stop when d * d > n)");
        String table = "   %-20s %8s %19s %12s";
        say(fmt(table, "n", "divisors", "slow steps", "fast steps"));
        long[] tests = {36, 360, 1_000_000};
        boolean sameEveryTime = true;
        for (long n : tests) {
            List<Long> slow = divisorsSlow(n);
            long slowSteps = steps;
            List<Long> fast = divisorsFast(n);
            sameEveryTime &= slow.equals(fast);
            say(fmt(table, fmt("%,d", n), fast.size(), fmt("%,d", slowSteps),
                    fmt("%,d", steps)));
        }
        long huge = 1_000_000_000_000L;
        int hugeCount = divisorsFast(huge).size();
        say(fmt(table, fmt("%,d", huge), hugeCount, fmt("%,d*", huge),
                fmt("%,d", steps)));
        say("   both ways found the same divisors: " + sameEveryTime);
        say("   * not run: at 10^8 steps a second it would take about 3 hours");

        say("");
        say("3) Divisors of 360: small partners d (d * d <= 360), big partners 360 / d");
        List<Long> small = new ArrayList<>();
        List<Long> big = new ArrayList<>();
        for (long d = 1; d * d <= 360; d++) {
            if (360 % d == 0) {
                small.add(d);
                big.add(360 / d);
            }
        }
        say("   small: " + small);
        say("   big:   " + big);
        say("   " + countDivisors(360) + " divisors in total");

        say("");
        say("4) Divisor counts 1..16  (perfect squares have an odd count)");
        StringBuilder numbers = new StringBuilder("   n:      ");
        StringBuilder counts = new StringBuilder("   count:  ");
        StringBuilder marks = new StringBuilder("   square: ");
        for (int n = 1; n <= 16; n++) {
            numbers.append(fmt("%3d", n));
            counts.append(fmt("%3d", countDivisors(n)));
            marks.append(isqrt(n) * isqrt(n) == n ? "  ^" : "   ");
        }
        say(numbers.toString());
        say(counts.toString());
        say(marks.toString().replaceAll("\\s+$", ""));

        say("");
        say("5) The int overflow trap in  d * d <= n");
        int d = 46_341;
        say("   int d = 46341;  d * d = " + (d * d)
                + "   <- negative! (really 2,147,488,281)");
        say("   (long) d * d   = " + ((long) d * d));

        say("");
        say("6) Divisibility rules with digit sums");
        long[] ruleTests = {4185, 123456, 999_999_999L};
        for (long n : ruleTests) {
            say(fmt("   %,13d  digit sum = %2d   divisible by 3: %-5b  by 9: %b",
                    n, digitSum(n), n % 3 == 0, n % 9 == 0));
        }

        say("");
        say("7) Counting multiples without a loop");
        say("   multiples of 7 in [1, 100]  = " + countMultiples(1, 100, 7));
        say("   multiples of 7 in [50, 100] = " + countMultiples(50, 100, 7));
        long both = countMultiples(1, 1000, 3) + countMultiples(1, 1000, 5)
                - countMultiples(1, 1000, 15);
        say("   numbers in [1, 1000] divisible by 3 or 5 = 333 + 200 - 66 = " + both);

        say("");
        say("8) k-th smallest divisor (LeetCode 1492)");
        say("   n = 12, k = 3  -> " + kthFactor(12, 3));
        say("   n = 36, k = 7  -> " + kthFactor(36, 7));
        say("   n = 4,  k = 4  -> " + kthFactor(4, 4));

        say("");
        say("9) Perfect numbers up to 10,000 (sum of proper divisors = n)");
        StringBuilder perfect = new StringBuilder("  ");
        for (long n = 1; n <= 10_000; n++) {
            if (isPerfect(n)) {
                perfect.append(" ").append(n);
            }
        }
        say(perfect.toString());

        say("");
        say("10) Divisor counts of every number up to n, by visiting multiples");
        int[] upTo12 = divisorCountsUpTo(12);
        StringBuilder row = new StringBuilder("   counts for 1..12: ");
        for (int n = 1; n <= 12; n++) {
            row.append(upTo12[n]).append(n < 12 ? " " : "");
        }
        say(row + "   steps = " + steps);
        int[] upToMillion = divisorCountsUpTo(1_000_000);
        int best = 1;
        for (int n = 1; n <= 1_000_000; n++) {
            if (upToMillion[n] > upToMillion[best]) {
                best = n;
            }
        }
        say(fmt("   n = 1,000,000: steps = %,d  (about n ln n, not n * sqrt(n))", steps));
        say(fmt("   most divisors below a million: %,d has %d divisors",
                best, upToMillion[best]));

        say("");
        say("11) Bulb Switcher (LeetCode 319): bulbs on = perfect squares <= n");
        say("   n = 10  -> " + isqrt(10) + " bulbs on (1, 4, 9)");
        say("   n = 100 -> " + isqrt(100) + " bulbs on");

        say("");
        say("12) Closest factor pair, walking down from sqrt(x) (LeetCode 1362)");
        long[] pairTests = {9, 10, 124, 125, 1000, 1001};
        for (long x : pairTests) {
            long[] p = closestPair(x);
            say(fmt("   %4d = %3d x %-4d (difference %d)", x, p[0], p[1], p[1] - p[0]));
        }
    }
}
