package kalah;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GameState {
    private static GameState gameState;
    private static ArrayList<ArrayList<House>> houseBoard;
    // HashMap to map player Id to store count
    private static HashMap<Integer, Store> playerStores;
    private static int playerTurn;

    private GameState() {
        playerTurn = 1;
        houseBoard = new ArrayList<>();
        houseBoard.add(new ArrayList<>(6));
        houseBoard.add(new ArrayList<>(6));
        playerStores = new HashMap<>();
        playerStores.put(1, new Store());
        playerStores.put(2, new Store());
    }

    public static GameState getGameState() {
        if (gameState == null) {
            gameState = new GameState();
        }
        return gameState;
    }

    public int getPlayerStore(int playerId){
        return playerStores.get(playerId).getSeedCount();
    }

    public int getSeedsAtHouse(int playerId, int house){
        return houseBoard.get(playerId).get(house).getSeedCount();
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void updateGameState(int playerId, int house) {
        int numberOfSeedsToMove = houseBoard.get(playerId).get(house).getAndEmptySeeds();
        int playerSide = playerId;
        while (numberOfSeedsToMove != 0 ) {
            for (int i = house + 1; i < houseBoard.get(playerSide).size(); i++) {
                houseBoard.get(playerSide).get(i).addSeed();
                numberOfSeedsToMove--;
            }

            playerStores.get(playerSide).getSeedCount();

            // logic to move the to the other side of the board after setting a seed to the store
            if(playerSide == 1) {
                playerSide = 2;
            } else {
                playerSide = 1;
            }
        }
    }
}
