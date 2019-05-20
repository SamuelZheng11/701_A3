package kalah.IO;

import com.qualitascorpus.testsupport.IO;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import kalah.game_objects.GameState;
import kalah.misc.Constants;
import kalah.misc.PlayerId;

public class IOHandler {
    private IO io;

    public IOHandler(IO io) {
        this.io = io;
    }
    
    public void drawGameState(GameState gameState) {
        // Some of the logic in this class will need to be changed based on how many people play (will need to be refactored further)
        io.println("+-------+-------+");
        io.println("|       | P2 " + formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(PlayerId.PLAYER_2.getPlayerValue())) +" |");

        for (int i = 0; i < Constants.DEFAULT_NUMBER_OF_HOUSES; i++) {
            io.println(printVerticalHouses(gameState, i));
        }

        io.println("+-------|-------+");
        io.println("| P1 " + formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(PlayerId.PLAYER_1.getPlayerValue())) + " |       |");
        io.println("+-------+-------+");
    }

    private String printVerticalHouses(GameState gameState, int leftHouseBoardNumberIndex) {
        return "| "+ (leftHouseBoardNumberIndex + 1) + "["+ formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_1.getPlayerValue(), leftHouseBoardNumberIndex)) + "] | " + (Constants.DEFAULT_NUMBER_OF_HOUSES - leftHouseBoardNumberIndex) + "[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(PlayerId.PLAYER_2.getPlayerValue(), (Constants.DEFAULT_NUMBER_OF_HOUSES - leftHouseBoardNumberIndex - 1))) + "] |";
    }

    // This layout needs to be changed if the number of people playing is odd due to the restrictions in the test
    private String printForward(GameState gameState, int playerNumber) {
        String linePrinted = "| P" + playerNumber +" |";

        for (int i = (gameState.getNumberOfHouses() - 1); i > -1; i--) {
            linePrinted = linePrinted + " " + (i+1) + "[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(playerNumber - 1, i)) + "] |";
        }

        linePrinted = linePrinted + " " + formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(playerNumber - 2)) + " |";
        return linePrinted;
    }

    // This layout needs to be changed if the number of people playing is odd due to the restrictions in the test
    private String printReverse(GameState gameState, int playerNumber) {
        String linePrinted = "| " + formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(playerNumber)) + " |";

        for (int i = 0; i < gameState.getNumberOfHouses(); i++) {
            linePrinted = linePrinted + " " + (i+1) + "[" + formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(playerNumber - 1, i)) + "] |";
        }

        linePrinted = linePrinted + " P" + playerNumber + " |";
        return linePrinted;
    }

    private String formatWhiteSpaceForNumber(int value) {
        // used to format spacing on large and small numbers (" [9]" vs "[10]")
        if (value < Constants.FORMAT_TRIGGER_VALUE) {
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
        for (int i = 0; i < gameState.getNumberOfPlayers() ; i++) {
            io.println("\tplayer " + (i+1) + ":" + gameState.getPlayerFinalScore(i));
        }
    }

    public void printGameResult(GameState gameState) {
        // determine who has the highest score or if its a draw by adding store score and house score
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
