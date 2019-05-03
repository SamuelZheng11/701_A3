package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.game_audit.*;
import kalah.game_objects.GameActionPerformer;
import kalah.game_objects.GameState;
import kalah.IO.IOHandler;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
		// Setup the Kalah game and necessary support classes
        GameState gameState = new GameState();
        GameActionPerformer gameActionPerformer = GameActionPerformer.getGameActionPerformer();
        IOHandler ioHandler = new IOHandler(io);
        AuditHandler auditHandler = new AuditHandler();

		while(true) {
            ioHandler.drawGameState(gameState);

            if (auditHandler.gameStateAnomalyPresent(gameState)){
                // this if statement does not have any switch statements because there is only one type of game state anomaly
                // and that is when all houses on one side is 0, In future a switch case could be added if there are more
                // types of game state anomaly
                ioHandler.printGameOver(gameState);
                ioHandler.printScore(gameState);
                ioHandler.printGameResult(gameState);
                break;
            }

			int playerTurn = gameState.getPlayerTurn();
            int inputArgument = ioHandler.getInputValue(io, playerTurn);

			if (!auditHandler.inputAnomalyPresent(inputArgument, gameState)) {
                gameActionPerformer.distributeSeedsAt(playerTurn, inputArgument, gameState);
            } else {
			    AuditType auditType = auditHandler.getAnomaly(inputArgument, gameState);
			    if (auditType == AuditType.EmptyHouse) {
			        ioHandler.printEmptyHouseMessage();
                } else if (auditType == AuditType.PlayerQuit) {
                    ioHandler.printGameOver(gameState);
                    break;
                }
            }
		}
	}
}
