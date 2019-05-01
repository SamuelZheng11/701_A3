package kalah;

public class House {
    private static final int EMPTY_HOUSE_NUMBER = 0;
    private static final int DEFAULT_HOUSE_NUMBER = 4;
    private int seedCount;
    private House upperHouse;
    private House oppositeHouse;

    public House() {
        seedCount = DEFAULT_HOUSE_NUMBER;
    }

    public void setupHouseRelation(House higherHouse, House oppositeHouse) {
        this.upperHouse = higherHouse;
        this.oppositeHouse = oppositeHouse;
        seedCount = DEFAULT_HOUSE_NUMBER;
    }

    public void addSeeds(int seeds) {
        seedCount += seeds;
    }

    public int getAndEmptySeeds() {
        int seedsInHouse = seedCount;
        seedCount = EMPTY_HOUSE_NUMBER;
        return seedsInHouse;
    }

    public House getOppositeHouse() {
        return this.oppositeHouse;
    }

    public House getUpperHouse() {
        return this.upperHouse;
    }

    public int getSeedCount() {
        return seedCount;
    }
}
