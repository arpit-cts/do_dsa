/**
 * Tower of Hanoi — solved with "Expectation -> Faith -> Meeting the expectation".
 * The full, child-friendly explanation (with tree diagrams) is in README.md in this folder.
 *
 * Run (Java 11+):  java TowerOfHanoi.java      -> 3 disks (default)
 *                  java TowerOfHanoi.java 4    -> 4 disks
 */
public class TowerOfHanoi {

    private static final int MAX_DISKS = 20; // 20 disks already means 1,048,575 moves!

    private static int moveNumber = 0;

    public static void main(String[] args) {
        int n = 3;
        if (args.length > 0) {
            try {
                n = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Please pass a whole number of disks, e.g.  java TowerOfHanoi.java 3");
                return;
            }
        }
        if (n < 0 || n > MAX_DISKS) {
            System.out.println("Please choose between 0 and " + MAX_DISKS + " disks.");
            return;
        }

        System.out.println("Moving " + n + (n == 1 ? " disk" : " disks") + " from A to B, using C as the helper:");
        toh(n, 'A', 'B', 'C');
        System.out.println("Done! Total moves = " + moveNumber + " (formula: 2^" + n + " - 1 = " + ((1 << n) - 1) + ")");
    }

    /**
     * EXPECTATION: print every move needed to shift n disks from {@code source} to
     * {@code destination}, using {@code helper}, never putting a bigger disk on a smaller one.
     */
    static void toh(int n, char source, char destination, char helper) {
        // BASE CASE: 0 disks means there is nothing to move.
        if (n == 0) {
            return;
        }

        // FAITH 1: toh(n-1) moves the n-1 smaller disks off disk n: source -> helper.
        toh(n - 1, source, helper, destination);

        // MY WORK: disk n is now on top and free, so move it straight to the destination.
        moveNumber++;
        System.out.println("Move " + moveNumber + ": disk " + n + " from " + source + " to " + destination);

        // FAITH 2: toh(n-1) moves the n-1 disks from the helper onto disk n: helper -> destination.
        toh(n - 1, helper, destination, source);
    }
}
