public class DigitsAndBases {
    public static void main(String[] args) {
        System.out.println("Digits and number bases demos");
        System.out.println("digitsOf(5070) = " + digitsOf(5070));
        System.out.println("countDigits(5070) = " + countDigits(5070));
        System.out.println("countDigitsByLog(5070) = " + countDigitsByLog(5070));
        System.out.println("sumDigits(-5070) = " + sumDigits(-5070));
        System.out.println("subtractProductAndSum(234) = "
                + subtractProductAndSum(234));
        System.out.println("digitalRoot(9875) = " + digitalRoot(9875));
        System.out.println("reverseNumber(12340) = " + reverseNumber(12340));
        System.out.println("isPalindrome(1221) = " + isPalindrome(1221));
        System.out.println("isHappy(19) = " + isHappy(19));
        System.out.println("buildChecked([1,2,3,4]) = "
                + buildChecked(new int[] {1, 2, 3, 4}));
        System.out.println("plusOne([9,9,9]) = "
                + digitsToString(plusOne(new int[] {9, 9, 9})));
        System.out.println("addStrings(\"456\", \"77\") = "
                + addStrings("456", "77"));
        System.out.println("addBinary(\"1011\", \"111\") = "
                + addBinary("1011", "111"));
        System.out.println("multiplyStrings(\"123\", \"45\") = "
                + multiplyStrings("123", "45"));
        System.out.println("baseToDecimal(\"1A\", 16) = "
                + baseToDecimal("1A", 16));
        System.out.println("decimalToBase(255, 16) = " + decimalToBase(255, 16));
        System.out.println("Integer.toBinaryString(13) = "
                + Integer.toBinaryString(13));
        System.out.println("Integer.toString(31, 16) = "
                + Integer.toString(31, 16));
        System.out.println("Integer.parseInt(\"1111\", 2) = "
                + Integer.parseInt("1111", 2));
        System.out.println("titleToNumber(\"AB\") = " + titleToNumber("AB"));
        System.out.println("numberToTitle(28) = " + numberToTitle(28));
        System.out.println("toBase7(-100) = " + toBase7(-100));
        System.out.println("toHex(-1) = " + toHex(-1));
        System.out.println("hasEvenDigitCount(1000) = "
                + hasEvenDigitCount(1000));
    }

    static String digitsOf(int n) {
        if (n == 0) {
            return "0";
        }
        long x = Math.abs((long) n);
        StringBuilder builder = new StringBuilder();
        while (x > 0) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(x % 10);
            x /= 10;
        }
        return builder.toString();
    }

    static int countDigits(int n) {
        if (n == 0) {
            return 1;
        }
        long x = Math.abs((long) n);
        int count = 0;
        while (x > 0) {
            count++;
            x /= 10;
        }
        return count;
    }

    static int countDigitsByLog(int n) {
        if (n == 0) {
            return 1;
        }
        long x = Math.abs((long) n);
        return (int) Math.floor(Math.log10(x)) + 1;
    }

    static int sumDigits(int n) {
        long x = Math.abs((long) n);
        int sum = 0;
        while (x > 0) {
            sum += (int) (x % 10);
            x /= 10;
        }
        return sum;
    }

    static int subtractProductAndSum(int n) {
        long x = Math.abs((long) n);
        int product = 1;
        int sum = 0;
        while (x > 0) {
            int digit = (int) (x % 10);
            product *= digit;
            sum += digit;
            x /= 10;
        }
        return product - sum;
    }

    static int digitalRoot(int n) {
        if (n == 0) {
            return 0;
        }
        return 1 + (n - 1) % 9;
    }

    static int reverseNumber(int n) {
        int result = 0;
        while (n != 0) {
            result = result * 10 + n % 10;
            n /= 10;
        }
        return result;
    }

    static boolean isPalindrome(int n) {
        if (n < 0 || (n % 10 == 0 && n != 0)) {
            return false;
        }
        int reversedHalf = 0;
        while (n > reversedHalf) {
            reversedHalf = reversedHalf * 10 + n % 10;
            n /= 10;
        }
        return n == reversedHalf || n == reversedHalf / 10;
    }

    static boolean isHappy(int n) {
        int slow = n;
        int fast = nextHappy(n);
        while (fast != 1 && slow != fast) {
            slow = nextHappy(slow);
            fast = nextHappy(nextHappy(fast));
        }
        return fast == 1;
    }

    static int nextHappy(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }

    static int buildChecked(int[] digits) {
        int result = 0;
        for (int digit : digits) {
            if (result > (Integer.MAX_VALUE - digit) / 10) {
                throw new ArithmeticException("overflow");
            }
            result = result * 10 + digit;
        }
        return result;
    }

    static int[] plusOne(int[] digits) {
        int[] answer = digits.clone();
        for (int i = answer.length - 1; i >= 0; i--) {
            if (answer[i] < 9) {
                answer[i]++;
                return answer;
            }
            answer[i] = 0;
        }
        int[] bigger = new int[answer.length + 1];
        bigger[0] = 1;
        return bigger;
    }

    static String addStrings(String a, String b) {
        StringBuilder result = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            int digitA = i >= 0 ? a.charAt(i--) - '0' : 0;
            int digitB = j >= 0 ? b.charAt(j--) - '0' : 0;
            int sum = digitA + digitB + carry;
            result.append(sum % 10);
            carry = sum / 10;
        }
        return result.reverse().toString();
    }

    static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            int bitA = i >= 0 ? a.charAt(i--) - '0' : 0;
            int bitB = j >= 0 ? b.charAt(j--) - '0' : 0;
            int sum = bitA + bitB + carry;
            result.append(sum % 2);
            carry = sum / 2;
        }
        return result.reverse().toString();
    }

    static String multiplyStrings(String a, String b) {
        int[] slots = new int[a.length() + b.length()];
        for (int i = a.length() - 1; i >= 0; i--) {
            for (int j = b.length() - 1; j >= 0; j--) {
                int product = (a.charAt(i) - '0') * (b.charAt(j) - '0');
                int sum = product + slots[i + j + 1];
                slots[i + j + 1] = sum % 10;
                slots[i + j] += sum / 10;
            }
        }
        StringBuilder result = new StringBuilder();
        for (int digit : slots) {
            if (result.length() > 0 || digit != 0) {
                result.append(digit);
            }
        }
        return result.length() == 0 ? "0" : result.toString();
    }

    static long baseToDecimal(String text, int base) {
        long value = 0;
        for (int i = 0; i < text.length(); i++) {
            int digit = Character.digit(text.charAt(i), base);
            if (digit < 0) {
                throw new IllegalArgumentException("bad digit");
            }
            value = value * base + digit;
        }
        return value;
    }

    static String decimalToBase(long n, int base) {
        if (n == 0) {
            return "0";
        }
        boolean negative = n < 0;
        long x = n;
        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();
        while (x != 0) {
            long remainder = x % base;
            if (remainder < 0) {
                remainder = -remainder;
            }
            result.append(digits.charAt((int) remainder));
            x /= base;
        }
        if (negative) {
            result.append('-');
        }
        return result.reverse().toString();
    }

    static int titleToNumber(String columnTitle) {
        int value = 0;
        for (int i = 0; i < columnTitle.length(); i++) {
            int digit = columnTitle.charAt(i) - 'A' + 1;
            value = value * 26 + digit;
        }
        return value;
    }

    static String numberToTitle(int columnNumber) {
        StringBuilder result = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--;
            result.append((char) ('A' + columnNumber % 26));
            columnNumber /= 26;
        }
        return result.reverse().toString();
    }

    static String toBase7(int n) {
        return decimalToBase(n, 7);
    }

    static String toHex(int n) {
        if (n == 0) {
            return "0";
        }
        char[] digits = "0123456789abcdef".toCharArray();
        StringBuilder result = new StringBuilder();
        int x = n;
        while (x != 0) {
            result.append(digits[x & 15]);
            x >>>= 4;
        }
        return result.reverse().toString();
    }

    static boolean hasEvenDigitCount(int n) {
        return countDigits(n) % 2 == 0;
    }

    static String digitsToString(int[] digits) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < digits.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(digits[i]);
        }
        return builder.append("]").toString();
    }
}
