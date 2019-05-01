package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {
    private static GameState gameState;
    private ArrayList<ArrayList<House>> houseBoard;
    // HashMap to map player Id to store count
    private HashMap<Integer, Store> playerStores;
    private int playerTurn;

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

        houseBoard.get(0).get(0).setupHouseRelation(null, houseBoard.get(0).get(1), houseBoard.get(1).get(5));
        houseBoard.get(0).get(1).setupHouseRelation(houseBoard.get(0).get(0), houseBoard.get(0).get(2), houseBoard.get(1).get(4));
        houseBoard.get(0).get(2).setupHouseRelation(houseBoard.get(0).get(1), houseBoard.get(0).get(3), houseBoard.get(1).get(3));
        houseBoard.get(0).get(3).setupHouseRelation(houseBoard.get(0).get(2), houseBoard.get(0).get(4), houseBoard.get(1).get(2));
        houseBoard.get(0).get(4).setupHouseRelation(houseBoard.get(0).get(3), houseBoard.get(0).get(5), houseBoard.get(1).get(1));
        houseBoard.get(0).get(5).setupHouseRelation(houseBoard.get(0).get(4), null, houseBoard.get(1).get(0));

        houseBoard.get(1).get(0).setupHouseRelation(null, houseBoard.get(1).get(1), houseBoard.get(0).get(5));
        houseBoard.get(1).get(1).setupHouseRelation(houseBoard.get(1).get(0), houseBoard.get(1).get(2), houseBoard.get(0).get(4));
        houseBoard.get(1).get(2).setupHouseRelation(houseBoard.get(1).get(1), houseBoard.get(1).get(3), houseBoard.get(0).get(3));
        houseBoard.get(1).get(3).setupHouseRelation(houseBoard.get(1).get(2), houseBoard.get(1).get(4), houseBoard.get(0).get(2));
        houseBoard.get(1).get(4).setupHouseRelation(houseBoard.get(1).get(3), houseBoard.get(1).get(5), houseBoard.get(0).get(1));
        houseBoard.get(1).get(5).setupHouseRelation(houseBoard.get(1).get(4), null, houseBoard.get(0).get(0));

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
        boolean hasCapture = false;
        int numberOfSeedsToMove = houseBoard.get(playerSide).get(house - 1).getAndEmptySeeds();
        while (numberOfSeedsToMove != 0 ) {
            for (int i = house; i < houseBoard.get(playerSide).size(); i++) {
                if (playerSide == playerId - 1 && numberOfSeedsToMove == 1 && houseBoard.get(playerSide).get(i).getSeedCount() == 0) {
                    int seedsToSow = 1;
                    if (playerSide == 0 && houseBoard.get(playerSide).get(i).getOppositeHouse().getSeedCount() != 0) {
                        hasCapture = true;
                        seedsToSow += houseBoard.get(playerSide).get(i).getOppositeHouse().getAndEmptySeeds();
                        for (int j = 0; j < seedsToSow; j++) {
                            playerStores.get(playerId).addSeedToStore();
                        }
                    } else if (playerSide == 1 && houseBoard.get(playerSide).get(i).getOppositeHouse().getSeedCount() != 0) {
                        hasCapture = true;
                        seedsToSow += houseBoard.get(1).get(houseBoard.get(playerSide).size() - i - 1).getAndEmptySeeds();
                        for (int j = 0; j < seedsToSow; j++) {
                            playerStores.get(playerId).addSeedToStore();
                        }
                    }
                    numberOfSeedsToMove--;
                } else if (numberOfSeedsToMove != 0) {
                    houseBoard.get(playerSide).get(i).addSeed();
                    numberOfSeedsToMove--;
                }

                if(numberOfSeedsToMove == 0) {
                    break;
                }
            }

            if(numberOfSeedsToMove <= 1) {
                if (numberOfSeedsToMove == 1 || hasCapture) {
                    if (playerId == 1) {
                        playerTurn = 1;
                    } else {
                        playerTurn = 2;
                    }
                    hasCapture = false;
                } else {
                    if (playerId == 1) {
                        playerTurn = 2;
                    } else {
                        playerTurn = 1;
                    }
                }
            }

            if (numberOfSeedsToMove >= 1) {
                playerStores.get(playerId).addSeedToStore();
                numberOfSeedsToMove--;

                if(numberOfSeedsToMove == 0) {
                    if (playerSide == 0) {
                        continue;
                    } else {
                        playerSide = 1;
                    }
                } else {
                    if (playerSide == 0) {
                        playerSide = 1;
                    } else {
                        playerSide = 0;
                    }
                }
                house = 0;
            }
        }
    }

    public boolean isValidHouse(int playerId, int house) {
        return houseBoard.get(playerId - 1).get(house - 1).getSeedCount() != 0;
    }

    public boolean playerHasWon(int playerId) {
        for (House house: houseBoard.get(playerId - 1)) {
            if(house.getSeedCount() != 0) {
                return false;
            }
        }
        return true;
    }

    public void drawGameState(IO io) {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[ " + gameState.getSeedsAtHouse(2, 6) + "] | 5[ " + gameState.getSeedsAtHouse(2, 5) + "] | 4[ " + gameState.getSeedsAtHouse(2, 4) + "] | 3[ " + gameState.getSeedsAtHouse(2, 3) + "] | 2[ " + gameState.getSeedsAtHouse(2, 2) + "] | 1[ " + gameState.getSeedsAtHouse(2, 1) + "] |  " + gameState.getPlayerStore(1) + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("|  " + gameState.getPlayerStore(2) + " | 1[ " + gameState.getSeedsAtHouse(1, 1) + "] | 2[ " + gameState.getSeedsAtHouse(1, 2) + "] | 3[ " + gameState.getSeedsAtHouse(1, 3) + "] | 4[ " + gameState.getSeedsAtHouse(1, 4) + "] | 5[ " + gameState.getSeedsAtHouse(1, 5) + "] | 6[ " + gameState.getSeedsAtHouse(1, 6) + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }
}
