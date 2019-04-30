package kalah;

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
        for (int i = 0; i < 6; i++) {
            houseBoard.get(0).add(new House());
        }

        houseBoard.add(new ArrayList<>(6));
        for (int i = 0; i < 6; i++) {
            houseBoard.get(1).add(new House());
        }
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
        return houseBoard.get(playerId - 1).get(house - 1).getSeedCount();
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void updateGameState(int playerId, int house) {
        int playerSide = playerId - 1;
        int houseIndex = house - 1;
        int numberOfSeedsToMove = houseBoard.get(playerSide).get(houseIndex).getAndEmptySeeds();
        while (numberOfSeedsToMove != 0 ) {
            for (int i = houseIndex + 1; i < houseBoard.get(playerSide).size(); i++) {
                if (numberOfSeedsToMove != 0) {
                    houseBoard.get(playerSide).get(i).addSeed();
                    numberOfSeedsToMove--;
                }
            }

            if(numberOfSeedsToMove != 1) {
                if (playerTurn == 1) {
                    playerTurn = 2;
                } else {
                    playerTurn = 1;
                }
            }

            if (numberOfSeedsToMove >= 1) {
                playerStores.get(playerId).addSeedToStore();
            }
        }
    }
}
