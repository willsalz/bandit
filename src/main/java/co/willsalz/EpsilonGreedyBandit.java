package co.willsalz;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class EpsilonGreedyBandit<T extends Enum<T>> implements Bandit<T> {

    private final double epsilon;
    private final Random rng;
    private final Class<T> armType;
    private final Map<T, Double> arms;
    private final Map<T, Integer> counts;

    public EpsilonGreedyBandit(final double epsilon, final Class<T> armType) {
        this.epsilon = epsilon;
        this.rng = new Random(System.nanoTime());
        this.armType = armType;
        this.arms = new HashMap<>();
        this.counts = new HashMap<>();

        this.reset();
    }

    @Override
    public T selectArm() {
        // Exploit
        if (rng.nextDouble() > epsilon) {
            return arms.entrySet().stream()
                .sorted(Map.Entry.<T, Double>comparingByValue().reversed())
                .limit(1)
                .findFirst()
                .get()
                .getKey();
        }

        // Explore
        return arms.keySet()
            .stream()
            .collect(Collectors.toList())
            .get(rng.nextInt(arms.size()));
    }

    @Override
    public double getReward(T arm) {
        return arms.get(arm);
    }

    @Override
    public void update(T arm, double reward) {
        final double oldReward = arms.get(arm);
        final int count = counts.get(arm) + 1;
        final double newReward = ((count - 1) / (double) count) * oldReward + (1 / (double) count) * reward;
        counts.put(arm, count);
        arms.put(arm, newReward);
    }

    @Override
    public void reset() {
        // Initialize Arms
        for (T arm : armType.getEnumConstants()) {
            arms.put(arm, 0.0);
            counts.put(arm, 0);
        }
        System.err.println(arms);
        System.err.println(counts);
    }
}
