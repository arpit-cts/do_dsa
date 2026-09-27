import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Chapter 09 · Prime Numbers — runnable demos.
// Run from this folder:  java Primes.java
public class Primes {

    static long steps; // counts loop rounds, to compare methods

    // Try every d from 2 to n - 1. About n steps.
    static boolean isPrimeSlow(long n) {
        steps = 0;
        if (n < 2) {
            return false;
        }
        for (long d = 2; d < n; d++) {
            steps++;
            if (n % d == 0) {
                return false;
            }
        }
        return true;
    }

    // Trial division up to sqrt(n): a composite n always has a divisor d with d * d <= n.
    static boolean isPrime(long n) {
        steps = 0;
        if (n < 2) {
            return false;
        }
        for (long d = 2; d * d <= n; d++) {
            steps++;
            if (n % d == 0) {
                return false;
            }
        }
        return true;
    }

    // Same idea, but only tries 2, 3 and then numbers of the form 6k - 1 and 6k + 1.
    static boolean isPrime6k(long n) {
        steps = 0;
        if (n < 2) {
            return false;
        }
        if (n < 4) {
            return true;                       // 2 and 3
        }
        steps += 2;
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (long d = 5; d * d <= n; d += 6) { // d = 6k - 1 and d + 2 = 6k + 1
            steps += 2;
            if (n % d == 0 || n % (d + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    // Sieve of Eratosthenes: isPrime[x] for every x from 0 to n.
    static boolean[] sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];
        for (int x = 2; x <= n; x++) {
            isPrime[x] = true;
        }
        steps = 0;
        for (int p = 2; (long) p * p <= n; p++) {
            if (isPrime[p]) {
                // start at p * p: smaller multiples were already crossed out
                for (int m = p * p; m <= n; m += p) {
                    isPrime[m] = false;
                    steps++;
                }
            }
        }
        return isPrime;
    }

    // LeetCode 204: how many primes are strictly less than n?
    static int countPrimesBelow(int n) {
        if (n < 3) {
            return 0;
        }
        boolean[] isPrime = sieve(n - 1);
        int count = 0;
        for (boolean b : isPrime) {
            if (b) {
                count++;
            }
        }
        return count;
    }

    // Prime factorization by trial division. Returns pairs {prime, exponent}.
    static List<long[]> factorize(long n) {
        List<long[]> factors = new ArrayList<>();
        steps = 0;
        for (long d = 2; d * d <= n; d++) {
            steps++;
            if (n % d == 0) {
                int exponent = 0;
                while (n % d == 0) {           // divide d out completely
                    n /= d;
                    exponent++;
                }
                factors.add(new long[] {d, exponent});
            }
        }
        if (n > 1) {
            factors.add(new long[] {n, 1});    // what is left is a prime bigger than sqrt
        }
        return factors;
    }

    static String recipe(List<long[]> factors) {
        StringBuilder sb = new StringBuilder();
        for (long[] f : factors) {
            if (sb.length() > 0) {
                sb.append(" x ");
            }
            sb.append(f[0]);
            if (f[1] > 1) {
                sb.append("^").append(f[1]);
            }
        }
        return sb.toString();
    }

    // Number of divisors from the recipe: (a1 + 1)(a2 + 1)...
    static long divisorCount(List<long[]> factors) {
        long count = 1;
        for (long[] f : factors) {
            count *= f[1] + 1;
        }
        return count;
    }

    // Smallest prime factor of every x from 0 to n.
    static int[] smallestPrimeFactor(int n) {
        int[] spf = new int[n + 1];
        for (int x = 2; x <= n; x++) {
            if (spf[x] == 0) {                 // nobody marked x, so x is prime
                for (int m = x; m <= n; m += x) {
                    if (spf[m] == 0) {
                        spf[m] = x;
                    }
                }
            }
        }
        return spf;
    }

    // LeetCode 172: trailing zeros of n! = how many factors 5 are hidden in 1..n.
    static long trailingZerosOfFactorial(long n) {
        long zeros = 0;
        for (long power = 5; power <= n; power *= 5) {
            zeros += n / power;
        }
        return zeros;
    }

    // LeetCode 263: only the prime factors 2, 3 and 5 are allowed.
    static boolean isUgly(long n) {
        if (n <= 0) {
            return false;
        }
        for (long p : new long[] {2, 3, 5}) {
            while (n % p == 0) {
                n /= p;
            }
        }
        return n == 1;
    }

    static String fmt(String pattern, Object... values) {
        return String.format(Locale.ROOT, pattern, values);
    }

    static void say(String line) {
        System.out.println(line);
    }

    public static void main(String[] args) {
        say("1) The primes below 100 (there are 25)");
        boolean[] small = sieve(100);
        StringBuilder line = new StringBuilder("  ");
        for (int x = 2; x < 100; x++) {
            if (small[x]) {
                line.append(fmt(" %2d", x));
            }
            if (x == 50) {
                say(line.toString());
                line = new StringBuilder("  ");
            }
        }
        say(line.toString());

        say("");
        say("2) Testing one number: slow (d < n), sqrt(n), and 6k +- 1");
        String t = "   %-15s %-6s %15s %12s %10s";
        say(fmt(t, "n", "prime?", "slow steps", "sqrt steps", "6k steps"));
        long[] tests = {1, 2, 91, 97, 7_919, 1_000_000, 1_000_000_007L};
        for (long n : tests) {
            boolean a = isPrime(n);
            long sqrtSteps = steps;
            boolean b = isPrime6k(n);
            long sixSteps = steps;
            String slowSteps;
            if (n <= 1_000_000) {
                boolean c = isPrimeSlow(n);
                slowSteps = fmt("%,d", steps) + (a == b && b == c ? "" : " ?!");
            } else {
                slowSteps = fmt("%,d*", n - 2);
            }
            say(fmt(t, fmt("%,d", n), a ? "yes" : "no", slowSteps, fmt("%,d", sqrtSteps),
                    fmt("%,d", sixSteps)));
        }
        say("   * not run: about a billion steps");

        say("");
        say("3) Sieve of Eratosthenes: how many crossings (marks)?");
        int[] sizes = {100, 10_000, 1_000_000, 10_000_000};
        for (int n : sizes) {
            boolean[] isPrime = sieve(n);
            int count = 0;
            for (boolean b : isPrime) {
                if (b) {
                    count++;
                }
            }
            say(fmt("   n = %,-12d primes = %,9d   marks = %,12d   marks / n = %.2f",
                    n, count, steps, (double) steps / n));
        }

        say("");
        say("4) How many primes? pi(n) compared with the estimate n / ln n");
        boolean[] big = sieve(10_000_000);
        long power = 10;
        int count = 0;
        for (int x = 2; x <= 10_000_000; x++) {
            if (big[x]) {
                count++;
            }
            if (x == power) {
                String row = "   n = %,-11d pi(n) = %,8d"
                        + "   n / ln n = %,8.0f   1 in %.1f is prime";
                say(fmt(row,
                        x, count, x / Math.log(x), (double) x / count));
                power *= 10;
            }
        }
        int below = countPrimesBelow(5_000_000);
        say(fmt("   LeetCode 204: primes below 5,000,000 = %,d", below));

        say("");
        say("5) Prime factorization (trial division, divide each factor out)");
        long[] numbers = {360, 84, 1001, 194, 97, 2_147_483_647L, 1_234_567_890L,
                600_851_475_143L};
        for (long n : numbers) {
            List<long[]> f = factorize(n);
            say(fmt("   %,17d = %-26s divisors = %3d   loop steps = %,d",
                    n, recipe(f), divisorCount(f), steps));
        }

        say("");
        say("6) Smallest prime factor sieve: factorize 84 by dividing by spf[x]");
        int[] spf = smallestPrimeFactor(100);
        int x = 84;
        StringBuilder chain = new StringBuilder("   84");
        while (x > 1) {
            chain.append(fmt(" -(/%d)-> %d", spf[x], x / spf[x]));
            x /= spf[x];
        }
        say(chain.toString());

        say("");
        say("7) Trailing zeros of n! (LeetCode 172): n/5 + n/25 + n/125 + ...");
        long[] facts = {5, 10, 25, 100, 1000, 1_000_000_000L};
        for (long n : facts) {
            say(fmt("   %,13d! ends with %,11d zeros", n, trailingZerosOfFactorial(n)));
        }

        say("");
        say("8) Ugly numbers (LeetCode 263): only 2, 3 and 5 allowed");
        long[] uglyTests = {1, 6, 8, 14, 30, 49};
        StringBuilder ugly = new StringBuilder("  ");
        for (long n : uglyTests) {
            ugly.append(fmt(" %d:%s", n, isUgly(n) ? "yes" : "no"));
        }
        say(ugly.toString());

        say("");
        say("9) Every prime above 3 is 6k - 1 or 6k + 1 (checked up to 10,000,000)");
        int[] byRemainder = new int[6];
        for (int p = 5; p <= 10_000_000; p++) {
            if (big[p]) {
                byRemainder[p % 6]++;
            }
        }
        say(fmt("   remainder mod 6:  0: %d  1: %,d  2: %d  3: %d  4: %d  5: %,d",
                byRemainder[0], byRemainder[1], byRemainder[2], byRemainder[3],
                byRemainder[4], byRemainder[5]));
    }
}
