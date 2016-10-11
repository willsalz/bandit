package co.willsalz;

import java.util.Random;

public class Main {
    private final static Random rng = new Random(System.nanoTime());
    private final static int iterations = 1000000;

    public static void main(String[] args) {
        final Bandit<Experiment> bandit = new EpsilonGreedyBandit<>(0.1, Experiment.class);

        for (int idx = 0; idx < iterations; idx++) {
            Experiment e = bandit.selectArm();
            switch (e) {
                case RED:
                    bandit.update(e, 3 + 2 * rng.nextGaussian());
                    break;
                case BLACK:
                    bandit.update(e, 2 + 1 * rng.nextGaussian());
                    break;
            }
            System.out.printf("Selected: %s. Expected Reward: %s\n", e, bandit.getReward(e));
        }
    }
}
