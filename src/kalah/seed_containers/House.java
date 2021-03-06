package kalah.seed_containers;

import kalah.misc.LogicalConstants;

public class House extends SeedContainer{
    // hold the next house on the board relative to this house (if this house is house 3 the upper house is house 4)
    private House upperHouse;
    private House oppositeHouse;

    public House() {
        seedCount = LogicalConstants.STARTING_HOUSE_SEED_VALUE;
    }

    public void setupHouseRelation(House higherHouse, House oppositeHouse) {
        this.upperHouse = higherHouse;
        this.oppositeHouse = oppositeHouse;
        seedCount = LogicalConstants.STARTING_HOUSE_SEED_VALUE;
    }

    public int getAndEmptySeeds() {
        int seedsInHouse = seedCount;
        seedCount = LogicalConstants.EMPTY_HOUSE_VALUE;
        return seedsInHouse;
    }

    public House getOppositeHouse() {
        return this.oppositeHouse;
    }

    public House getUpperHouse() {
        return this.upperHouse;
    }
}
