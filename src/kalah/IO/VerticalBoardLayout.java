package kalah.IO;

import kalah.game_objects.GameState;
import kalah.misc.Constants;
import kalah.misc.PlayerId;

import java.util.ArrayList;
import java.util.List;

public class VerticalBoardLayout implements BoardLayout {

    @Override
    public List<String> getPrintableBoardFor(GameState gameState) {
        List<String> boardRepresentation = new ArrayList<>();

        boardRepresentation.add("+-------+-------+");
        boardRepresentation.add("|       | P2 " + formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(PlayerId.PLAYER_2.getPlayerValue())) +" |");

        for (int i = 0; i < Constants.DEFAULT_NUMBER_OF_HOUSES; i++) {
            boardRepresentation.add(printVerticalHouses(gameState, i));
        }

        boardRepresentation.add("+-------|-------+");
        boardRepresentation.add("| P1 " + formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(PlayerId.PLAYER_1.getPlayerValue())) + " |       |");
        boardRepresentation.add("+-------+-------+");

        return boardRepresentation;
    }
}
