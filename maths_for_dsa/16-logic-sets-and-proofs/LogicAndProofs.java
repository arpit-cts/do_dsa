import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicAndProofs {
    public static void main(String[] args) {
        System.out.println("Logic, sets and proofs demos");
        printTruthTable();
        System.out.println("Guard i < n && a[i] > 0 with i=3,n=3: "
                + safePositive(new int[] {4, 5, 6}, 3));
        System.out.println("De Morgan sample !(true && false): "
                + (!(true && false)) + " equals " + (!true || !false));

        int[] a = {1, 2, 2, 3};
        int[] b = {2, 2, 4};
        System.out.println("Unique intersection: " + intersection(a, b));
        System.out.println("Intersection with counts: " + intersectWithCounts(a, b));
        System.out.println("Contains duplicate [1,2,3,1]: "
                + containsDuplicate(new int[] {1, 2, 3, 1}));
        System.out.println("Power set size for n=5: " + powerSetSize(5));

        int n = 5;
        System.out.println("1+...+" + n + " = " + sumToN(n)
                + ", formula = " + formulaSum(n));
        System.out.println("Hanoi moves for 4 disks: " + hanoiMoves(4));
        System.out.println("2^10 > 10: " + (powerOfTwo(10) > 10));
        System.out.println("n^2+n+41 at n=40: " + eulerPolynomial(40));
        System.out.println("Is that value prime? " + isPrime(eulerPolynomial(40)));

        int[] sorted = {2, 4, 7, 9, 12};
        System.out.println("Binary search 9 index: " + binarySearch(sorted, 9));
        System.out.println("Majority element: "
                + majorityElement(new int[] {2, 2, 1, 1, 1, 2, 2}));

        ListNode cycleHead = cycleList();
        System.out.println("Linked list has cycle: " + hasCycle(cycleHead));
        System.out.println("Cycle starts at value: " + detectCycle(cycleHead).value);
        System.out.println("Duplicate by Floyd in [1,3,4,2,2]: "
                + findDuplicate(new int[] {1, 3, 4, 2, 2}));
        System.out.println("Happy number 19: " + isHappy(19));

        System.out.println("Can jump [2,3,1,1,4]: "
                + canJump(new int[] {2, 3, 1, 1, 4}));
        System.out.println("Assign cookies: "
                + findContentChildren(new int[] {1, 2, 3}, new int[] {1, 1}));
        System.out.println("Gas station start: "
                + canCompleteCircuit(new int[] {1, 2, 3, 4, 5},
                new int[] {3, 4, 5, 1, 2}));
    }

    private static void printTruthTable() {
        System.out.println("p q | AND OR XOR NOT-p");
        boolean[] values = {false, true};
        for (boolean p : values) {
            for (boolean q : values) {
                System.out.println(p + " " + q + " | "
                        + (p && q) + " " + (p || q) + " " + (p ^ q)
                        + " " + (!p));
            }
        }
    }

    private static boolean safePositive(int[] nums, int i) {
        return i < nums.length && nums[i] > 0;
    }

    private static Set<Integer> intersection(int[] first, int[] second) {
        Set<Integer> seen = new HashSet<Integer>();
        for (int value : first) {
            seen.add(value);
        }
        Set<Integer> result = new HashSet<Integer>();
        for (int value : second) {
            if (seen.contains(value)) {
                result.add(value);
            }
        }
        return result;
    }

    private static List<Integer> intersectWithCounts(int[] first, int[] second) {
        Arrays.sort(first);
        Arrays.sort(second);
        List<Integer> result = new ArrayList<Integer>();
        int i = 0;
        int j = 0;
        while (i < first.length && j < second.length) {
            if (first[i] == second[j]) {
                result.add(first[i]);
                i++;
                j++;
            } else if (first[i] < second[j]) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }

    private static boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<Integer>();
        for (int num : nums) {
            if (!seen.add(num)) {
                return true;
            }
        }
        return false;
    }

    private static long powerSetSize(int n) {
        return 1L << n;
    }

    private static int sumToN(int n) {
        int sum = 0;
        for (int value = 1; value <= n; value++) {
            sum += value;
        }
        return sum;
    }

    private static int formulaSum(int n) {
        return n * (n + 1) / 2;
    }

    private static long hanoiMoves(int disks) {
        return (1L << disks) - 1;
    }

    private static long powerOfTwo(int n) {
        return 1L << n;
    }

    private static int eulerPolynomial(int n) {
        return n * n + n + 41;
    }

    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int d = 2; d * d <= n; d++) {
            if (n % d == 0) {
                return false;
            }
        }
        return true;
    }

    private static int binarySearch(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }

    private static int majorityElement(int[] nums) {
        int candidate = 0;
        int votes = 0;
        for (int num : nums) {
            if (votes == 0) {
                candidate = num;
            }
            votes += num == candidate ? 1 : -1;
        }
        return candidate;
    }

    private static boolean hasCycle(ListNode head) {
        return detectCycle(head) != null;
    }

    private static ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                ListNode finder = head;
                while (finder != slow) {
                    finder = finder.next;
                    slow = slow.next;
                }
                return finder;
            }
        }
        return null;
    }

    private static int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        int finder = nums[0];
        while (finder != slow) {
            finder = nums[finder];
            slow = nums[slow];
        }
        return finder;
    }

    private static boolean isHappy(int n) {
        int slow = n;
        int fast = nextHappy(n);
        while (fast != 1 && slow != fast) {
            slow = nextHappy(slow);
            fast = nextHappy(nextHappy(fast));
        }
        return fast == 1;
    }

    private static int nextHappy(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }

    private static boolean canJump(int[] nums) {
        int farthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) {
                return false;
            }
            farthest = Math.max(farthest, i + nums[i]);
        }
        return true;
    }

    private static int findContentChildren(int[] greed, int[] cookies) {
        Arrays.sort(greed);
        Arrays.sort(cookies);
        int child = 0;
        int cookie = 0;
        while (child < greed.length && cookie < cookies.length) {
            if (cookies[cookie] >= greed[child]) {
                child++;
            }
            cookie++;
        }
        return child;
    }

    private static int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0;
        int tank = 0;
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            int gain = gas[i] - cost[i];
            total += gain;
            tank += gain;
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        return total >= 0 ? start : -1;
    }

    private static ListNode cycleList() {
        ListNode a = new ListNode(3);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(0);
        ListNode d = new ListNode(-4);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = b;
        return a;
    }

    private static class ListNode {
        int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
        }
    }
}
