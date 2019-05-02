package kalah;

public enum Player_Id {
    PLAYER_1(0),
    PLAYER_2(1);

    private int numVal;

    Player_Id(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
