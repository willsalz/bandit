package co.willsalz;

public interface Bandit<T extends Enum<T>> {

    T selectArm();

    void update(T arm, double reward);

    void reset();

    double getReward(T arm);

}
