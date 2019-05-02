package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.custom_exceptions.EmptyHouseSelectedException;
import kalah.custom_exceptions.EndGameConditionMetException;
import kalah.custom_exceptions.PlayerQuitException;
import kalah.game_objects.GameActionPerformer;
import kalah.game_objects.GameState;
import kalah.misc.IOHandler;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
		// Replace what's below with your implementation
        GameState gameState = new GameState();
        GameActionPerformer gameActionPerformer = GameActionPerformer.getGameActionPerformer();
        IOHandler ioHandler = new IOHandler(io);
		while(true) {
            ioHandler.drawGameState(gameState);

			int playerTurn = gameState.getPlayerTurn();
            try {
                if(gameState.playerHasWon(playerTurn)) {
                    throw new EndGameConditionMetException();
                }
                int inputArgument = ioHandler.getInputValue(io, playerTurn);
                if(inputArgument == ioHandler.getCancelResultConstant()) {
                    throw new PlayerQuitException();
                }
				if(gameState.isValidHouse(playerTurn, inputArgument)) {
                    gameActionPerformer.distributeSeedsAt(playerTurn, inputArgument, gameState);
				} else {
				    throw new EmptyHouseSelectedException();
				}
			} catch (EmptyHouseSelectedException e) {
                ioHandler.printEmptyHouseMessage();
                continue;
            } catch (EndGameConditionMetException e) {
                ioHandler.printGameOver(gameState);
                ioHandler.printScore(gameState);
                ioHandler.printGameResult(gameState);
			    break;
            } catch (PlayerQuitException e) {
                ioHandler.printGameOver(gameState);
                break;
            }
		}
	}
}
