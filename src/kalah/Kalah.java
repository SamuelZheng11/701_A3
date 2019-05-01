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
			io.println("Player " + playerTurn + "'s turn - Specify house number or 'q' to quit: ");
			String input = keyboardInput.next();
			if(input.toLowerCase().equals("q")) {
				io.println("Quitting game.");
				break;
			}

			try {
				int house = Integer.parseInt(input);
				if(house < 1 || house > 6) {
					throw new NumberOutOfHouseBoundsException();
				}
				if(GameState.getGameState().isValidHouse(playerTurn, house)) {
				    if(!GameState.getGameState().playerHasWon(playerTurn)) {
                        GameState.getGameState().updateGameState(playerTurn, house);
                    } else {
				        throw new PlayerWonException();
                    }
				} else {
				    throw new EmptyHouseSelectedException();
				}
			} catch (NumberFormatException e) {
				io.println("Input not recognised, please enter a valid command");
				continue;
			} catch (NumberOutOfHouseBoundsException e) {
                io.println("Please enter a valid house number (between 1 and 6 inclusivly)");
                continue;
            } catch (EmptyHouseSelectedException e) {
                io.println("Please select a house that has 1 or more seeds in it");
                continue;
            } catch (PlayerWonException e) {
			    io.println("Player " + GameState.getGameState().getPlayerTurn() +" Wins!");
			    break;
            }
		}
	}
}
