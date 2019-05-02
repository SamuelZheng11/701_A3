package kalah;

import com.qualitascorpus.testsupport.IO;

public class OutputPrinter {

    private IO io;

    public OutputPrinter(IO io) {
        this.io = io;
    }

    public void drawGameState(GameState gameState) {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_2.getNumVal(), 5)) + "] | 5[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_2.getNumVal(), 4)) + "] | 4[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_2.getNumVal(), 3)) + "] | 3[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_2.getNumVal(), 2)) + "] | 2[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_2.getNumVal(), 1)) + "] | 1[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_2.getNumVal(), 0)) + "] | " + formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(Player_Id.PLAYER_1.getNumVal())) + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| " + formatWhiteSpaceForNumber(gameState.getPlayerStoreScore(Player_Id.PLAYER_2.getNumVal())) + " | 1[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_1.getNumVal(), 0)) + "] | 2[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_1.getNumVal(), 1)) + "] | 3[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_1.getNumVal(), 2)) + "] | 4[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_1.getNumVal(), 3)) + "] | 5[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_1.getNumVal(), 4)) + "] | 6[" + formatWhiteSpaceForNumber(gameState.getGameBoard().getSeedsAtHouse(Player_Id.PLAYER_1.getNumVal(), 5)) + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    private String formatWhiteSpaceForNumber(int value) {
        if (value < 10) {
            return " " + value;
        } else {
            return "" + value;
        }
    }

    public void printGameOver(GameState gameState) {
        io.println("Game over");
        this.drawGameState(gameState);
    }

    public void printScore(GameState gameState) {
        io.println("\tplayer 1:" + gameState.getPlayerFinalScore(Player_Id.PLAYER_1.getNumVal()));
        io.println("\tplayer 2:" + gameState.getPlayerFinalScore(Player_Id.PLAYER_2.getNumVal()));
    }

    public void printGameResult(GameState gameState) {
        if(gameState.getPlayerFinalScore(Player_Id.PLAYER_1.getNumVal()) > gameState.getPlayerFinalScore(Player_Id.PLAYER_2.getNumVal())) {
            io.println("Player 1 wins!");
        } else if (gameState.getPlayerFinalScore(Player_Id.PLAYER_1.getNumVal()) < gameState.getPlayerFinalScore(Player_Id.PLAYER_2.getNumVal())) {
            io.println("Player 2 wins!");
        } else {
            io.println("A tie!");
        }
    }
}
