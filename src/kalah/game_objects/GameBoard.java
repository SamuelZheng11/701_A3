package kalah.game_objects;

import kalah.misc.Player_Id;
import kalah.seed_containers.House;
import kalah.seed_containers.Store;

import java.util.ArrayList;

public class GameBoard {
    private static final int NUMBER_OF_HOUSES = 6;
    private ArrayList<Store> playerStores;
    private ArrayList<ArrayList<House>> houseBoard;

    public GameBoard() {
        houseBoard = new ArrayList<>();
        houseBoard.add(new ArrayList<House>(NUMBER_OF_HOUSES));
        for (int i = 0; i < NUMBER_OF_HOUSES; i++) {
            houseBoard.get(Player_Id.PLAYER_1.getNumVal()).add(new House());
        }

        houseBoard.add(new ArrayList<House>(NUMBER_OF_HOUSES));
        for (int i = 0; i < NUMBER_OF_HOUSES; i++) {
            houseBoard.get(Player_Id.PLAYER_2.getNumVal()).add(new House());
        }

        // Setup links between each of player 1's houses
        for (int i = 0; i < NUMBER_OF_HOUSES - 1; i++) {
            houseBoard.get(Player_Id.PLAYER_1.getNumVal()).get(i).setupHouseRelation(houseBoard.get(Player_Id.PLAYER_1.getNumVal()).get(i + 1), houseBoard.get(Player_Id.PLAYER_2.getNumVal()).get(NUMBER_OF_HOUSES - (i + 1)));
        }

        // Setup links between each of player 2's houses
        for (int i = 0; i < NUMBER_OF_HOUSES - 1; i++) {
            houseBoard.get(Player_Id.PLAYER_2.getNumVal()).get(i).setupHouseRelation(houseBoard.get(Player_Id.PLAYER_2.getNumVal()).get(i + 1), houseBoard.get(Player_Id.PLAYER_1.getNumVal()).get(NUMBER_OF_HOUSES - (i + 1)));
        }

        //link up last houses for each player
        houseBoard.get(Player_Id.PLAYER_1.getNumVal()).get(5).setupHouseRelation(null, houseBoard.get(Player_Id.PLAYER_2.getNumVal()).get(0));
        houseBoard.get(Player_Id.PLAYER_2.getNumVal()).get(5).setupHouseRelation(null, houseBoard.get(Player_Id.PLAYER_1.getNumVal()).get(0));

        playerStores = new ArrayList<>();
        playerStores.add(new Store());
        playerStores.add(new Store());
    }

    public int getSeedsAtHouse(int playerId, int houseIndex){
        return houseBoard.get(playerId).get(houseIndex).getSeedCount();
    }

    public int getAndClearSeedsAt(int playerId, int houseIndex){
        return houseBoard.get(playerId).get(houseIndex).getAndEmptySeeds();
    }

    public House getHouseAt(int playerId, int houseIndex) {
        return houseBoard.get(playerId).get(houseIndex);
    }

    public void addSeedToHouse(House house, int numberOfSeeds) {
        house.addSeeds(numberOfSeeds);
    }

    public void addSeedToPlayerStore(int playerId, int numberOfSeeds){
        playerStores.get(playerId).addSeeds(numberOfSeeds);
    }

    public ArrayList<House> getHousesForPlayer(int playerId) {
        return houseBoard.get(playerId);
    }

    public int getSeedsInHousesForPlayer(int playerId) {
        int seeds = 0;
        for (House house: houseBoard.get(playerId)) {
            seeds += house.getSeedCount();
        }
        return seeds;
    }

    public int getScoreForPlayer(int playerId) {
        return playerStores.get(playerId).getSeedCount();
    }

    public House getFirstHouseForPlayer(int playerId){
        return houseBoard.get(playerId).get(0);
    }
}
