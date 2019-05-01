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
		while(true) {
            gameState.drawGameState(io);

			int playerTurn = gameState.getPlayerTurn();
            try {
                if(gameState.playerHasWon(playerTurn)) {
                    throw new PlayerWonException();
                }
                int inputArgument = io.readInteger("Player P" + (playerTurn+1) + "'s turn - Specify house number or 'q' to quit: ", 1, 6, -1, "q");
                if(inputArgument == -1) {
                    this.printGameOver(io, gameState);
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
                this.printGameOver(io, gameState);
                this.printScore(io, gameState);
                this.printGameResult(io, gameState);
			    break;
            }
		}
	}

	private void printGameOver(IO io, GameState gameState) {
        io.println("Game over");
        gameState.drawGameState(io);
    }

    private void printScore(IO io, GameState gameState) {
        io.println("\tplayer 1:" + gameState.getPlayerScore(GameState.PLAYER_1_ID));
        io.println("\tplayer 2:" + gameState.getPlayerScore(GameState.PLAYER_2_ID));
    }

    private void printGameResult(IO io, GameState gameState) {
        if(gameState.getPlayerScore(GameState.PLAYER_1_ID) > gameState.getPlayerScore(GameState.PLAYER_2_ID)) {
            io.println("Player 1 wins!");
        } else if (gameState.getPlayerScore(GameState.PLAYER_1_ID) < gameState.getPlayerScore(GameState.PLAYER_2_ID)) {
            io.println("Player 2 wins!");
        } else {
            io.println("A tie!");
        }
    }
}
