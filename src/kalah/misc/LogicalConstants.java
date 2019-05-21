package kalah.misc;

import kalah.IO.BoardLayout.BoardLayoutType;

public class LogicalConstants {
    public static final int LOWER_INPUT_RANGE = 1;
    public static final int UPPER_INPUT_RANGE = 6;
    public static final int CANCEL_RESULT_OUTPUT = -1;
    public static final String CANCEL_RESULT_DISPLAY_VALUE = "q";
    public static final int DEFAULT_NUMBER_OF_HOUSES = 6;
    public static final int EMPTY_HOUSE_VALUE = 0;
    public static final int STARTING_HOUSE_SEED_VALUE = 4;
    public static final int STARTING_STORE_SEED_VALUE = 0;
    public static final int DEFAULT_NUMBER_OF_PLAYERS = 2;
    public static final int DEFAULT_STARTING_PLAYER_ID = PlayerId.PLAYER_1.getPlayerValue();
    public static int FORMAT_TRIGGER_VALUE = 10;
    public static BoardLayoutType DEFAULT_BOARD_LAYOUT = BoardLayoutType.Horizontal;
}
