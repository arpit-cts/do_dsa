import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Chapter 07 · Recurrences — count the calls and the work of recursive functions.
// Run from this folder:  java Recurrences.java
public class Recurrences {

    static long calls;      // how many times a recursive function was entered
    static long work;       // extra work done outside the recursive calls
    static int depth;       // current depth of the call stack
    static int maxDepth;    // deepest the stack ever got

    static void enter() {
        calls++;
        depth++;
        maxDepth = Math.max(maxDepth, depth);
    }

    static void leave() {
        depth--;
    }

    static void reset() {
        calls = 0;
        work = 0;
        depth = 0;
        maxDepth = 0;
    }

    // T(n) = T(n - 1) + O(1)
    static long factorial(int n) {
        enter();
        long result = n <= 1 ? 1 : n * factorial(n - 1);
        leave();
        return result;
    }

    // T(n) = T(n / 2) + O(1): look at the middle, keep one half
    static int binarySearch(int[] a, int target, int lo, int hi) {
        enter();
        int result;
        if (lo > hi) {
            result = -1;
        } else {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == target) {
                result = mid;
            } else if (a[mid] < target) {
                result = binarySearch(a, target, mid + 1, hi);
            } else {
                result = binarySearch(a, target, lo, mid - 1);
            }
        }
        leave();
        return result;
    }

    // T(n) = 2T(n / 2) + O(1): the biggest value of a[lo..hi], found by halves
    static int maxByHalves(int[] a, int lo, int hi) {
        enter();
        int result;
        if (lo == hi) {
            result = a[lo];
        } else {
            int mid = lo + (hi - lo) / 2;
            result = Math.max(maxByHalves(a, lo, mid), maxByHalves(a, mid + 1, hi));
        }
        leave();
        return result;
    }

    // T(n) = 2T(n / 2) + O(n): merge sort; "work" counts the elements merged
    static void mergeSort(int[] a, int lo, int hi, int[] temp) {
        enter();
        if (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            mergeSort(a, lo, mid, temp);
            mergeSort(a, mid + 1, hi, temp);
            int i = lo;
            int j = mid + 1;
            int k = lo;
            while (i <= mid || j <= hi) {
                if (j > hi || (i <= mid && a[i] <= a[j])) {
                    temp[k++] = a[i++];
                } else {
                    temp[k++] = a[j++];
                }
                work++;
            }
            System.arraycopy(temp, lo, a, lo, hi - lo + 1);
        }
        leave();
    }

    // T(n) = T(n - 1) + T(n - 2) + O(1): plain Fibonacci
    static long fib(int n) {
        enter();
        long result = n < 2 ? n : fib(n - 1) + fib(n - 2);
        leave();
        return result;
    }

    // The same, but every answer is remembered: each fib(k) is computed only once
    static long fibMemo(int n, long[] memo) {
        enter();
        long result;
        if (n < 2) {
            result = n;
        } else if (memo[n] != 0) {
            result = memo[n];
        } else {
            result = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
            memo[n] = result;
        }
        leave();
        return result;
    }

    // T(n) = 2T(n - 1) + O(1): Tower of Hanoi; "work" counts the moves
    static void hanoi(int n) {
        enter();
        if (n > 0) {
            hanoi(n - 1);
            work++;                 // move the biggest disk
            hanoi(n - 1);
        }
        leave();
    }

    // Every item is either skipped or taken: 2 branches, n levels deep
    static long leaves;

    static void subsets(int index, int n) {
        enter();
        if (index == n) {
            leaves++;               // one complete subset
        } else {
            subsets(index + 1, n);  // skip item "index"
            subsets(index + 1, n);  // take item "index"
        }
        leave();
    }

    // n choices for the first place, n - 1 for the second, ...: n! arrangements
    static void permutations(List<Integer> chosen, boolean[] used) {
        enter();
        if (chosen.size() == used.length) {
            leaves++;
        } else {
            for (int i = 0; i < used.length; i++) {
                if (!used[i]) {
                    used[i] = true;
                    chosen.add(i);
                    permutations(chosen, used);
                    chosen.remove(chosen.size() - 1);
                    used[i] = false;
                }
            }
        }
        leave();
    }

    static String fmt(String pattern, Object... values) {
        return String.format(Locale.ROOT, pattern, values);
    }

    static void say(String line) {
        System.out.println(line);
    }

    static int[] sortedArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = 2 * i;
        }
        return a;
    }

    static int[] shuffledArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = (int) ((i * 7_919L) % n);     // a fixed, repeatable mix
        }
        return a;
    }

    static final String ROW = "   %-34s %9s %13s %13s %10s";

    static void report(String name, int n, String extraWork) {
        String depthText = fmt("%,d", maxDepth);
        say(fmt(ROW, name, fmt("%,d", n), fmt("%,d", calls), extraWork, depthText));
    }

    public static void main(String[] args) {
        say("1) Calls, extra work and stack depth of real recursive functions");
        say(fmt(ROW, "function", "n", "calls", "work", "max depth"));

        for (int n : new int[] {10, 1_000}) {
            reset();
            factorial(n);
            report("factorial      T(n)=T(n-1)+1", n, "-");
        }
        for (int n : new int[] {1_000, 1_000_000}) {
            reset();
            binarySearch(sortedArray(n), -1, 0, n - 1);
            report("binary search  T(n)=T(n/2)+1", n, "-");
        }
        for (int n : new int[] {8, 1_000_000}) {
            reset();
            maxByHalves(shuffledArray(n), 0, n - 1);
            report("max by halves  T(n)=2T(n/2)+1", n, "-");
        }
        for (int n : new int[] {8, 1_024, 1_048_576}) {
            reset();
            mergeSort(shuffledArray(n), 0, n - 1, new int[n]);
            report("merge sort     T(n)=2T(n/2)+n", n, fmt("%,d", work));
        }
        for (int n : new int[] {3, 10, 20}) {
            reset();
            hanoi(n);
            report("Tower of Hanoi T(n)=2T(n-1)+1", n, fmt("%,d", work) + " mv");
        }
        int[] fibSizes = {10, 20, 30};
        for (int n : fibSizes) {
            reset();
            fib(n);
            report("fib, plain     T(n)=T(n-1)+T(n-2)", n, "-");
        }
        for (int n : fibSizes) {
            reset();
            fibMemo(n, new long[n + 1]);
            report("fib, memo      every fib(k) once", n, "-");
        }

        say("");
        say("2) Many branches: branches ^ depth");
        String b = "   %-34s %4s %13s %13s";
        say(fmt(b, "function", "n", "leaves", "calls"));
        for (int n : new int[] {3, 10, 20}) {
            reset();
            leaves = 0;
            subsets(0, n);
            say(fmt(b, "subsets: 2 branches, depth n", n, fmt("%,d", leaves),
                    fmt("%,d", calls)));
        }
        for (int n : new int[] {3, 5, 8}) {
            reset();
            leaves = 0;
            permutations(new ArrayList<>(), new boolean[n]);
            say(fmt(b, "permutations: n, n-1, ... choices", n, fmt("%,d", leaves),
                    fmt("%,d", calls)));
        }

        say("");
        say("3) Merge sort of 16 numbers, level by level (the recursion tree)");
        say("   level   calls   size of each   work per level");
        int n = 16;
        for (int level = 0; (n >> level) >= 1; level++) {
            int count = 1 << level;
            int size = n >> level;
            String levelWork = size > 1 ? fmt("%d x %d = %d", count, size, count * size)
                    : "(base cases)";
            say(fmt("   %5d   %5d   %12d   %14s", level, count, size, levelWork));
        }
        say("   4 merging levels x 16 = 64 elements merged = n log2(n)");
    }
}