import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Randomness {
    public static void main(String[] args) {
        System.out.println("Probability and randomness demos");
        System.out.println("--------------------------------");

        System.out.println("Two dice outcomes = " + twoDiceOutcomes());
        System.out.println("Ways to roll sum 7 = " + waysToRollSum(7));
        System.out.printf("P(sum 7) = %.6f%n", waysToRollSum(7) / 36.0);
        System.out.printf("P(at least one six in 4 rolls) = %.6f%n",
                atLeastOneSixInFourRolls());
        System.out.printf("P(two red without replacement from 3 red, 2 blue) = %.6f%n",
                probabilityTwoRedWithoutReplacement(3, 2));

        System.out.println();
        System.out.println("Expected value");
        System.out.printf("E[die] = %.1f%n", expectedDie());
        System.out.printf("Expected fixed points in 5-person hat check = %.1f%n",
                expectedFixedPoints(5));
        System.out.printf("Expected flips until first head = %.1f%n",
                expectedFlipsUntilFirstHead());
        System.out.printf("Coupon collector for 5 coupons approx %.6f%n",
                couponCollector(5));

        System.out.println();
        System.out.println("Java Random with seed 42");
        Random seeded = new Random(42);
        System.out.println("nextInt(10) samples = " + randomSamples(seeded, 5, 10));
        Random rangeRandom = new Random(42);
        System.out.println("range [5, 9] samples = "
                + rangeSamples(rangeRandom, 5, 5, 9));

        System.out.println();
        System.out.println("Shuffle and bias");
        int[] shuffled = {1, 2, 3, 4};
        fisherYatesShuffle(shuffled, new Random(42));
        System.out.println("Fisher-Yates shuffle of [1, 2, 3, 4] = "
                + Arrays.toString(shuffled));
        System.out.println("Naive shuffle frequencies for n = 3:");
        System.out.println(formatMap(naiveShuffleFrequenciesForThree()));

        System.out.println();
        System.out.println("Sampling");
        System.out.println("Reservoir pick from [10, 20, 30, 40, 50] = "
                + reservoirPick(new int[]{10, 20, 30, 40, 50}, new Random(42)));
        WeightedPicker picker = new WeightedPicker(new int[]{2, 5, 3});
        Random weightRandom = new Random(42);
        System.out.println("Weighted picks for weights [2, 5, 3] = "
                + weightedSamples(picker, weightRandom, 8));
        Rand7Source rand7Source = new Rand7Source(new Random(42));
        System.out.println("Rand10 samples from Rand7 = "
                + rand10Samples(rand7Source, 8));

        System.out.println();
        System.out.println("Birthday, random set, Monte Carlo");
        System.out.printf("P(shared birthday among 23 people) = %.6f%n",
                birthdayCollisionProbability(23));
        RandomizedSet set = new RandomizedSet(new Random(42));
        set.insert(10);
        set.insert(20);
        set.insert(30);
        set.remove(20);
        System.out.println("RandomizedSet getRandom after [10, 30] = " + set.getRandom());
        System.out.printf("Monte Carlo pi estimate with 100000 points = %.6f%n",
                estimatePi(100000, new Random(42)));
    }

    static int twoDiceOutcomes() {
        return 6 * 6;
    }

    static int waysToRollSum(int target) {
        int ways = 0;
        for (int first = 1; first <= 6; first++) {
            for (int second = 1; second <= 6; second++) {
                if (first + second == target) {
                    ways++;
                }
            }
        }
        return ways;
    }

    static double atLeastOneSixInFourRolls() {
        return 1.0 - Math.pow(5.0 / 6.0, 4);
    }

    static double probabilityTwoRedWithoutReplacement(int red, int blue) {
        if (red < 0 || blue < 0) {
            throw new IllegalArgumentException("marble counts cannot be negative");
        }
        if (red < 2) {
            return 0.0;
        }
        int total = red + blue;
        if (total < 2) {
            return 0.0;
        }
        return (red / (double) total) * ((red - 1) / (double) (total - 1));
    }

    static double expectedDie() {
        double total = 0.0;
        for (int face = 1; face <= 6; face++) {
            total += face * (1.0 / 6.0);
        }
        return total;
    }

    static double expectedFixedPoints(int people) {
        double expected = 0.0;
        for (int person = 0; person < people; person++) {
            expected += 1.0 / people;
        }
        return expected;
    }

    static double expectedFlipsUntilFirstHead() {
        return 2.0;
    }

    static double couponCollector(int coupons) {
        double harmonic = 0.0;
        for (int i = 1; i <= coupons; i++) {
            harmonic += 1.0 / i;
        }
        return coupons * harmonic;
    }

    static String randomSamples(Random random, int count, int bound) {
        int[] values = new int[count];
        for (int i = 0; i < count; i++) {
            values[i] = random.nextInt(bound);
        }
        return Arrays.toString(values);
    }

    static String rangeSamples(Random random, int count, int low, int high) {
        int[] values = new int[count];
        for (int i = 0; i < count; i++) {
            values[i] = low + random.nextInt(high - low + 1);
        }
        return Arrays.toString(values);
    }

    static void fisherYatesShuffle(int[] values, Random random) {
        for (int last = values.length - 1; last > 0; last--) {
            int chosen = random.nextInt(last + 1);
            int temp = values[last];
            values[last] = values[chosen];
            values[chosen] = temp;
        }
    }

    static Map<String, Integer> naiveShuffleFrequenciesForThree() {
        Map<String, Integer> counts = new LinkedHashMap<String, Integer>();
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                for (int c = 0; c < 3; c++) {
                    int[] values = {1, 2, 3};
                    int[] choices = {a, b, c};
                    for (int i = 0; i < 3; i++) {
                        int temp = values[i];
                        values[i] = values[choices[i]];
                        values[choices[i]] = temp;
                    }
                    String key = Arrays.toString(values);
                    counts.put(key, counts.containsKey(key) ? counts.get(key) + 1 : 1);
                }
            }
        }
        return counts;
    }

    static String formatMap(Map<String, Integer> counts) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            builder.append(entry.getKey())
                    .append(" -> ")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    static int reservoirPick(int[] values, Random random) {
        if (values.length == 0) {
            throw new IllegalArgumentException("stream must contain at least one item");
        }
        int chosen = values[0];
        for (int i = 1; i <= values.length; i++) {
            if (random.nextInt(i) == 0) {
                chosen = values[i - 1];
            }
        }
        return chosen;
    }

    static String weightedSamples(WeightedPicker picker, Random random, int count) {
        int[] values = new int[count];
        for (int i = 0; i < count; i++) {
            values[i] = picker.pickIndex(random);
        }
        return Arrays.toString(values);
    }

    static String rand10Samples(Rand7Source source, int count) {
        int[] values = new int[count];
        for (int i = 0; i < count; i++) {
            values[i] = rand10(source);
        }
        return Arrays.toString(values);
    }

    static int rand10(Rand7Source source) {
        while (true) {
            int row = source.rand7();
            int column = source.rand7();
            int number = (row - 1) * 7 + column;
            if (number <= 40) {
                return 1 + (number - 1) % 10;
            }
        }
    }

    static double birthdayCollisionProbability(int people) {
        double allDifferent = 1.0;
        for (int person = 0; person < people; person++) {
            allDifferent *= (365.0 - person) / 365.0;
        }
        return 1.0 - allDifferent;
    }

    static double estimatePi(int points, Random random) {
        int inside = 0;
        for (int i = 0; i < points; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (x * x + y * y <= 1.0) {
                inside++;
            }
        }
        return 4.0 * inside / points;
    }

    static class WeightedPicker {
        private final int[] prefix;
        private final int total;

        WeightedPicker(int[] weights) {
            if (weights.length == 0) {
                throw new IllegalArgumentException("weights must not be empty");
            }
            prefix = new int[weights.length];
            int running = 0;
            for (int i = 0; i < weights.length; i++) {
                if (weights[i] <= 0) {
                    throw new IllegalArgumentException("weights must be positive");
                }
                running = Math.addExact(running, weights[i]);
                prefix[i] = running;
            }
            total = running;
        }

        int pickIndex(Random random) {
            int target = 1 + random.nextInt(total);
            int low = 0;
            int high = prefix.length - 1;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (prefix[mid] >= target) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            return low;
        }
    }

    static class Rand7Source {
        private final Random random;

        Rand7Source(Random random) {
            this.random = random;
        }

        int rand7() {
            return 1 + random.nextInt(7);
        }
    }

    static class RandomizedSet {
        private final List<Integer> values = new ArrayList<Integer>();
        private final Map<Integer, Integer> indexByValue =
                new HashMap<Integer, Integer>();
        private final Random random;

        RandomizedSet(Random random) {
            this.random = random;
        }

        boolean insert(int value) {
            if (indexByValue.containsKey(value)) {
                return false;
            }
            indexByValue.put(value, values.size());
            values.add(value);
            return true;
        }

        boolean remove(int value) {
            if (!indexByValue.containsKey(value)) {
                return false;
            }
            int removeIndex = indexByValue.get(value);
            int lastIndex = values.size() - 1;
            int lastValue = values.get(lastIndex);
            values.set(removeIndex, lastValue);
            indexByValue.put(lastValue, removeIndex);
            indexByValue.remove(value);
            values.remove(lastIndex);
            return true;
        }

        int getRandom() {
            if (values.isEmpty()) {
                throw new IllegalStateException("set is empty");
            }
            return values.get(random.nextInt(values.size()));
        }
    }
}
