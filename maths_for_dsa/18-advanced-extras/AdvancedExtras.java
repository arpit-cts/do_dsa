import java.math.BigInteger;
import java.util.Arrays;
import java.util.Locale;

// Chapter 18 · Advanced Extras — runnable demos.
// Run from this folder:  java AdvancedExtras.java
public class AdvancedExtras {

    static final long MOD = 1_000_000_007L;

    // ---------------------------------------------------------------- 1. Matrix power

    static final long[][] FIB = {{1, 1}, {1, 0}};
    static final long[][] TRIB = {{1, 1, 1}, {1, 0, 0}, {0, 1, 0}};
    static long multiplications;   // how many matrix multiplications we did

    // c = a × b. With mod = 0 the answer is exact (and throws if a long overflows).
    static long[][] multiply(long[][] a, long[][] b, long mod) {
        multiplications++;
        int n = a.length;
        long[][] c = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                for (int j = 0; j < n; j++) {
                    if (mod == 0) {
                        long product = Math.multiplyExact(a[i][k], b[k][j]);
                        c[i][j] = Math.addExact(c[i][j], product);
                    } else {
                        c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % mod;
                    }
                }
            }
        }
        return c;
    }

    static long[][] identity(int n) {
        long[][] id = new long[n][n];
        for (int i = 0; i < n; i++) {
            id[i][i] = 1;
        }
        return id;
    }

    // Fast power for matrices: the same squaring trick as fast power for numbers.
    static long[][] power(long[][] m, long exponent, long mod) {
        long[][] result = identity(m.length);
        long[][] base = m;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = multiply(result, base, mod);
            }
            exponent >>= 1;
            if (exponent > 0) {                 // skip the last square: it is never used
                base = multiply(base, base, mod);
            }
        }
        return result;
    }

    // [[1,1],[1,0]]^n = [[F(n+1), F(n)], [F(n), F(n-1)]], so F(n) sits at [0][1].
    static long fibMatrix(long n, long mod) {
        return power(FIB, n, mod)[0][1];
    }

    static long fibLoop(int n) {
        long a = 0;
        long b = 1;
        for (int i = 0; i < n; i++) {
            long next = a + b;
            a = b;
            b = next;
        }
        return a;
    }

    // Tribonacci (LeetCode 1137): T(0) = 0, T(1) = 1, T(2) = 1.
    // [T(n+2), T(n+1), T(n)] = TRIB^n × [1, 1, 0],
    // so T(n) = (row 2 of TRIB^n) times [1, 1, 0].
    static long tribonacci(int n) {
        long[][] p = power(TRIB, n, 0);
        return p[2][0] + p[2][1];
    }

    // ---------------------------------------------------------------- 2. Games

    // "Take 1..maxTake stones; whoever takes the last stone wins."
    // win[s] is true when the player to move can force a win with s stones left.
    static boolean[] takeAwayTable(int n, int maxTake) {
        boolean[] win = new boolean[n + 1];     // win[0] = false: no stones, you lost
        for (int s = 1; s <= n; s++) {
            for (int take = 1; take <= maxTake && take <= s; take++) {
                if (!win[s - take]) {           // I can leave my friend a losing pile
                    win[s] = true;
                    break;
                }
            }
        }
        return win;
    }

    static int nimSum(int[] piles) {
        int x = 0;
        for (int p : piles) {
            x ^= p;
        }
        return x;
    }

    // A winning move in Nim: make the XOR of all piles 0.
    // Returns {pile, new size}, or null when there is none.
    static int[] nimWinningMove(int[] piles) {
        int x = nimSum(piles);
        if (x == 0) {
            return null;
        }
        for (int i = 0; i < piles.length; i++) {
            int target = piles[i] ^ x;
            if (target < piles[i]) {
                return new int[] {i, target};
            }
        }
        return null;                            // never happens when x != 0
    }

    // LeetCode 1025 Divisor Game: pick x with 0 < x < n and n % x == 0,
    // then replace n by n - x.
    static boolean[] divisorGameTable(int n) {
        boolean[] win = new boolean[n + 1];
        for (int s = 2; s <= n; s++) {
            for (int x = 1; x < s; x++) {
                if (s % x == 0 && !win[s - x]) {
                    win[s] = true;
                    break;
                }
            }
        }
        return win;
    }

    // LeetCode 486 Predict the Winner: best (my score - your score) on nums[i..j].
    static int bestScoreDifference(int[] nums) {
        int n = nums.length;
        int[][] diff = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            diff[i][i] = nums[i];
            for (int j = i + 1; j < n; j++) {
                diff[i][j] = Math.max(nums[i] - diff[i + 1][j], nums[j] - diff[i][j - 1]);
            }
        }
        return diff[0][n - 1];
    }

    // ---------------------------------------------------------------- 3. Euler's totient

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long phiByCounting(long n) {
        long count = 0;
        for (long k = 1; k <= n; k++) {
            if (gcd(k, n) == 1) {
                count++;
            }
        }
        return count;
    }

    // phi(n) = n × (1 - 1/p) for every distinct prime p of n. O(sqrt(n)).
    static long phi(long n) {
        long result = n;
        for (long p = 2; p * p <= n; p++) {
            if (n % p == 0) {
                while (n % p == 0) {
                    n /= p;
                }
                result -= result / p;           // result = result × (1 - 1/p)
            }
        }
        if (n > 1) {
            result -= result / n;               // one prime bigger than sqrt is left
        }
        return result;
    }

    // phi of every number up to n, sieve style.
    static int[] phiSieve(int n) {
        int[] phi = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            phi[i] = i;
        }
        for (int p = 2; p <= n; p++) {
            if (phi[p] == p) {                  // untouched, so p is prime
                for (int m = p; m <= n; m += p) {
                    phi[m] -= phi[m] / p;
                }
            }
        }
        return phi;
    }

    static long powMod(long base, long exponent, long mod) {
        long result = 1 % mod;
        base %= mod;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = mulMod(result, base, mod);
            }
            base = mulMod(base, base, mod);
            exponent >>= 1;
        }
        return result;
    }

    // ------------------------------------------------------- 4. Chinese remainder

    // Returns {g, x, y} with a*x + b*y = g = gcd(a, b).
    static long[] extendedGcd(long a, long b) {
        if (b == 0) {
            return new long[] {a, 1, 0};
        }
        long[] next = extendedGcd(b, a % b);
        return new long[] {next[0], next[2], next[1] - (a / b) * next[2]};
    }

    // Solve x = r[i] (mod m[i]) for pairwise coprime m[i]. Returns {x, M}.
    static long[] crt(long[] r, long[] m) {
        long x = 0;
        long bigM = 1;
        for (int i = 0; i < r.length; i++) {
            // find t with x + bigM * t = r[i] (mod m[i])
            long inverse = Math.floorMod(extendedGcd(bigM % m[i], m[i])[1], m[i]);
            long t = mulMod(Math.floorMod(r[i] - x, m[i]), inverse, m[i]);
            x += bigM * t;
            bigM *= m[i];
            x = Math.floorMod(x, bigM);
        }
        return new long[] {x, bigM};
    }

    // ---------------------------------------------------------------- 5. Miller-Rabin

    static final long[] BASES = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};

    // (a * b) % m without overflow, using BigInteger for the big product.
    static long mulMod(long a, long b, long m) {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b))
                .mod(BigInteger.valueOf(m)).longValue();
    }

    static boolean fermatSaysPrime(long n, long a) {
        return powMod(a, n - 1, n) == 1;
    }

    // One round: false means "a proves n is composite".
    static boolean passesRound(long n, long a, long d, int s) {
        long x = powMod(a, d, n);
        if (x == 1 || x == n - 1) {
            return true;
        }
        for (int r = 1; r < s; r++) {
            x = mulMod(x, x, n);
            if (x == n - 1) {
                return true;
            }
        }
        return false;
    }

    // Always correct for every long: these 12 bases have no liars below 3.3 × 10^24.
    static boolean isPrimeMillerRabin(long n) {
        if (n < 2) {
            return false;
        }
        for (long p : BASES) {
            if (n % p == 0) {
                return n == p;
            }
        }
        long d = n - 1;
        int s = 0;
        while ((d & 1) == 0) {                  // n - 1 = d × 2^s with d odd
            d >>= 1;
            s++;
        }
        for (long a : BASES) {
            if (!passesRound(n, a, d, s)) {
                return false;
            }
        }
        return true;
    }

    // ---------------------------------------------------------------- 6. Graph facts

    static int[] parent;

    static int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    // LeetCode 684 idea: the first edge whose ends are already connected closes a cycle.
    static int[] redundantEdge(int n, int[][] edges) {
        parent = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
        for (int[] e : edges) {
            int a = find(e[0]);
            int b = find(e[1]);
            if (a == b) {
                return e;
            }
            parent[a] = b;
        }
        return null;
    }

    // ---------------------------------------------------------------- output

    static String fmt(String pattern, Object... values) {
        return String.format(Locale.ROOT, pattern, values);
    }

    static void say(String line) {
        System.out.println(line);
    }

    public static void main(String[] args) {
        say("1) Matrix power: Fibonacci with [[1,1],[1,0]]^n");
        for (int n : new int[] {10, 50, 90}) {
            multiplications = 0;
            long viaMatrix = fibMatrix(n, 0);
            say(fmt("   F(%d) = %,d   loop agrees: %b   matrix multiplications: %d",
                    n, viaMatrix, viaMatrix == fibLoop(n), multiplications));
        }
        for (long n : new long[] {1_000_000L, 1_000_000_000_000_000_000L}) {
            multiplications = 0;
            long value = fibMatrix(n, MOD);
            say(fmt("   F(%,d) mod 1e9+7 = %d   (%d matrix multiplications)",
                    n, value, multiplications));
        }
        say("   Tribonacci (LeetCode 1137): T(25) = " + tribonacci(25)
                + ", T(37) = " + tribonacci(37));

        say("");
        say("2) Games: take 1, 2 or 3 stones; the last stone wins");
        say("   (W = the player to move can win, L = the player to move loses)");
        boolean[] win = takeAwayTable(16, 3);
        StringBuilder stones = new StringBuilder("   stones: ");
        StringBuilder result = new StringBuilder("   result: ");
        for (int s = 0; s <= 16; s++) {
            stones.append(fmt("%3d", s));
            result.append(win[s] ? "  W" : "  L");
        }
        say(stones.toString());
        say(result.toString());
        int[][] games = {{3, 4, 5}, {1, 2, 3}, {7, 7}, {1, 4, 9, 12}};
        for (int[] piles : games) {
            int[] move = nimWinningMove(piles);
            String advice = move == null ? "every move loses: first player LOSES"
                    : fmt("first player WINS: make pile %d size %d",
                            move[0] + 1, move[1]);
            say(fmt("   Nim %-13s xor = %2d -> %s", Arrays.toString(piles), nimSum(piles),
                    advice));
        }
        boolean[] divisor = divisorGameTable(12);
        StringBuilder dg = new StringBuilder();
        dg.append("   Divisor Game (LeetCode 1025), n = 1..12: ");
        for (int n = 1; n <= 12; n++) {
            dg.append(divisor[n] ? "W" : "L");
        }
        say(dg + "   (W exactly when n is even)");
        say("   Predict the Winner (LeetCode 486): [1, 5, 2] -> difference "
                + bestScoreDifference(new int[] {1, 5, 2}) + ",  [1, 5, 233, 7] -> "
                + bestScoreDifference(new int[] {1, 5, 233, 7}));

        say("");
        say("3) Euler's totient phi(n): how many of 1..n share no factor with n");
        for (long n : new long[] {12, 36, 97, 100, 1_000, 1_000_000_007L}) {
            String check = n <= 1_000 ? fmt("(counting: %d)", phiByCounting(n)) : "";
            say(fmt("   phi(%,d) = %,d %s", n, phi(n), check).replaceAll("\\s+$", ""));
        }
        int[] table = phiSieve(100);
        long fractions4 = 0;
        long fractions100 = 0;
        for (int d = 2; d <= 100; d++) {
            fractions100 += table[d];
            if (d <= 4) {
                fractions4 += table[d];
            }
        }
        say(fmt("   Simplified Fractions (LeetCode 1447): n = 4 -> %d, n = 100 -> %,d",
                fractions4, fractions100));
        say(fmt("   Euler's theorem: 3^phi(10) mod 10 = 3^4 mod 10 = %d",
                powMod(3, 4, 10)));
        say(fmt("   so the inverse of 3 mod 10 = 3^(phi - 1) mod 10 = %d",
                powMod(3, 3, 10)));

        say("");
        say("4) Chinese remainder theorem");
        long[] answer = crt(new long[] {2, 3, 2}, new long[] {3, 5, 7});
        say(fmt("   x = 2 (mod 3), x = 3 (mod 5), x = 2 (mod 7)  ->  x = %d (mod %d)",
                answer[0], answer[1]));
        say("   every pair (x mod 3, x mod 5) happens exactly once for x = 0..14:");
        say("              x mod 5 = 0   1   2   3   4");
        for (int r3 = 0; r3 < 3; r3++) {
            StringBuilder row = new StringBuilder(fmt("   x mod 3 = %d:      ", r3));
            for (int r5 = 0; r5 < 5; r5++) {
                for (int x = 0; x < 15; x++) {
                    if (x % 3 == r3 && x % 5 == r5) {
                        row.append(fmt("%4d", x));
                    }
                }
            }
            say(row.toString());
        }

        say("");
        say("5) Miller-Rabin: prime tests for huge numbers");
        say(fmt("   Fermat test with a = 2 says 561 is prime: %b"
                + "   (but 561 = 3 x 11 x 17)",
                fermatSaysPrime(561, 2)));
        say("   Miller-Rabin on 561: 560 = 35 x 2^4,"
                + " so look at 2^35 mod 561 and square it:");
        StringBuilder trace = new StringBuilder("   ");
        long x = powMod(2, 35, 561);
        trace.append(x);
        for (int r = 1; r < 4; r++) {
            x = mulMod(x, x, 561);
            trace.append(" -> ").append(x);
        }
        say(trace + "  (never 560)");
        long[] tests = {561, 1_000_000_007L, 4_759_123_141L, 999_999_999_989L,
                1_000_000_000_000_000_003L, 2_305_843_009_213_693_951L};
        for (long n : tests) {
            boolean ours = isPrimeMillerRabin(n);
            boolean java = BigInteger.valueOf(n).isProbablePrime(50);
            say(fmt("   %,27d  prime: %-5b  (Java's BigInteger agrees: %b)", n, ours,
                    ours == java));
        }

        say("");
        say("6) Graph counting facts");
        int[][] edges = {{1, 2}, {1, 3}, {2, 3}, {3, 4}, {4, 5}};
        int[] degree = new int[6];
        for (int[] e : edges) {
            degree[e[0]]++;
            degree[e[1]]++;
        }
        int sum = 0;
        int odd = 0;
        for (int v = 1; v <= 5; v++) {
            sum += degree[v];
            if (degree[v] % 2 == 1) {
                odd++;
            }
        }
        say(fmt("   handshake: %d edges, degrees %s", edges.length,
                Arrays.toString(Arrays.copyOfRange(degree, 1, 6))));
        say(fmt("   degree sum = %d = 2 x %d, odd-degree nodes: %d",
                sum, edges.length, odd));
        for (long n : new long[] {5, 100, 100_000}) {
            say(fmt("   complete graph on %,d nodes: %,d edges", n, n * (n - 1) / 2));
        }
        int[] extra = redundantEdge(3, new int[][] {{1, 2}, {1, 3}, {2, 3}});
        say("   tree on 3 nodes has 2 edges; the extra edge that closes a cycle: "
                + Arrays.toString(extra));
        say(fmt("   grid 3 x 4 has %d edges, grid 1000 x 1000 has %,d edges",
                3 * 3 + 4 * 2, 1000L * 999 + 1000L * 999));
        StringBuilder cayley = new StringBuilder("   labelled trees n^(n-2), n = 2..6:");
        for (int n = 2; n <= 6; n++) {
            long count = 1;
            for (int i = 0; i < n - 2; i++) {
                count *= n;
            }
            cayley.append(" ").append(count);
        }
        say(cayley.toString());
    }
}
