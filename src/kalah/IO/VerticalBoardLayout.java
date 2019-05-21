package kalah.IO;

import kalah.game_objects.GameState;
import kalah.misc.Constants;
import kalah.misc.PlayerId;

import java.util.ArrayList;
import java.util.List;

public class VerticalBoardLayout implements BoardLayout {
    private SeedDigitFormatter wsf = null;

    VerticalBoardLayout(SeedDigitFormatter wsf) {
        this.wsf = wsf;
    };

    @Override
    public List<String> getPrintableBoardFor(GameState gameState) {
        List<String> boardRepresentation = new ArrayList<>();

        boardRepresentation.add(getVerticalDivider());
        boardRepresentation.add(getVerticalStoreLineFor(PlayerId.PLAYER_2, gameState);

        for (int i = 0; i < Constants.DEFAULT_NUMBER_OF_HOUSES; i++) {
            boardRepresentation.add(printVerticalHouses(gameState, i));
        }

        boardRepresentation.add(getVerticalDivider());
        boardRepresentation.add("| P1 " + wsf.formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(PlayerId.PLAYER_1.getPlayerValue())) + " |       |");
        boardRepresentation.add(getVerticalDivider());

        return boardRepresentation;
    }

    private String getVerticalDivider() {
        return "+-------|-------+";
    }

    private String getVerticalStoreLineFor(PlayerId playerId, GameState gameState) {
        return "|       | P" + (playerId.getPlayerValue() + 1) + " " + wsf.formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(playerId.getPlayerValue())) +" |";
    }

    private String getVerticalHousesLayout(GameState gameState, int leftHouseBoardNumberIndex) {
        return "| " + getPrintableLineForHousePlayer(leftHouseBoardNumberIndex, PlayerId.PLAYER_1, gameState) + " | " + getPrintableLineForHousePlayer(Constants.DEFAULT_NUMBER_OF_HOUSES -1 - leftHouseBoardNumberIndex, PlayerId.PLAYER_2, gameState) + " |";
    }

    private String getPrintableLineForHousePlayer(int houseIndex, PlayerId playerId, GameState gameState) {
        return (houseIndex + 1) + "["+ wsf.formatWhiteSpaceForNumber(gameState.getSeedsAtHouse(playerId.getPlayerValue(), houseIndex)) + "]"
    }
}
