package kalah.IO;

import com.qualitascorpus.testsupport.IO;
import kalah.game_objects.GameState;
import kalah.misc.Constants;
import kalah.misc.PlayerId;

public class IOHandler {

    private IO io;

    public IOHandler(IO io) {
        this.io = io;
    }

    public void drawGameState(GameState gameState) {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_2.getPlayerValue(), 5)) + "] | 5[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_2.getPlayerValue(), 4)) + "] | 4[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_2.getPlayerValue(), 3)) + "] | 3[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_2.getPlayerValue(), 2)) + "] | 2[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_2.getPlayerValue(), 1)) + "] | 1[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_2.getPlayerValue(), 0)) + "] | " + formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(PlayerId.PLAYER_1.getPlayerValue())) + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| " + formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(PlayerId.PLAYER_2.getPlayerValue())) + " | 1[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_1.getPlayerValue(), 0)) + "] | 2[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_1.getPlayerValue(), 1)) + "] | 3[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_1.getPlayerValue(), 2)) + "] | 4[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_1.getPlayerValue(), 3)) + "] | 5[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_1.getPlayerValue(), 4)) + "] | 6[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_1.getPlayerValue(), 5)) + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    private String formatWhiteSpaceForNumber(int value) {
        if (value < 10) {
            return " " + value;
        } else {
            return "" + value;
        }
    }

    public void printGameOver(GameState gameState) {
        io.println("Game over");
        this.drawGameState(gameState);
    }

    public void printScore(GameState gameState) {
        io.println("\tplayer 1:" + gameState.getPlayerFinalScore(PlayerId.PLAYER_1.getPlayerValue()));
        io.println("\tplayer 2:" + gameState.getPlayerFinalScore(PlayerId.PLAYER_2.getPlayerValue()));
    }

    public void printGameResult(GameState gameState) {
        if(gameState.getPlayerFinalScore(PlayerId.PLAYER_1.getPlayerValue()) > gameState.getPlayerFinalScore(PlayerId.PLAYER_2.getPlayerValue())) {
            io.println("Player 1 wins!");
        } else if (gameState.getPlayerFinalScore(PlayerId.PLAYER_1.getPlayerValue()) < gameState.getPlayerFinalScore(PlayerId.PLAYER_2.getPlayerValue())) {
            io.println("Player 2 wins!");
        } else {
            io.println("A tie!");
        }
    }

    public void printEmptyHouseMessage() {
        io.println("House is empty. Move again.");
    }

    public int getInputValue(IO io, int playerTurn) {
        return io.readInteger("Player P" + (playerTurn+1) + "'s turn - Specify house number or 'q' to quit: ", Constants.LOWER_INPUT_RANGE, Constants.UPPER_INPUT_RANGE, Constants.CANCEL_RESULT_OUTPUT, Constants.CANCEL_RESULT_DISPLAY_VALUE);
    }
}
