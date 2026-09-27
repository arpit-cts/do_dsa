import java.util.Arrays;

public class Combinatorics {
    public static void main(String[] args) {
        System.out.println("Counting and combinatorics demos");
        System.out.println("--------------------------------");

        System.out.println("Rule of product: 3 shirts x 2 pants = "
                + productRule(3, 2) + " outfits");
        System.out.println("Rule of sum: 4 teas or 3 juices = "
                + sumRule(4, 3) + " drink choices");

        System.out.println();
        System.out.println("Factorials");
        System.out.println("0! = " + factorialLong(0));
        System.out.println("5! = " + factorialLong(5));
        System.out.println("10! = " + factorialLong(10));
        System.out.println("13! exact = " + factorialLong(13));
        System.out.println("13! stored in int overflows to " + factorialIntOverflow(13));
        System.out.println("20! stored in long = " + factorialLong(20));
        System.out.println("21! stored in long overflows to "
                + factorialLongOverflow(21));

        System.out.println();
        System.out.println("Permutations");
        System.out.println("P(5, 3) podiums = " + permutations(5, 3));
        System.out.println("4-digit PINs with repetition = " + power(10, 4));
        System.out.println("MISSISSIPPI distinct words = " + mississippiWords());

        System.out.println();
        System.out.println("Combinations");
        System.out.println("C(10, 3) teams = " + choose(10, 3));
        System.out.println("C(10, 7) equals C(10, 3) = " + choose(10, 7));
        System.out.println("C(30, 15) safely in long = " + choose(30, 15));
        System.out.println("Pascal row 5 = " + Arrays.toString(pascalRow(5)));

        System.out.println();
        System.out.println("Subsets and grids");
        System.out.println("Subsets of 5 items = " + power(2, 5));
        System.out.println("Subsets of size 2 from 5 items = " + choose(5, 2));
        System.out.println("Unique paths 3x7 by formula = " + uniquePathsFormula(3, 7));
        System.out.println("Unique paths 3x7 by DP = " + uniquePathsDp(3, 7));
        int[][] obstacleGrid = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        System.out.println("Unique paths with one obstacle = "
                + uniquePathsWithObstacles(obstacleGrid));

        System.out.println();
        System.out.println("Stars, pigeonholes, inclusion-exclusion, Catalan");
        System.out.println("7 identical candies to 3 kids = " + starsAndBars(7, 3));
        System.out.println("Sorted vowel strings of length 2 = " + countVowelStrings(2));
        System.out.println("Smallest socks needed for a guaranteed pair among 4 colors = "
                + pigeonholeGuarantee(4));
        System.out.println("Numbers <= 100 divisible by 2, 3, or 5 = "
                + divisibleBy2Or3Or5(100));
        System.out.println("Catalan numbers C0..C6 = " + Arrays.toString(catalanUpTo(6)));
        System.out.println("Pickup and delivery orders for n = 3 = "
                + countPickupDeliveryOrders(3));
    }

    static int sumRule(int firstChoices, int secondChoices) {
        return firstChoices + secondChoices;
    }

    static int productRule(int firstChoices, int secondChoices) {
        return firstChoices * secondChoices;
    }

    static long factorialLong(int n) {
        long answer = 1;
        for (int number = 2; number <= n; number++) {
            answer *= number;
        }
        return answer;
    }

    static int factorialIntOverflow(int n) {
        int answer = 1;
        for (int number = 2; number <= n; number++) {
            answer *= number;
        }
        return answer;
    }

    static long factorialLongOverflow(int n) {
        long answer = 1L;
        for (int number = 2; number <= n; number++) {
            answer *= number;
        }
        return answer;
    }

    static long permutations(int n, int k) {
        long answer = 1;
        for (int choice = 0; choice < k; choice++) {
            answer *= n - choice;
        }
        return answer;
    }

    static long power(long base, int exponent) {
        long answer = 1;
        for (int count = 0; count < exponent; count++) {
            answer *= base;
        }
        return answer;
    }

    static long mississippiWords() {
        return factorialLong(11)
                / (factorialLong(4) * factorialLong(4) * factorialLong(2));
    }

    static long choose(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            return 0;
        }
        int smallerSide = Math.min(k, n - k);
        long answer = 1;
        for (int i = 1; i <= smallerSide; i++) {
            long numerator = n - smallerSide + i;
            long denominator = i;
            long shared = gcd(numerator, denominator);
            numerator /= shared;
            denominator /= shared;
            shared = gcd(answer, denominator);
            answer /= shared;
            denominator /= shared;
            if (denominator != 1) {
                throw new ArithmeticException("combination step was not exact");
            }
            answer = Math.multiplyExact(answer, numerator);
        }
        return answer;
    }

    static long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return Math.abs(a);
    }

    static long[] pascalRow(int row) {
        long[] values = new long[row + 1];
        values[0] = 1;
        for (int r = 1; r <= row; r++) {
            for (int c = r; c >= 1; c--) {
                values[c] += values[c - 1];
            }
        }
        return values;
    }

    static long uniquePathsFormula(int rows, int columns) {
        return choose(rows + columns - 2, rows - 1);
    }

    static int uniquePathsDp(int rows, int columns) {
        int[][] ways = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            ways[row][0] = 1;
        }
        for (int col = 0; col < columns; col++) {
            ways[0][col] = 1;
        }
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < columns; col++) {
                ways[row][col] = ways[row - 1][col] + ways[row][col - 1];
            }
        }
        return ways[rows - 1][columns - 1];
    }

    static int uniquePathsWithObstacles(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[][] ways = new int[rows][columns];
        ways[0][0] = grid[0][0] == 1 ? 0 : 1;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (grid[row][col] == 1) {
                    ways[row][col] = 0;
                } else {
                    if (row > 0) {
                        ways[row][col] += ways[row - 1][col];
                    }
                    if (col > 0) {
                        ways[row][col] += ways[row][col - 1];
                    }
                }
            }
        }
        return ways[rows - 1][columns - 1];
    }

    static long starsAndBars(int candies, int kids) {
        return choose(candies + kids - 1, kids - 1);
    }

    static long countVowelStrings(int length) {
        return choose(length + 4, 4);
    }

    static int pigeonholeGuarantee(int colors) {
        return colors + 1;
    }

    static int divisibleBy2Or3Or5(int limit) {
        int by2 = limit / 2;
        int by3 = limit / 3;
        int by5 = limit / 5;
        int by6 = limit / 6;
        int by10 = limit / 10;
        int by15 = limit / 15;
        int by30 = limit / 30;
        return by2 + by3 + by5 - by6 - by10 - by15 + by30;
    }

    static long[] catalanUpTo(int maxN) {
        long[] catalan = new long[maxN + 1];
        catalan[0] = 1;
        for (int n = 0; n < maxN; n++) {
            long next = 0;
            for (int left = 0; left <= n; left++) {
                next += catalan[left] * catalan[n - left];
            }
            catalan[n + 1] = next;
        }
        return catalan;
    }

    static long countPickupDeliveryOrders(int n) {
        long answer = 1;
        for (int order = 1; order <= n; order++) {
            answer *= order;
            answer *= 2L * order - 1;
        }
        return answer;
    }
}
