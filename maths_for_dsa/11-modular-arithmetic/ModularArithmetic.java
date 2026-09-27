import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModularArithmetic {
    static final long MOD = 1_000_000_007L;

    static int positiveMod(int number, int mod) {
        return ((number % mod) + mod) % mod;
    }

    static long addMod(long a, long b, long mod) {
        return ((a % mod) + (b % mod)) % mod;
    }

    static long subtractMod(long a, long b, long mod) {
        return ((a % mod) - (b % mod) + mod) % mod;
    }

    static long multiplyMod(long a, long b, long mod) {
        return ((a % mod) * (b % mod)) % mod;
    }

    static double fastPower(double x, int n) {
        long exponent = n;
        if (exponent < 0) {
            x = 1.0 / x;
            exponent = -exponent;
        }
        double answer = 1.0;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                answer *= x;
            }
            x *= x;
            exponent >>= 1;
        }
        return answer;
    }

    static long powerMod(long base, long exponent, long mod) {
        base = positiveMod((int) (base % mod), (int) mod);
        long answer = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                answer = answer * base % mod;
            }
            base = base * base % mod;
            exponent >>= 1;
        }
        return answer;
    }

    static int superPow(int a, int[] digits) {
        int mod = 1337;
        int answer = 1;
        for (int digit : digits) {
            answer = (int) powerMod(answer, 10, mod)
                    * (int) powerMod(a, digit, mod) % mod;
        }
        return answer;
    }

    static int countGoodNumbers(long n) {
        long evenPositions = (n + 1) / 2;
        long oddPositions = n / 2;
        return (int) (powerMod(5, evenPositions, MOD)
                * powerMod(4, oddPositions, MOD) % MOD);
    }

    static int[] rotateRight(int[] numbers, int k) {
        int n = numbers.length;
        int[] answer = new int[n];
        k %= n;
        for (int i = 0; i < n; i++) {
            answer[(i + k) % n] = numbers[i];
        }
        return answer;
    }

    static int subarraysDivByK(int[] numbers, int k) {
        Map<Integer, Integer> frequency = new HashMap<Integer, Integer>();
        frequency.put(0, 1);
        int prefix = 0;
        int answer = 0;
        for (int number : numbers) {
            prefix = positiveMod(prefix + number, k);
            int seen = frequency.getOrDefault(prefix, 0);
            answer += seen;
            frequency.put(prefix, seen + 1);
        }
        return answer;
    }

    static boolean checkSubarraySum(int[] numbers, int k) {
        Map<Integer, Integer> firstIndex = new HashMap<Integer, Integer>();
        firstIndex.put(0, -1);
        int prefix = 0;
        for (int i = 0; i < numbers.length; i++) {
            prefix = positiveMod(prefix + numbers[i], k);
            if (firstIndex.containsKey(prefix)) {
                if (i - firstIndex.get(prefix) >= 2) {
                    return true;
                }
            } else {
                firstIndex.put(prefix, i);
            }
        }
        return false;
    }

    static int smallestRepunitDivByK(int k) {
        if (k % 2 == 0 || k % 5 == 0) {
            return -1;
        }
        int remainder = 0;
        for (int length = 1; length <= k; length++) {
            remainder = (remainder * 10 + 1) % k;
            if (remainder == 0) {
                return length;
            }
        }
        return -1;
    }

    static int strStrRabinKarp(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m == 0) {
            return 0;
        }
        if (m > n) {
            return -1;
        }
        long base = 256;
        long mod = 1_000_000_007L;
        long highestPower = 1;
        long needleHash = 0;
        long windowHash = 0;
        for (int i = 0; i < m; i++) {
            if (i > 0) {
                highestPower = highestPower * base % mod;
            }
            needleHash = (needleHash * base + needle.charAt(i)) % mod;
            windowHash = (windowHash * base + haystack.charAt(i)) % mod;
        }
        for (int start = 0; start <= n - m; start++) {
            if (needleHash == windowHash
                    && haystack.substring(start, start + m).equals(needle)) {
                return start;
            }
            if (start < n - m) {
                long remove = haystack.charAt(start) * highestPower % mod;
                windowHash = (windowHash - remove + mod) % mod;
                windowHash = (windowHash * base + haystack.charAt(start + m)) % mod;
            }
        }
        return -1;
    }

    static List<String> repeatedDnaSequences(String s) {
        Set<String> seen = new HashSet<String>();
        Set<String> repeated = new HashSet<String>();
        for (int i = 0; i + 10 <= s.length(); i++) {
            String piece = s.substring(i, i + 10);
            if (!seen.add(piece)) {
                repeated.add(piece);
            }
        }
        List<String> answer = new ArrayList<String>(repeated);
        answer.sort(String::compareTo);
        return answer;
    }

    static int lastDigitOfPower(int base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        int result = 1;
        int last = positiveMod(base, 10);
        for (int i = 0; i < exponent; i++) {
            result = result * last % 10;
        }
        return result;
    }

    static int digitSumMod9(String digits) {
        int remainder = 0;
        for (int i = 0; i < digits.length(); i++) {
            remainder = (remainder + digits.charAt(i) - '0') % 9;
        }
        return remainder;
    }

    static int numPrimeArrangements(int n) {
        boolean[] composite = new boolean[n + 1];
        int primes = 0;
        for (int number = 2; number <= n; number++) {
            if (!composite[number]) {
                primes++;
                for (long multiple = (long) number * number;
                        multiple <= n;
                        multiple += number) {
                    composite[(int) multiple] = true;
                }
            }
        }
        long answer = 1;
        for (int i = 2; i <= primes; i++) {
            answer = answer * i % MOD;
        }
        for (int i = 2; i <= n - primes; i++) {
            answer = answer * i % MOD;
        }
        return (int) answer;
    }

    public static void main(String[] args) {
        System.out.println("positiveMod(-7, 5) = " + positiveMod(-7, 5));
        System.out.println("addMod(8, 9, 7) = " + addMod(8, 9, 7));
        System.out.println("subtractMod(3, 8, 7) = " + subtractMod(3, 8, 7));
        System.out.println("multiplyMod(123456789, 987654321, MOD) = "
                + multiplyMod(123456789, 987654321, MOD));

        System.out.println("fastPower(2, 10) = " + fastPower(2, 10));
        System.out.println("powerMod(3, 13, 1_000_000_007) = "
                + powerMod(3, 13, MOD));
        System.out.println("superPow(2, [1,0]) = " + superPow(2, new int[] {1, 0}));
        System.out.println("countGoodNumbers(4) = " + countGoodNumbers(4));

        int[] rotated = rotateRight(new int[] {1, 2, 3, 4, 5}, 2);
        System.out.println("rotateRight([1,2,3,4,5], 2) = " + Arrays.toString(rotated));
        System.out.println("subarraysDivByK([4,5,0,-2,-3,1], 5) = "
                + subarraysDivByK(new int[] {4, 5, 0, -2, -3, 1}, 5));
        System.out.println("checkSubarraySum([23,2,4,6,7], 6) = "
                + checkSubarraySum(new int[] {23, 2, 4, 6, 7}, 6));
        System.out.println("smallestRepunitDivByK(3) = " + smallestRepunitDivByK(3));

        System.out.println("strStrRabinKarp(mississippi, issip) = "
                + strStrRabinKarp("mississippi", "issip"));
        System.out.println("repeatedDnaSequences(...) = "
                + repeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"));
        System.out.println("lastDigitOfPower(7, 222) = " + lastDigitOfPower(7, 222));
        System.out.println("digitSumMod9(9876543210) = " + digitSumMod9("9876543210"));
        System.out.println("numPrimeArrangements(5) = " + numPrimeArrangements(5));
    }
}
