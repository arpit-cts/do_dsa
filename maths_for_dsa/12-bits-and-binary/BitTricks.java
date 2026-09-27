import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitTricks {
    static String bits8(int x) {
        String s = Integer.toBinaryString(x & 255);
        return "00000000".substring(s.length()) + s;
    }

    static String bits32(int x) {
        String s = Integer.toBinaryString(x);
        return "00000000000000000000000000000000".substring(s.length()) + s;
    }

    static int checkBit(int x, int k) {
        return (x >> k) & 1;
    }

    static int setBit(int x, int k) {
        return x | (1 << k);
    }

    static int clearBit(int x, int k) {
        return x & ~(1 << k);
    }

    static int toggleBit(int x, int k) {
        return x ^ (1 << k);
    }

    static int countOnesBrianKernighan(int x) {
        int count = 0;
        while (x != 0) {
            x = x & (x - 1);
            count++;
        }
        return count;
    }

    static boolean isPowerOfTwo(int x) {
        return x > 0 && (x & (x - 1)) == 0;
    }

    static boolean isPowerOfFour(int x) {
        return x > 0 && (x & (x - 1)) == 0 && (x & 0x55555555) != 0;
    }

    static int lowestOneBit(int x) {
        return x & -x;
    }

    static int singleNumber(int[] nums) {
        int answer = 0;
        for (int x : nums) {
            answer ^= x;
        }
        return answer;
    }

    static int missingNumber(int[] nums) {
        int answer = nums.length;
        for (int i = 0; i < nums.length; i++) {
            answer ^= i;
            answer ^= nums[i];
        }
        return answer;
    }

    static int hammingDistance(int a, int b) {
        return Integer.bitCount(a ^ b);
    }

    static int[] countingBits(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i >> 1] + (i & 1);
        }
        return bits;
    }

    static int reverseBits(int x) {
        int answer = 0;
        for (int i = 0; i < 32; i++) {
            answer <<= 1;
            answer |= x & 1;
            x >>>= 1;
        }
        return answer;
    }

    static List<List<String>> subsets(String[] items) {
        List<List<String>> answer = new ArrayList<List<String>>();
        int total = 1 << items.length;
        for (int mask = 0; mask < total; mask++) {
            List<String> one = new ArrayList<String>();
            for (int i = 0; i < items.length; i++) {
                if (((mask >> i) & 1) == 1) {
                    one.add(items[i]);
                }
            }
            answer.add(one);
        }
        return answer;
    }

    static int sumWithoutPlus(int a, int b) {
        while (b != 0) {
            int sumWithoutCarry = a ^ b;
            int carry = (a & b) << 1;
            a = sumWithoutCarry;
            b = carry;
        }
        return a;
    }

    static int singleNumberTwo(int[] nums) {
        int answer = 0;
        for (int bit = 0; bit < 32; bit++) {
            int count = 0;
            for (int x : nums) {
                count += (x >> bit) & 1;
            }
            if (count % 3 != 0) {
                answer |= 1 << bit;
            }
        }
        return answer;
    }

    static int[] singleNumberThree(int[] nums) {
        int xorAll = 0;
        for (int x : nums) {
            xorAll ^= x;
        }
        int splitBit = xorAll & -xorAll;
        int a = 0;
        int b = 0;
        for (int x : nums) {
            if ((x & splitBit) == 0) {
                a ^= x;
            } else {
                b ^= x;
            }
        }
        return new int[] {a, b};
    }

    static int rangeBitwiseAnd(int left, int right) {
        int shifts = 0;
        while (left < right) {
            left >>= 1;
            right >>= 1;
            shifts++;
        }
        return left << shifts;
    }

    static int maxProductWordLengths(String[] words) {
        int[] masks = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                masks[i] |= 1 << (words[i].charAt(j) - 'a');
            }
        }

        int best = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if ((masks[i] & masks[j]) == 0) {
                    best = Math.max(best, words[i].length() * words[j].length());
                }
            }
        }
        return best;
    }

    static int xorOperation(int n, int start) {
        int answer = 0;
        for (int i = 0; i < n; i++) {
            answer ^= start + 2 * i;
        }
        return answer;
    }

    static void printSubmasks(int mask) {
        List<String> seen = new ArrayList<String>();
        for (int s = mask; s > 0; s = (s - 1) & mask) {
            seen.add(bits8(s));
        }
        System.out.println("Submasks of " + bits8(mask) + ":");
        System.out.println(seen);
    }

    public static void main(String[] args) {
        int a = 44;
        int b = 21;
        System.out.println("8-bit a = " + a + " -> " + bits8(a));
        System.out.println("8-bit b = " + b + " -> " + bits8(b));
        System.out.println("a & b = " + (a & b) + " -> " + bits8(a & b));
        System.out.println("a | b = " + (a | b) + " -> " + bits8(a | b));
        System.out.println("a ^ b = " + (a ^ b) + " -> " + bits8(a ^ b));
        System.out.println("~a low 8 bits -> " + bits8(~a));
        System.out.println("5 << 2 = " + (5 << 2));
        System.out.println("-20 >> 2 = " + (-20 >> 2));
        System.out.println("-20 >>> 2 = " + (-20 >>> 2));
        System.out.println("-1 as 32 bits = " + bits32(-1));
        System.out.println("~12 = " + (~12) + ", and -12 - 1 = " + (-12 - 1));
        System.out.println("Check bit 3 of 44 = " + checkBit(44, 3));
        System.out.println("Set bit 1 of 44 = " + setBit(44, 1));
        System.out.println("Clear bit 3 of 44 = " + clearBit(44, 3));
        System.out.println("Toggle bit 2 of 44 = " + toggleBit(44, 2));
        System.out.println("bitCount(44) = " + Integer.bitCount(44));
        System.out.println("Brian count(44) = " + countOnesBrianKernighan(44));
        System.out.println("44 & (44 - 1) = " + (44 & 43) + " -> " + bits8(44 & 43));
        System.out.println("44 & -44 = " + lowestOneBit(44) + " -> "
                + bits8(lowestOneBit(44)));
        System.out.println("32 is power of two? " + isPowerOfTwo(32));
        System.out.println("48 is power of two? " + isPowerOfTwo(48));
        System.out.println("64 is power of four? " + isPowerOfFour(64));
        System.out.println("Single number = " + singleNumber(new int[] {4, 1, 2, 1, 2}));
        System.out.println("Missing number = " + missingNumber(new int[] {3, 0, 1}));
        System.out.println("Counting bits to 8 = " + Arrays.toString(countingBits(8)));
        System.out.println("Hamming distance 25, 30 = " + hammingDistance(25, 30));
        System.out.println("Reverse bits of 00000101 ends with = "
                + bits8(reverseBits(5)));
        System.out.println("Subsets of [A, B, C]:");
        System.out.println(subsets(new String[] {"A", "B", "C"}));
        printSubmasks(22);
        System.out.println("7 + 13 without plus = " + sumWithoutPlus(7, 13));
        System.out.println("Single number II = "
                + singleNumberTwo(new int[] {2, 2, 3, 2}));
        System.out.println("Single number III = "
                + Arrays.toString(singleNumberThree(new int[] {1, 2, 1, 3, 2, 5})));
        System.out.println("Range AND 5..7 = " + rangeBitwiseAnd(5, 7));
        System.out.println("Max product word lengths = "
                + maxProductWordLengths(
                        new String[] {"abcw", "baz", "foo", "bar", "xtfn"}));
        System.out.println("XOR operation n=5, start=0 = " + xorOperation(5, 0));
        System.out.println("highestOneBit(44) = " + Integer.highestOneBit(44));
        System.out.println("numberOfTrailingZeros(44) = "
                + Integer.numberOfTrailingZeros(44));
        System.out.println("numberOfLeadingZeros(44) = "
                + Integer.numberOfLeadingZeros(44));
        long bigBit = 1L << 40;
        System.out.println("1L << 40 = " + bigBit);
    }
}
