package kalah;

import java.util.concurrent.atomic.AtomicInteger;

public class Store {
    private static final int STARTING_SEED_VALUE = 0;
    private static AtomicInteger idGenerator = new AtomicInteger();
    private int playerId;
    private int seedCount;

    public Store() {
        playerId = idGenerator.incrementAndGet();
        seedCount = STARTING_SEED_VALUE;
    }

    public void addSeedToStore() {
        seedCount++;
    }

    public int getSeedCount() {
        return seedCount;
    }
}
