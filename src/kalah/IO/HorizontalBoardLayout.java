package kalah.IO;

import kalah.game_objects.GameState;

import java.util.ArrayList;
import java.util.List;

public class HorizontalBoardLayout implements BoardLayout{
    private SeedDigitFormatter sdf;

    public HorizontalBoardLayout(SeedDigitFormatter sdf) {
        this.sdf = sdf;
    }

    @Override
    public List<String> getPrintableBoardFor(GameState gameState) {
        List<String> boardRepresentation = new ArrayList<>();

        boardRepresentation.add(getExternalDividerString());
        for (int i = gameState.getNumberOfPlayers(); i > 0 ; i--) {
            if(i%2 == 0) {
                boardRepresentation.add(printForward(gameState, i));
            } else {
                boardRepresentation.add(printReverse(gameState, i));
            }
            if (i != 1) {
                boardRepresentation.add(getInternalDividerString());
            }
        }
        boardRepresentation.add(getExternalDividerString());

        return boardRepresentation;
    }

    private String getInternalDividerString() {
        return "|    |-------+-------+-------+-------+-------+-------|    |";
    }

    private String getExternalDividerString() {
        return "+----+-------+-------+-------+-------+-------+-------+----+";
    }

    // This layout needs to be changed if the number of people playing is odd due to the restrictions in the test
    private String printForward(GameState gameState, int playerNumber) {
        String linePrinted = "| P" + playerNumber +" |";

        for (int i = (gameState.getNumberOfHouses() - 1); i > -1; i--) {
            linePrinted = linePrinted + " " + (i+1) + "[" + sdf.formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(playerNumber - 1, i)) + "] |";
        }

        linePrinted = linePrinted + " " + sdf.formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(playerNumber - 2)) + " |";
        return linePrinted;
    }

    // This layout needs to be changed if the number of people playing is odd due to the restrictions in the test
    private String printReverse(GameState gameState, int playerNumber) {
        String linePrinted = "| " + sdf.formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(playerNumber)) + " |";

        for (int i = 0; i < gameState.getNumberOfHouses(); i++) {
            linePrinted = linePrinted + " " + (i+1) + "[" + sdf.formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(playerNumber - 1, i)) + "] |";
        }

        linePrinted = linePrinted + " P" + playerNumber + " |";
        return linePrinted;
    }
}
