package kalah;

import java.util.concurrent.atomic.AtomicInteger;

public class Store {
    private static final int STARTING_SEED_VALUE = 0;
    private int seedCount;

    public Store() {
        seedCount = STARTING_SEED_VALUE;
    }

    public void addSeedToStore() {
        seedCount++;
    }

    public int getSeedCount() {
        return seedCount;
    }
}
