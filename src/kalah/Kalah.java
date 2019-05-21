package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.IO.GameOverManager;
import kalah.game_audit.*;
import kalah.game_objects.GameActionPerformer;
import kalah.game_objects.GameState;
import kalah.IO.IOHandler;
import kalah.IO.BoardLayout.BoardLayoutType;
import kalah.misc.LogicalConstants;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
    private BoardLayoutType boardLayoutType;

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
		// Setup the Kalah game and necessary support classes
        GameState gameState = new GameState(LogicalConstants.DEFAULT_NUMBER_OF_PLAYERS, LogicalConstants.DEFAULT_NUMBER_OF_HOUSES);
        GameActionPerformer gameActionPerformer = new GameActionPerformer();
        IOHandler ioHandler = new IOHandler(io, LogicalConstants.DEFAULT_BOARD_LAYOUT);
        AuditHandler auditHandler = new AuditHandler();
        boardLayoutType = LogicalConstants.DEFAULT_BOARD_LAYOUT;

		while(true) {
            ioHandler.drawGameState(gameState);

            if (auditHandler.gameStateAnomalyPresent(gameState)){
                // this if statement does not have any switch statements because there is only one type of game state anomaly
                // and that is when all houses on one side is 0, In future a switch case could be added if there are more
                // types of game state anomaly
                ioHandler.setGameOverManager(new GameOverManager(gameState));
                ioHandler.printGameSummary(gameState);
                break;
            }

            int playerTurn = gameState.getPlayerTurn();
            int inputArgument = ioHandler.getUserInput(io, playerTurn);

			if (!auditHandler.inputAnomalyPresent(inputArgument, gameState)) {
                gameActionPerformer.distributeSeedsAt(playerTurn, inputArgument, gameState);
            } else {
			    AuditType auditType = auditHandler.getAnomaly(inputArgument, gameState);
			    if (auditType == AuditType.EmptyHouse) {
			        ioHandler.userSelectedAnEmptyHouseMessage();
                } else if (auditType == AuditType.PlayerQuit) {
			        ioHandler.setGameOverManager(new GameOverManager(gameState));
                    ioHandler.printEndGameMessageSet(gameState);
                    break;
                } else if (auditType == AuditType.EndCondition) {
                    ioHandler.setGameOverManager(new GameOverManager(gameState));
                    ioHandler.printGameSummary(gameState);
			        break;
                }
            }
		}
	}
}
