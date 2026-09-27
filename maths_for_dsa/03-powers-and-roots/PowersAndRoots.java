public class PowersAndRoots {
    public static void main(String[] args) {
        System.out.println("Powers and roots demos");
        System.out.println("2^10 = " + exactPower(2, 10));
        System.out.println("10^6 = " + exactPower(10, 6));
        System.out.println("2^30 = " + exactPower(2, 30));
        System.out.println("2^10 is close to 10^3: " + exactPower(2, 10)
                + " vs " + exactPower(10, 3));

        System.out.println();
        System.out.println("Exponent rules");
        System.out.println("2^3 * 2^4 = " + (exactPower(2, 3) * exactPower(2, 4)));
        System.out.println("(2^3)^4 = " + exactPower(exactPower(2, 3), 4));
        System.out.println("5^0 = " + exactPower(5, 0));
        System.out.println("2^-3 = " + fastPower(2.0, -3));

        System.out.println();
        System.out.println("Integer square root");
        System.out.println("floor(sqrt(50)) = " + integerSqrt(50));
        System.out.println("floor(sqrt(2147395600)) = " + integerSqrt(2147395600));
        System.out.println("49 is a perfect square: " + isPerfectSquare(49));
        System.out.println("50 is a perfect square: " + isPerfectSquare(50));
        System.out.println("sqrt by Newton, n = 50: " + newtonSqrt(50));

        System.out.println();
        System.out.println("Power checks");
        System.out.println("64 is power of two: " + isPowerOfTwo(64));
        System.out.println("45 is power of three: " + isPowerOfThree(45));
        System.out.println("81 is power of three: " + isPowerOfThree(81));
        System.out.println("81 by largest int power trick: "
                + isPowerOfThreeByLargestPower(81));
        System.out.println("256 is power of four: " + isPowerOfFour(256));

        System.out.println();
        System.out.println("Fast power");
        System.out.println("fastPower(2, 13) = " + fastPower(2.0, 13));
        System.out.println("fastPower(2, -3) = " + fastPower(2.0, -3));
        System.out.println("fastPower(2, Integer.MIN_VALUE) = "
                + fastPower(2.0, Integer.MIN_VALUE));

        System.out.println();
        System.out.println("Math.pow precision");
        long exact = exactPower(3, 20);
        long fromMathPow = (long) Math.pow(3, 20);
        System.out.println("3^20 exact loop = " + exact);
        System.out.println("(long)Math.pow(3, 20) = " + fromMathPow);
        System.out.println("They match: " + (exact == fromMathPow));
        long exactBig = exactPower(3, 34);
        long fromMathPowBig = (long) Math.pow(3, 34);
        System.out.println("3^34 exact loop = " + exactBig);
        System.out.println("(long)Math.pow(3, 34) = " + fromMathPowBig);
        System.out.println("They match: " + (exactBig == fromMathPowBig));

        System.out.println();
        System.out.println("Sum of Square Numbers");
        System.out.println("c = 65: " + judgeSquareSum(65));
        System.out.println("c = 3: " + judgeSquareSum(3));
    }

    public static long exactPower(long base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Use fastPower for negative exponents.");
        }
        long answer = 1;
        for (int i = 0; i < exponent; i++) {
            answer *= base;
        }
        return answer;
    }

    public static int integerSqrt(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(
                    "Square root needs a non-negative number.");
        }
        int low = 0;
        int high = n;
        int answer = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid == 0 || mid <= n / mid) {
                answer = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return answer;
    }

    public static boolean isPerfectSquare(int n) {
        if (n < 0) {
            return false;
        }
        int root = integerSqrt(n);
        return root * root == n;
    }

    public static long safeSqrtLong(long n) {
        if (n < 0) {
            throw new IllegalArgumentException(
                    "Square root needs a non-negative number.");
        }
        if (n == 0) {
            return 0;
        }
        long root = (long) Math.sqrt(n);
        while ((root + 1) <= n / (root + 1)) {
            root++;
        }
        while (root > n / root) {
            root--;
        }
        return root;
    }

    public static double newtonSqrt(double n) {
        if (n < 0) {
            throw new IllegalArgumentException(
                    "Square root needs a non-negative number.");
        }
        if (n == 0) {
            return 0;
        }
        double guess = n;
        for (int i = 0; i < 20; i++) {
            guess = (guess + n / guess) / 2.0;
        }
        return guess;
    }

    public static boolean isPowerOfTwo(int n) {
        if (n < 1) {
            return false;
        }
        while (n % 2 == 0) {
            n /= 2;
        }
        return n == 1;
    }

    public static boolean isPowerOfThree(int n) {
        if (n < 1) {
            return false;
        }
        while (n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }

    public static boolean isPowerOfThreeByLargestPower(int n) {
        int largestPowerOfThreeInInt = 1162261467;
        return n > 0 && largestPowerOfThreeInInt % n == 0;
    }

    public static boolean isPowerOfFour(int n) {
        if (n < 1) {
            return false;
        }
        while (n % 4 == 0) {
            n /= 4;
        }
        return n == 1;
    }

    public static double fastPower(double x, int n) {
        long exponent = n;
        if (exponent < 0) {
            x = 1.0 / x;
            exponent = -exponent;
        }
        double answer = 1.0;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                answer *= x;
            }
            x *= x;
            exponent /= 2;
        }
        return answer;
    }

    public static boolean judgeSquareSum(int c) {
        long left = 0;
        long right = integerSqrt(c);
        while (left <= right) {
            long sum = left * left + right * right;
            if (sum == c) {
                return true;
            }
            if (sum < c) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }
}
