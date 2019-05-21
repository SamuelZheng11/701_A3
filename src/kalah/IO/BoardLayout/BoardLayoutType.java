package kalah.IO.BoardLayout;

public enum BoardLayoutType {
    Horizontal(0);

    private int numVal;

    BoardLayoutType(int numVal) {
        this.numVal = numVal;
    }

    public int getBoardLayoutTypeId() {
        return numVal;
    }
}
