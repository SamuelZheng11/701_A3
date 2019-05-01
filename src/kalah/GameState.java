package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.ArrayList;

public class GameState {
    private static GameState gameState;
    private ArrayList<ArrayList<House>> houseBoard;
    // HashMap to map player Id to store count
    private ArrayList<Store> playerStores;
    private int playerTurn;
    private static final int PLAYER_1_ID = 0;
    private static final int PLAYER_2_ID = 1;
    private static final int NUMBER_OF_HOUSES = 6;

    private GameState() {
        playerTurn = PLAYER_1_ID;
        houseBoard = new ArrayList<>();
        houseBoard.add(new ArrayList<>(NUMBER_OF_HOUSES));
        for (int i = 0; i < NUMBER_OF_HOUSES; i++) {
            houseBoard.get(PLAYER_1_ID).add(new House());
        }

        houseBoard.add(new ArrayList<>(NUMBER_OF_HOUSES));
        for (int i = 0; i < NUMBER_OF_HOUSES; i++) {
            houseBoard.get(PLAYER_2_ID).add(new House());
        }

        // Setup links between each house
        houseBoard.get(PLAYER_1_ID).get(0).setupHouseRelation(houseBoard.get(0).get(1), houseBoard.get(1).get(5));
        houseBoard.get(PLAYER_1_ID).get(1).setupHouseRelation(houseBoard.get(PLAYER_1_ID).get(2), houseBoard.get(PLAYER_2_ID).get(4));
        houseBoard.get(PLAYER_1_ID).get(2).setupHouseRelation(houseBoard.get(PLAYER_1_ID).get(3), houseBoard.get(PLAYER_2_ID).get(3));
        houseBoard.get(PLAYER_1_ID).get(3).setupHouseRelation(houseBoard.get(PLAYER_1_ID).get(4), houseBoard.get(PLAYER_2_ID).get(2));
        houseBoard.get(PLAYER_1_ID).get(4).setupHouseRelation(houseBoard.get(PLAYER_1_ID).get(5), houseBoard.get(PLAYER_2_ID).get(1));
        houseBoard.get(PLAYER_1_ID).get(5).setupHouseRelation(null, houseBoard.get(PLAYER_2_ID).get(0));

        houseBoard.get(PLAYER_2_ID).get(0).setupHouseRelation(houseBoard.get(PLAYER_2_ID).get(1), houseBoard.get(PLAYER_1_ID).get(5));
        houseBoard.get(PLAYER_2_ID).get(1).setupHouseRelation(houseBoard.get(PLAYER_2_ID).get(2), houseBoard.get(PLAYER_1_ID).get(4));
        houseBoard.get(PLAYER_2_ID).get(2).setupHouseRelation(houseBoard.get(PLAYER_2_ID).get(3), houseBoard.get(PLAYER_1_ID).get(3));
        houseBoard.get(PLAYER_2_ID).get(3).setupHouseRelation(houseBoard.get(PLAYER_2_ID).get(4), houseBoard.get(PLAYER_1_ID).get(2));
        houseBoard.get(PLAYER_2_ID).get(4).setupHouseRelation(houseBoard.get(PLAYER_2_ID).get(5), houseBoard.get(PLAYER_1_ID).get(1));
        houseBoard.get(PLAYER_2_ID).get(5).setupHouseRelation(null, houseBoard.get(PLAYER_1_ID).get(0));

        playerStores = new ArrayList<>();
        playerStores.add(new Store());
        playerStores.add(new Store());
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
        return houseBoard.get(playerId).get(house - 1).getSeedCount();
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void updateGameState(int playerId, int house) {
        int playerSide = playerId;
        boolean lastSeedSownAtStore = false;
        boolean hasCapture = false;
        int numberOfSeedsToMove = houseBoard.get(playerId).get(house - 1).getAndEmptySeeds();
        House targetHouse = houseBoard.get(playerId).get(house - 1).getUpperHouse();
        while (numberOfSeedsToMove != 0) {

            while (targetHouse != null && numberOfSeedsToMove > 0) {

                if(numberOfSeedsToMove == 1) {
                    if(targetHouse.getOppositeHouse().getSeedCount() > 0 && targetHouse.getSeedCount() == 0 && playerSide == playerTurn) {
                        playerStores.get(playerSide).addSeedsToStore(targetHouse.getOppositeHouse().getAndEmptySeeds() + 1);
                        hasCapture = true;
                    } else {
                        targetHouse.addSeeds(1);
                    }
                    numberOfSeedsToMove--;
                    break;
                }

                targetHouse.addSeeds(1);
                targetHouse = targetHouse.getUpperHouse();
                numberOfSeedsToMove--;
            }


            if (playerSide == playerId && numberOfSeedsToMove != 0) {
                playerStores.get(playerSide).addSeedsToStore(1);
                numberOfSeedsToMove--;

                if(numberOfSeedsToMove == 0) {
                    lastSeedSownAtStore = true;
                    break;
                }
            }

            if(numberOfSeedsToMove != 0) {
                if (playerSide == PLAYER_1_ID) {
                    playerSide = PLAYER_2_ID;
                } else {
                    playerSide = PLAYER_1_ID;
                }
            }

            targetHouse = houseBoard.get(playerSide).get(0);
        }

        if(hasCapture || lastSeedSownAtStore) {
            return;
        } else {
            if(playerTurn == PLAYER_1_ID) {
                playerTurn = PLAYER_2_ID;

            } else {
                playerTurn = PLAYER_1_ID;
            }
        }
        //            for (int i = house; i < houseBoard.get(playerSide).size(); i++) {
//                if (playerSide == playerId - 1 && numberOfSeedsToMove == 1 && houseBoard.get(playerSide).get(i).getSeedCount() == 0) {
//                    int seedsToSow = 1;
//                    if (playerSide == 0 && houseBoard.get(playerSide).get(i).getOppositeHouse().getSeedCount() != 0) {
//                        hasCapture = true;
//                        seedsToSow += houseBoard.get(playerSide).get(i).getOppositeHouse().getAndEmptySeeds();
//                        for (int j = 0; j < seedsToSow; j++) {
//                            playerStores.get(playerId).addSeedToStore();
//                        }
//                    } else if (playerSide == 1 && houseBoard.get(playerSide).get(i).getOppositeHouse().getSeedCount() != 0) {
//                        hasCapture = true;
//                        seedsToSow += houseBoard.get(playerSide).get(i).getOppositeHouse().getAndEmptySeeds();
//                        for (int j = 0; j < seedsToSow; j++) {
//                            playerStores.get(playerId).addSeedToStore();
//                        }
//                    }
//                    numberOfSeedsToMove--;
//                    break;
//                } else if (numberOfSeedsToMove != 0) {
//                    houseBoard.get(playerSide).get(i).addSeed();
//                    numberOfSeedsToMove--;
//                }
//            }
//
//            if(numberOfSeedsToMove <= 1) {
//                if (numberOfSeedsToMove == 1|| hasCapture) {
//                    if (playerId == 1) {
//                        playerTurn = 1;
//                    } else {
//                        playerTurn = 2;
//                    }
//                    hasCapture = false;
//                } else {
//                    if (playerId == 1) {
//                        playerTurn = 2;
//                    } else {
//                        playerTurn = 1;
//                    }
//                }
//            }
//
//            if (numberOfSeedsToMove >= 1 && playerSide == playerId - 1) {
//                playerStores.get(playerId).addSeedToStore();
//                numberOfSeedsToMove--;
//
//                if(numberOfSeedsToMove == 0) {
//                    if (playerSide == 0) {
//                        continue;
//                    } else {
//                        playerSide = 1;
//                    }
//                } else {
//                    if (playerSide == 0) {
//                        playerSide = 1;
//                    } else {
//                        playerSide = 0;
//                    }
//                }
//                house = 0;
//            }
    }

    public boolean isValidHouse(int playerId, int house) {
        return houseBoard.get(playerId).get(house - 1).getSeedCount() != 0;
    }

    public boolean playerHasWon(int playerId) {
        for (House house: houseBoard.get(playerId)) {
            if(house.getSeedCount() != 0) {
                return false;
            }
        }
        return true;
    }

    public void drawGameState(IO io) {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[ " + gameState.getSeedsAtHouse(1, 6) + "] | 5[ " + gameState.getSeedsAtHouse(1, 5) + "] | 4[ " + gameState.getSeedsAtHouse(1, 4) + "] | 3[ " + gameState.getSeedsAtHouse(1, 3) + "] | 2[ " + gameState.getSeedsAtHouse(1, 2) + "] | 1[ " + gameState.getSeedsAtHouse(1, 1) + "] |  " + gameState.getPlayerStore(0) + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("|  " + gameState.getPlayerStore(1) + " | 1[ " + gameState.getSeedsAtHouse(0, 1) + "] | 2[ " + gameState.getSeedsAtHouse(0, 2) + "] | 3[ " + gameState.getSeedsAtHouse(0, 3) + "] | 4[ " + gameState.getSeedsAtHouse(0, 4) + "] | 5[ " + gameState.getSeedsAtHouse(0, 5) + "] | 6[ " + gameState.getSeedsAtHouse(0, 6) + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }
}
