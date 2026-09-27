public class IntegerDivision {
    public static void main(String[] args) {
        System.out.println("Numbers and integer division demos");
        System.out.println("isOddRemainder(-3) = " + isOddRemainder(-3));
        System.out.println("isOddSafe(-3) = " + isOddSafe(-3));
        System.out.println("isOddBit(-3) = " + isOddBit(-3));

        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
        System.out.println("Integer.MAX_VALUE + 1 = " + (Integer.MAX_VALUE + 1));
        System.out.println("50000 * 50000 as int = " + (50000 * 50000));
        System.out.println("50000 * 50000 as long = " + ((long) 50000 * 50000));
        System.out.println("Math.abs(Integer.MIN_VALUE) = "
                + Math.abs(Integer.MIN_VALUE));

        System.out.println("7 / 3 = " + (7 / 3) + ", 7 % 3 = " + (7 % 3));
        System.out.println("-7 / 3 = " + (-7 / 3) + ", -7 % 3 = " + (-7 % 3));
        System.out.println("floorDiv(-7, 3) = " + Math.floorDiv(-7, 3));
        System.out.println("floorMod(-7, 3) = " + Math.floorMod(-7, 3));

        System.out.println("ceilDivPositive(17, 5) = " + ceilDivPositive(17, 5));
        System.out.println("safeMid(2_000_000_000, 2_100_000_000) = "
                + safeMid(2_000_000_000, 2_100_000_000));
        System.out.println("unsignedMid(2_000_000_000, 2_100_000_000) = "
                + unsignedMid(2_000_000_000, 2_100_000_000));

        System.out.println("reverseChecked(123) = " + reverseChecked(123));
        System.out.println("reverseChecked(-120) = " + reverseChecked(-120));
        System.out.println("reverseChecked(1534236469) = "
                + reverseChecked(1534236469));

        System.out.println("kokoHours([3,6,7,11], 4) = "
                + kokoHours(new int[] {3, 6, 7, 11}, 4));
        System.out.println("countIntegersInclusive(-2, 3) = "
                + countIntegersInclusive(-2, 3));
        System.out.println("averageWithoutOverflow(2_000_000_000, 2_100_000_000) = "
                + averageWithoutOverflow(2_000_000_000, 2_100_000_000));
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));
        System.out.println("almostEqual(0.1 + 0.2, 0.3) = "
                + almostEqual(0.1 + 0.2, 0.3, 1e-9));
        System.out.println("(int) -3.9 = " + (int) -3.9);
    }

    static boolean isOddRemainder(int n) {
        return n % 2 == 1;
    }

    static boolean isOddSafe(int n) {
        return n % 2 != 0;
    }

    static boolean isOddBit(int n) {
        return (n & 1) == 1;
    }

    static int addExactInt(int a, int b) {
        return Math.addExact(a, b);
    }

    static int multiplyExactInt(int a, int b) {
        return Math.multiplyExact(a, b);
    }

    static boolean wouldMultiplyOverflowPositive(int a, int b) {
        return a != 0 && b > Integer.MAX_VALUE / a;
    }

    static int reverseChecked(int x) {
        int result = 0;
        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            if (result > Integer.MAX_VALUE / 10
                    || (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10
                    || (result == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }
            result = result * 10 + digit;
        }
        return result;
    }

    static int ceilDivPositive(int a, int b) {
        if (a < 0 || b <= 0) {
            throw new IllegalArgumentException("use only a >= 0 and b > 0");
        }
        if (a == 0) {
            return 0;
        }
        return 1 + (a - 1) / b;
    }

    static int safeMid(int lo, int hi) {
        return lo + (hi - lo) / 2;
    }

    static int unsignedMid(int lo, int hi) {
        return (lo + hi) >>> 1;
    }

    static long kokoHours(int[] piles, int speed) {
        long hours = 0;
        for (int pile : piles) {
            hours += ceilDivPositive(pile, speed);
        }
        return hours;
    }

    static long countIntegersInclusive(long left, long right) {
        return right - left + 1;
    }

    static int averageWithoutOverflow(int a, int b) {
        return (int) (((long) a + b) / 2);
    }

    static boolean almostEqual(double a, double b, double epsilon) {
        return Math.abs(a - b) <= epsilon;
    }
}
