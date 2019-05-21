package kalah.IO;

import com.qualitascorpus.testsupport.IO;
import kalah.game_objects.GameState;
import kalah.misc.BoardLayoutType;
import kalah.misc.Constants;
import kalah.misc.PlayerId;

public class IOHandler {
    private IO io;
    private BoardLayout boardLayout;

    public IOHandler(IO io, BoardLayoutType boardLayoutType) {
        this.io = io;

        // can be used in future to enforce what type of layout/printing strategy to use (strategy pattern)
        if(boardLayoutType.getBoardLayoutTypeId() == BoardLayoutType.Horizontal.getBoardLayoutTypeId()) {
            boardLayout = new HorizontalBoardLayout(new SeedDigitFormatter());
        }
    }

    public void drawGameState(GameState gameState) {
        for (String lineOnBoard: boardLayout.getPrintableBoardFor(gameState)) {
            io.println(lineOnBoard);
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
