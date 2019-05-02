package kalah.seed_containers;

public class Store implements SeedContainer{
    private static final int STARTING_SEED_VALUE = 0;
    private int seedCount;

    public Store() {
        seedCount = STARTING_SEED_VALUE;
    }

    public void addSeeds(int seeds) {
        seedCount += seeds;
    }

    public int getSeedCount() {
        return seedCount;
    }
}
