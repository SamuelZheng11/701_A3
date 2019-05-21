package kalah.misc;

public enum BoardLayoutType {
    Vertical(0),
    Horizontal(1);

    private int numVal;

    BoardLayoutType(int numVal) {
        this.numVal = numVal;
    }

    public int getBoardLayout() {
        return numVal;
    }
}
