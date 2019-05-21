package kalah.IO;

import com.qualitascorpus.testsupport.IO;
import kalah.IO.BoardLayout.BoardLayout;
import kalah.IO.BoardLayout.HorizontalBoardLayout;
import kalah.game_objects.GameState;
import kalah.IO.BoardLayout.BoardLayoutType;
import kalah.misc.LogicalConstants;
import kalah.misc.PlayerId;

public class IOHandler {
    private IO io;
    private BoardLayout boardLayout;
    private GameOverManager gameOverManager;

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

    public void setGameOverManager(GameOverManager gameOverManager) {
        this.gameOverManager = gameOverManager;
    }

    public void printGameSummary(GameState gameState){
        printEndGameMessageSet(gameState);
        io.println(gameOverManager.getPlayerScoreFor(PlayerId.PLAYER_1));
        io.println(gameOverManager.getPlayerScoreFor(PlayerId.PLAYER_2));
        io.println(gameOverManager.getResultMessage(gameState));
    }

    public void printEndGameMessageSet(GameState gameState) {
        io.println(gameOverManager.getGameOverMessage());
        drawGameState(gameState);
    }

    public void userSelectedAnEmptyHouseMessage() {
        io.println(PrintableConstants.EMPTY_HOUSE_MOVE_AGAIN_MESSAGE);
    }

    public int getUserInput(IO io, int playerTurn) {
        return io.readInteger("Player P" + (playerTurn+1) + "'s turn - Specify house number or 'q' to quit: ", LogicalConstants.LOWER_INPUT_RANGE, LogicalConstants.UPPER_INPUT_RANGE, LogicalConstants.CANCEL_RESULT_OUTPUT, LogicalConstants.CANCEL_RESULT_DISPLAY_VALUE);
    }
}
