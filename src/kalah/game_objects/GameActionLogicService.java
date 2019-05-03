package kalah.game_objects;

import kalah.misc.PlayerId;
import kalah.seed_containers.House;

public class GameActionLogicService {
    public boolean CaptureCondition(House targetHouse, int playerSide, GameState gameState) {
        return targetHouse.getOppositeHouse().getSeedCount() > 0 && targetHouse.getSeedCount() == 0 && playerSide == gameState.getPlayerTurn();
    }

    public boolean addSeedToStoreCondition(int playerSide, int playerId, int numberOfSeedsToMove) {
        return playerSide == playerId && numberOfSeedsToMove != 0;
    }

    public int distributeSeedsOnPlayerSide(House targetHouse, int numberOfSeedsToMove, int playerSide, GameState gameState) {
        while (targetHouse != null && numberOfSeedsToMove > 0) {

            if (numberOfSeedsToMove == 1) {
                if(CaptureCondition(targetHouse, playerSide, gameState)) {
                    gameState.performCaptureForPlayerAt(playerSide, targetHouse);
                } else {
                    gameState.addSeedsToHouse(targetHouse, 1);
                }
                numberOfSeedsToMove--;
                break;
            }

            gameState.addSeedsToHouse(targetHouse, 1);
            targetHouse = targetHouse.getUpperHouse();
            numberOfSeedsToMove--;
        }
        return numberOfSeedsToMove;
    }

    public boolean lastSeedDistributedAtHouse(int playerSide, int playerId, int numberOfSeedsToMove, GameState gameState) {
        if (addSeedToStoreCondition(playerSide, playerId, numberOfSeedsToMove)) {
            gameState.addSeedsToStore(playerSide, 1);
            numberOfSeedsToMove--;

            if(numberOfSeedsToMove == 0) {
                return true;
            }
        }
        return false;
    }

    public int determineNextPlayerSide(int numberOfSeedsToMove, int currentPlayerSide) {
        if(numberOfSeedsToMove != 0) {
            if (currentPlayerSide == PlayerId.PLAYER_1.getPlayerValue()) {
                return PlayerId.PLAYER_2.getPlayerValue();
            } else {
                return PlayerId.PLAYER_1.getPlayerValue();
            }
        }
        return currentPlayerSide;
    }

    private int determineNextPlayerTurn(boolean lastSeedSownAtStore, GameState gameState) {
        if(lastSeedSownAtStore) {
            return gameState.getPlayerTurn();
        } else {
            if(gameState.getPlayerTurn() == PlayerId.PLAYER_1.getPlayerValue()) {
                return PlayerId.PLAYER_2.getPlayerValue();
            } else {
                return PlayerId.PLAYER_1.getPlayerValue();
            }
        }
    }

    public House getFirstHouseForPlayer(int playerId, GameState gameState){
        return gameState.getFirstHouseForPlayer(playerId);
    }

    public void setPlayerTurnTo(boolean lastSeedSownAtStore, GameState gameState) {
        gameState.setPlayerTurn(determineNextPlayerTurn(lastSeedSownAtStore, gameState));
    }
}
