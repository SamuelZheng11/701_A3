package kalah.misc;

public enum PlayerId {
    PLAYER_1(0),
    PLAYER_2(1);

    private int numVal;

    PlayerId(int numVal) {
        this.numVal = numVal;
    }

    public int getPlayerValue() {
        return numVal;
    }
}
