package kalah.game_objects;

import kalah.seed_containers.House;

public class GameState {
    private int playerTurn;
    private GameBoard gameBoard;

    public GameState() {
        gameBoard = new GameBoard();
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerId) {
        playerTurn = playerId;
    }

    public boolean isValidHouse(int playerId, int house) {
        return gameBoard.getSeedsAtHouse(playerId, house - 1) != 0;
    }

    public boolean playerHasWon(int playerId) {
        for (House house: gameBoard.getHousesForPlayer(playerId)) {
            if(house.getSeedCount() != 0) {
                return false;
            }
        }
        return true;
    }

    public int getPlayerStoreScore(int playerId) {
        return gameBoard.getScoreForPlayer(playerId);
    }

    public void performCaptureForPlayerAt(int playerId, House targetHouse) {
        gameBoard.addSeedToPlayerStore(playerId, targetHouse.getOppositeHouse().getAndEmptySeeds() + 1);
    }

    public int getPlayerFinalScore(int playerId) {
        return gameBoard.getSeedsInHousesForPlayer(playerId) + gameBoard.getScoreForPlayer(playerId);
    }

    public int getSeedsAtHouse(int playerSide, int houseIndex) {
        return gameBoard.getSeedsAtHouse(playerSide, houseIndex);
    }

    public House getFirstHouseForPlayer(int playerId) {
        return gameBoard.getFirstHouseForPlayer(playerId);
    }

    public int moveSeedsAt(int playerSide, int houseIndex) {
        return gameBoard.getAndClearSeedsAt(playerSide, houseIndex);
    }

    public House getHouseAt(int playerSide, int houseIndex) {
        return gameBoard.getHouseAt(playerSide, houseIndex);
    }

    public void addSeedsToHouse(House targetHouse, int numberOfSeeds) {
        gameBoard.addSeedToHouse(targetHouse, numberOfSeeds);
    }

    public void addSeedsToStore(int playerId, int numberOfSeeds) {
        gameBoard.addSeedToPlayerStore(playerId, numberOfSeeds);
    }
}
