package kalah.game_objects;

import kalah.misc.PlayerId;
import kalah.seed_containers.House;
import kalah.seed_containers.Store;

import java.util.ArrayList;

public class GameBoard {
    private int numberOfPlayers;
    private int numberOfHouses;
    private ArrayList<Store> playerStores;
    private ArrayList<ArrayList<House>> houseBoard;

    public GameBoard(int numberOfPlayers, int numberOfHouses) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfHouses = numberOfHouses;
        houseBoard = new ArrayList<>();
        playerStores = new ArrayList<>();

        for (int i = 0; i <numberOfPlayers ; i++) {
            houseBoard.add(new ArrayList<House>(numberOfHouses));

            for (int j = 0; j < numberOfHouses; j++) {
                houseBoard.get(i).add(new House());
            }
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            playerStores.add(new Store());
        }

        for (int i = 0; i < numberOfPlayers ; i++) {
            // Setup links between each of player's houses
            for (int j = 0; j < numberOfHouses - 1; j++) {
                if (i + 1 < numberOfPlayers) {
                    houseBoard.get(i).get(j).setupHouseRelation(houseBoard.get(i).get(j + 1), houseBoard.get(i + 1).get(numberOfHouses - (j + 1)));
                } else {
                    houseBoard.get(i).get(j).setupHouseRelation(houseBoard.get(i).get(j + 1), houseBoard.get(PlayerId.PLAYER_1.getPlayerValue()).get(numberOfHouses - (j + 1)));
                }
            }

            // link up last house for each player
            if (i + 1 < numberOfPlayers) {
                houseBoard.get(i).get(numberOfHouses - 1).setupHouseRelation(null, houseBoard.get(i + 1).get(0));
            } else {
                houseBoard.get(i).get(numberOfHouses - 1).setupHouseRelation(null, houseBoard.get(PlayerId.PLAYER_1.getPlayerValue()).get(0));
            }
        }
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

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }
}
