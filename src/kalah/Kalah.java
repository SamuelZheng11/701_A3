package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

import java.util.Scanner;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	Scanner keyboardInput = new Scanner(System.in);

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
		// Replace what's below with your implementation
		while(true) {
			drawGameState(io, GameState.getGameState());

			int playerTurn = GameState.getGameState().getPlayerTurn();
			io.println("Player " + playerTurn + "'s turn - Specify house number or 'q' to quit: ");
			String input = keyboardInput.next();
			if(input.toLowerCase().equals("q")) {
				break;
			}

			try {
				int house = Integer.parseInt(input);
				GameState.getGameState().updateGameState(playerTurn, house);
			} catch (NumberFormatException e) {
				io.println("Input not recognised as a command, please enter a valid command");
				continue;
			}
		}
	}

	private void getInput(IO io) {

	}

	private void drawGameState(IO io, GameState gameState) {
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		io.println("| P2 | 6[ " + gameState.getSeedsAtHouse(2, 6) + "] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  " + gameState.getPlayerStore(1) + " |");
		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		io.println("|  " + gameState.getPlayerStore(2) + " | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
	}
}
