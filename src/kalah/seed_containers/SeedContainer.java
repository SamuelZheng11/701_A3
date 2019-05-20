package kalah.seed_containers;

public abstract class SeedContainer {
    // seeds are the counters in a container
    protected int seedCount;

    public int getSeedCount() {
        return seedCount;
    }

    public void addSeeds(int seeds) {
        seedCount += seeds;
    }
}
