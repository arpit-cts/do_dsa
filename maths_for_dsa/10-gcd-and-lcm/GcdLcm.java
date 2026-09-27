import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GcdLcm {
    static long gcdLong(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    static int toIntGcd(long gcd) {
        if (gcd > Integer.MAX_VALUE) {
            throw new ArithmeticException("GCD does not fit in int");
        }
        return (int) gcd;
    }

    static int gcdRecursive(int a, int b) {
        return toIntGcd(gcdRecursiveLong(a, b));
    }

    static long gcdRecursiveLong(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (b == 0) {
            return a;
        }
        return gcdRecursiveLong(b, a % b);
    }

    static int gcdIterative(int a, int b) {
        return toIntGcd(gcdLong(a, b));
    }

    static long lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return Math.abs((long) a / gcdLong(a, b) * b);
    }

    static int gcdOfArray(int[] numbers) {
        int answer = 0;
        for (int number : numbers) {
            answer = gcdIterative(answer, number);
        }
        return answer;
    }

    static long lcmOfArray(int[] numbers) {
        long answer = 1;
        for (int number : numbers) {
            if (number == 0) {
                return 0;
            }
            long common = gcdLong(answer, number);
            answer = Math.abs(answer / common * number);
        }
        return answer;
    }

    static String reduceFraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }
        int divisor = gcdIterative(numerator, denominator);
        numerator /= divisor;
        denominator /= divisor;
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        return numerator + "/" + denominator;
    }

    static String addFractions(int a, int b, int c, int d) {
        long commonDenominator = lcm(b, d);
        long numerator = (long) a * (commonDenominator / b)
                + (long) c * (commonDenominator / d);
        return reduceFraction((int) numerator, (int) commonDenominator);
    }

    static String normalizedSlope(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        if (dx == 0) {
            return "1/0";
        }
        if (dy == 0) {
            return "0/1";
        }
        int divisor = gcdIterative(dx, dy);
        dx /= divisor;
        dy /= divisor;
        if (dx < 0) {
            dx = -dx;
            dy = -dy;
        }
        return dy + "/" + dx;
    }

    static int[] extendedGcd(int a, int b) {
        if (b == 0) {
            return new int[] {Math.abs(a), a < 0 ? -1 : 1, 0};
        }
        int[] smaller = extendedGcd(b, a % b);
        int gcd = smaller[0];
        int x1 = smaller[1];
        int y1 = smaller[2];
        int x = y1;
        int y = x1 - (a / b) * y1;
        return new int[] {gcd, x, y};
    }

    static boolean canMeasureWater(int jug1, int jug2, int target) {
        if (target < 0) {
            return false;
        }
        if (target == 0) {
            return true;
        }
        if (target > jug1 + jug2) {
            return false;
        }
        return target % gcdIterative(jug1, jug2) == 0;
    }

    static String gcdOfStrings(String first, String second) {
        if (!(first + second).equals(second + first)) {
            return "";
        }
        int length = gcdIterative(first.length(), second.length());
        return first.substring(0, length);
    }

    static boolean hasGroupsSizeX(int[] deck) {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (int card : deck) {
            counts.put(card, counts.getOrDefault(card, 0) + 1);
        }
        int groupSize = 0;
        for (int count : counts.values()) {
            groupSize = gcdIterative(groupSize, count);
        }
        return groupSize >= 2;
    }

    static int nthMagicalNumber(int n, int a, int b) {
        int mod = 1_000_000_007;
        long low = Math.min(a, b);
        long high = low * n;
        long both = lcm(a, b);
        while (low < high) {
            long mid = low + (high - low) / 2;
            long count = mid / a + mid / b - mid / both;
            if (count >= n) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return (int) (low % mod);
    }

    static boolean isGoodArray(int[] numbers) {
        return gcdOfArray(numbers) == 1;
    }

    static int countSubarraysWithGcdK(int[] numbers, int k) {
        int answer = 0;
        for (int start = 0; start < numbers.length; start++) {
            int currentGcd = 0;
            for (int end = start; end < numbers.length; end++) {
                currentGcd = gcdIterative(currentGcd, numbers[end]);
                if (currentGcd == k) {
                    answer++;
                }
                if (currentGcd < k || currentGcd % k != 0) {
                    break;
                }
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        System.out.println("gcdRecursive(48, 18) = " + gcdRecursive(48, 18));
        System.out.println("gcdIterative(-48, 18) = " + gcdIterative(-48, 18));
        System.out.println("lcm(12, 18) = " + lcm(12, 18));

        int[] numbers = {12, 18, 30};
        System.out.println("gcd " + Arrays.toString(numbers)
                + " = " + gcdOfArray(numbers));
        System.out.println("lcm " + Arrays.toString(numbers)
                + " = " + lcmOfArray(numbers));

        System.out.println("reduce 42/56 = " + reduceFraction(42, 56));
        System.out.println("1/6 + 1/4 = " + addFractions(1, 6, 1, 4));
        System.out.println("slope (1, 1) to (5, 3) = " + normalizedSlope(1, 1, 5, 3));

        int[] eg = extendedGcd(30, 12);
        System.out.println("extendedGcd(30, 12): gcd=" + eg[0]
                + ", x=" + eg[1] + ", y=" + eg[2]);
        System.out.println("30*x + 12*y = " + (30 * eg[1] + 12 * eg[2]));

        System.out.println("canMeasureWater(3, 5, 4) = " + canMeasureWater(3, 5, 4));
        System.out.println("gcdOfStrings(ABCABC, ABC) = "
                + gcdOfStrings("ABCABC", "ABC"));
        int[] deck = {1, 1, 2, 2, 2, 2};
        System.out.println("hasGroupsSizeX([1,1,2,2,2,2]) = " + hasGroupsSizeX(deck));
        System.out.println("nthMagicalNumber(5, 2, 4) = " + nthMagicalNumber(5, 2, 4));
        System.out.println("isGoodArray([12, 5, 7, 23]) = "
                + isGoodArray(new int[] {12, 5, 7, 23}));
        System.out.println("countSubarraysWithGcdK([9,3,1,2,6,3], 3) = "
                + countSubarraysWithGcdK(new int[] {9, 3, 1, 2, 6, 3}, 3));
        System.out.println("BigInteger gcd = "
                + new BigInteger("123456789123456789")
                        .gcd(new BigInteger("987654321")));
    }
}
