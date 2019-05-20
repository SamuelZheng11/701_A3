package kalah.misc;

public enum PlayerId {
    // used to assign a number to a given player enum (java doesn't auto assign one)
    // add more players as needed
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
