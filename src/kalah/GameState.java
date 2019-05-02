package kalah;

public class GameState {
    private int playerTurn;
    private GameBoard gameBoard;

    public GameState() {
        gameBoard = new GameBoard();
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void updateGameState(int playerId, int house) {
        int playerSide = playerId;
        int houseIndex = house - 1;
        boolean lastSeedSownAtStore = false;
        int numberOfSeedsToMove = gameBoard.getAndClearSeedsAt(playerSide, houseIndex);
        House targetHouse = gameBoard.getHouseAt(playerSide, houseIndex).getUpperHouse();
        while (numberOfSeedsToMove != 0) {

            while (targetHouse != null && numberOfSeedsToMove > 0) {

                if(numberOfSeedsToMove == 1) {
                    if(targetHouse.getOppositeHouse().getSeedCount() > 0 && targetHouse.getSeedCount() == 0 && playerSide == playerTurn) {
                        gameBoard.performCaptureForPlayerAt(playerSide, targetHouse);
                    } else {
                        gameBoard.addSeedToHouse(targetHouse);
                    }
                    numberOfSeedsToMove--;
                    break;
                }

                gameBoard.addSeedToHouse(targetHouse);
                targetHouse = targetHouse.getUpperHouse();
                numberOfSeedsToMove--;
            }


            if (playerSide == playerId && numberOfSeedsToMove != 0) {
                gameBoard.addSeedToPlayerStore(playerSide);
                numberOfSeedsToMove--;

                if(numberOfSeedsToMove == 0) {
                    lastSeedSownAtStore = true;
                    break;
                }
            }

            if(numberOfSeedsToMove != 0) {
                if (playerSide == Player_Id.PLAYER_1.getNumVal()) {
                    playerSide = Player_Id.PLAYER_2.getNumVal();
                } else {
                    playerSide = Player_Id.PLAYER_1.getNumVal();
                }
            }

            targetHouse = gameBoard.getFirstHouseForPlayer(playerSide);
        }

        if(lastSeedSownAtStore) {
            return;
        } else {
            if(playerTurn == Player_Id.PLAYER_1.getNumVal()) {
                playerTurn = Player_Id.PLAYER_2.getNumVal();

            } else {
                playerTurn = Player_Id.PLAYER_1.getNumVal();
            }
        }
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

    public int getPlayerFinalScore(int playerId) {
        return gameBoard.getSeedsInHousesForPlayer(playerId) + gameBoard.getScoreForPlayer(playerId);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
