import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SumsAndSeries {
    public static void main(String[] args) {
        System.out.println("Sums and series demos");
        System.out.println("1 + 2 + ... + 100 = " + triangular(100));
        System.out.println("5 + 8 + 11 + 14 = " + arithmeticSeries(5, 14, 3));
        System.out.println("first 5 odd numbers sum = " + sumFirstOddNumbers(5));
        System.out.println("first 5 even numbers sum = " + sumFirstEvenNumbers(5));
        System.out.println("1^2 + ... + 5^2 = " + sumOfSquares(5));
        System.out.println("1^3 + ... + 5^3 = " + sumOfCubes(5));

        System.out.println();
        System.out.println("Geometric, halving and harmonic");
        System.out.println("1 + 2 + 4 + ... + 2^5 = " + powersOfTwoSeries(5));
        System.out.println("3 + 6 + 12 + 24 = " + geometricSeries(3, 2, 3));
        System.out.println("64 + 32 + ... + 1 = " + halvingUntilOne(64));
        System.out.printf("H_10 rounded to 4 decimals = %.4f%n", harmonic(10));
        System.out.printf("telescoping n = 5 gives %.4f%n", telescoping(5));

        System.out.println();
        System.out.println("Prefix sums");
        int[] nums = {2, -1, 3, 4, -2};
        long[] prefix = prefixSums(nums);
        System.out.println("array = " + Arrays.toString(nums));
        System.out.println("prefix = " + Arrays.toString(prefix));
        System.out.println("range sum index 1..3 = " + rangeSum(prefix, 1, 3));
        System.out.println("running sum = " + Arrays.toString(runningSum(nums)));
        System.out.println("subarrays with sum 5 = " + subarraySumEqualsK(nums, 5));
        System.out.println("product except self = "
                + Arrays.toString(productExceptSelf(new int[] {1, 2, 3, 4})));

        System.out.println();
        System.out.println("Difference arrays and counting");
        int[][] bookings = {{1, 2, 10}, {2, 3, 20}, {2, 5, 25}};
        System.out.println("flight bookings = "
                + Arrays.toString(corpFlightBookings(bookings, 5)));
        System.out.println("missing number in [3, 0, 1] = "
                + missingNumber(new int[] {3, 0, 1}));
        System.out.println("handshakes among 6 people = " + handshakes(6));
        System.out.println("subarrays in length 5 array = " + subarrayCount(5));
        System.out.println("arranging 8 coins makes rows = " + arrangeCoins(8));
    }

    public static long triangular(long n) {
        return n * (n + 1) / 2;
    }

    public static long arithmeticSeries(long first, long last, long step) {
        if (step <= 0 || last < first || (last - first) % step != 0) {
            throw new IllegalArgumentException("The terms must land exactly on last.");
        }
        long count = (last - first) / step + 1;
        return (first + last) * count / 2;
    }

    public static long sumFirstOddNumbers(long n) {
        return n * n;
    }

    public static long sumFirstEvenNumbers(long n) {
        return n * (n + 1);
    }

    public static long sumOfSquares(long n) {
        return n * (n + 1) * (2 * n + 1) / 6;
    }

    public static long sumOfCubes(long n) {
        long triangle = triangular(n);
        return triangle * triangle;
    }

    public static long powersOfTwoSeries(int k) {
        return (1L << (k + 1)) - 1;
    }

    public static long geometricSeries(long first, long ratio, int k) {
        if (ratio == 1) {
            return first * (k + 1);
        }
        long power = 1;
        for (int i = 0; i < k + 1; i++) {
            power *= ratio;
        }
        return first * (power - 1) / (ratio - 1);
    }

    public static long halvingUntilOne(long n) {
        long sum = 0;
        while (n >= 1) {
            sum += n;
            n /= 2;
        }
        return sum;
    }

    public static double harmonic(int n) {
        double sum = 0.0;
        for (int i = 1; i <= n; i++) {
            sum += 1.0 / i;
        }
        return sum;
    }

    public static double telescoping(int n) {
        return 1.0 - 1.0 / (n + 1);
    }

    public static long[] prefixSums(int[] nums) {
        long[] prefix = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        return prefix;
    }

    public static long rangeSum(long[] prefix, int left, int right) {
        return prefix[right + 1] - prefix[left];
    }

    public static int[] runningSum(int[] nums) {
        int[] answer = new int[nums.length];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            answer[i] = sum;
        }
        return answer;
    }

    public static int subarraySumEqualsK(int[] nums, int k) {
        Map<Integer, Integer> seen = new HashMap<Integer, Integer>();
        seen.put(0, 1);
        int prefix = 0;
        int count = 0;
        for (int num : nums) {
            prefix += num;
            count += seen.getOrDefault(prefix - k, 0);
            seen.put(prefix, seen.getOrDefault(prefix, 0) + 1);
        }
        return count;
    }

    public static int[] productExceptSelf(int[] nums) {
        int[] answer = new int[nums.length];
        int leftProduct = 1;
        for (int i = 0; i < nums.length; i++) {
            answer[i] = leftProduct;
            leftProduct *= nums[i];
        }
        int rightProduct = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            answer[i] *= rightProduct;
            rightProduct *= nums[i];
        }
        return answer;
    }

    public static int[] corpFlightBookings(int[][] bookings, int n) {
        int[] diff = new int[n + 1];
        for (int[] booking : bookings) {
            int first = booking[0] - 1;
            int last = booking[1] - 1;
            int seats = booking[2];
            diff[first] += seats;
            if (last + 1 < n) {
                diff[last + 1] -= seats;
            }
        }
        int[] answer = new int[n];
        int current = 0;
        for (int i = 0; i < n; i++) {
            current += diff[i];
            answer[i] = current;
        }
        return answer;
    }

    public static int missingNumber(int[] nums) {
        long expected = triangular(nums.length);
        long actual = 0;
        for (int num : nums) {
            actual += num;
        }
        return (int) (expected - actual);
    }

    public static long handshakes(long n) {
        return n * (n - 1) / 2;
    }

    public static long subarrayCount(long n) {
        return n * (n + 1) / 2;
    }

    public static int arrangeCoins(int n) {
        int low = 0;
        int high = n;
        int answer = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if ((long) mid * (mid + 1) / 2 <= n) {
                answer = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return answer;
    }
}
