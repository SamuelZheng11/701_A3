package kalah;

public class House {
    private static final int EMPTY_HOUSE_NUMBER = 0;
    private static final int DEFAULT_HOUSE_NUMBER = 4;
    private int seedCount;

    public House() {
        seedCount = DEFAULT_HOUSE_NUMBER;
    }

    public void addSeed() {
        seedCount++;
    }

    public int getAndEmptySeeds() {
        int seedsInHouse = seedCount;
        seedCount = EMPTY_HOUSE_NUMBER;
        return seedsInHouse;
    }

    public int getSeedCount() {
        return seedCount;
    }
}
