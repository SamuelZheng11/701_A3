package kalah;

public class House {
    private static final int EMPTY_HOUSE_NUMBER = 0;
    private static final int DEFAULT_HOUSE_NUMBER = 4;
    private int seedCount;
    private House lowerHouse;
    private House upperHouse;
    private House oppositeHouse;

    public House() {
        seedCount = DEFAULT_HOUSE_NUMBER;
    }

    public void setupHouseRelation(House lowerHouse, House higherHouse, House oppositeHouse) {
        this.lowerHouse = lowerHouse;
        this.upperHouse = higherHouse;
        this.oppositeHouse = oppositeHouse;
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

    public House getOppositeHouse() {
        return this.oppositeHouse;
    }

    public int getSeedCount() {
        return seedCount;
    }
}
