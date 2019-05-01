package kalah;

public class Store {
    private static final int STARTING_SEED_VALUE = 0;
    private int seedCount;

    public Store() {
        seedCount = STARTING_SEED_VALUE;
    }

    public void addSeedsToStore(int seeds) {
        seedCount += seeds;
    }

    public int getSeedCount() {
        return seedCount;
    }
}
