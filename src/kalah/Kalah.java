package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.custom_exceptions.EmptyHouseSelectedException;
import kalah.custom_exceptions.PlayerWonException;

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
        OutputPrinter printer = new OutputPrinter(io);
		while(true) {
            printer.drawGameState(gameState);

			int playerTurn = gameState.getPlayerTurn();
            try {
                if(gameState.playerHasWon(playerTurn)) {
                    throw new PlayerWonException();
                }
                int inputArgument = io.readInteger("Player P" + (playerTurn+1) + "'s turn - Specify house number or 'q' to quit: ", 1, 6, -1, "q");
                if(inputArgument == -1) {
                    printer.printGameOver(gameState);
                    break;
                }
				if(gameState.isValidHouse(playerTurn, inputArgument)) {
                    gameState.updateGameState(playerTurn, inputArgument);
				} else {
				    throw new EmptyHouseSelectedException();
				}
			} catch (EmptyHouseSelectedException e) {
                io.println("House is empty. Move again.");
                continue;
            } catch (PlayerWonException e) {
                printer.printGameOver(gameState);
                printer.printScore(gameState);
                printer.printGameResult(gameState);
			    break;
            }
		}
	}
}
