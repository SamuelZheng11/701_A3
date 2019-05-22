package kalah.IO;

import com.qualitascorpus.testsupport.IO;
import kalah.IO.BoardLayout.BoardLayout;
import kalah.game_objects.GameState;
import kalah.misc.LogicalConstants;
import kalah.misc.PlayerId;

public class IOHandler {
    private IO io;
    private BoardLayout boardLayout;
    private GameOverMessageManager gameOverMessageManager;

    public IOHandler(IO io, BoardLayout boardLayout) {
        this.io = io;
        this.boardLayout = boardLayout;
    }

    public void drawGameState(GameState gameState) {
        for (String lineOnBoard: boardLayout.getPrintableBoardFor(gameState)) {
            io.println(lineOnBoard);
        }
    }

    public void setGameOverMessageManager(GameOverMessageManager gameOverMessageManager) {
        this.gameOverMessageManager = gameOverMessageManager;
    }

    public void printGameSummary(GameState gameState){
        printEndGameMessageSet(gameState);
        io.println(gameOverMessageManager.getPlayerScoreFor(PlayerId.PLAYER_1));
        io.println(gameOverMessageManager.getPlayerScoreFor(PlayerId.PLAYER_2));
        io.println(gameOverMessageManager.getResultMessage(gameState));
    }

    public void printEndGameMessageSet(GameState gameState) {
        io.println(gameOverMessageManager.getGameOverMessage());
        drawGameState(gameState);
    }

    public void userSelectedAnEmptyHouseMessage() {
        io.println(PrintableConstants.EMPTY_HOUSE_MOVE_AGAIN_MESSAGE);
    }

    public int getUserInput(int playerTurn) {
        return io.readInteger("Player P" + (playerTurn+1) + "'s turn - Specify house number or 'q' to quit: ", LogicalConstants.LOWER_INPUT_RANGE, LogicalConstants.UPPER_INPUT_RANGE, LogicalConstants.CANCEL_RESULT_OUTPUT, LogicalConstants.CANCEL_RESULT_DISPLAY_VALUE);
    }
}
