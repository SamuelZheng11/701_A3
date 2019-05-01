package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.custom_exceptions.EmptyHouseSelectedException;
import kalah.custom_exceptions.NumberOutOfHouseBoundsException;
import kalah.custom_exceptions.PlayerWonException;

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
            GameState.getGameState().drawGameState(io);

			int playerTurn = GameState.getGameState().getPlayerTurn();
            try {
                if(GameState.getGameState().playerHasWon(playerTurn)) {
                    throw new PlayerWonException();
                }
                io.println("Player " + Math.addExact(playerTurn, 1) + "'s turn - Specify house number or 'q' to quit: ");
                String input = keyboardInput.next();
                if(input.toLowerCase().equals("q")) {
                    io.println("Quitting game.");
                    break;
                }
                    int house = Integer.parseInt(input);
				if(house < 1 || house > 6) {
					throw new NumberOutOfHouseBoundsException();
				}
				if(GameState.getGameState().isValidHouse(playerTurn, house)) {
                    GameState.getGameState().updateGameState(playerTurn, house);
				} else {
				    throw new EmptyHouseSelectedException();
				}
			} catch (NumberFormatException e) {
				io.println("Input not recognised, please enter a valid command");
				continue;
			} catch (NumberOutOfHouseBoundsException e) {
                io.println("Please enter a valid house number (between 1 and 6 inclusively)");
                continue;
            } catch (EmptyHouseSelectedException e) {
                io.println("Please select a house that has 1 or more seeds in it");
                continue;
            } catch (PlayerWonException e) {
                if(GameState.getGameState().getPlayerStore(0) - GameState.getGameState().getPlayerStore(1) > 0) {
                    io.println("Player 1 Wins!");
                } else if (GameState.getGameState().getPlayerStore(0) - GameState.getGameState().getPlayerStore(1) < 0) {
                    io.println("Player 2 Wins!");
                } else {
                    io.println("Draw, Everyone loses");
                }
			    break;
            }
		}
	}
}
