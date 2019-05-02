package kalah.game_objects;

import kalah.misc.Player_Id;
import kalah.seed_containers.House;

public class GameActionLogicService {
    public boolean CaptureConditionMet(House targetHouse, int playerSide, GameState gameState) {
        return targetHouse.getOppositeHouse().getSeedCount() > 0 && targetHouse.getSeedCount() == 0 && playerSide == gameState.getPlayerTurn();
    }

    public int distributeSeedsOnPlayerSide(House targetHouse, int numberOfSeedsToMove, int playerSide, GameState gameState) {
        while (targetHouse != null && numberOfSeedsToMove > 0) {

            if (numberOfSeedsToMove == 1) {
                if(CaptureConditionMet(targetHouse, playerSide, gameState)) {
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

    public boolean canAddSeedToStore(int playerSide, int playerId, int numberOfSeedsToMove) {
        return playerSide == playerId && numberOfSeedsToMove != 0;
    }

    public boolean lastSeedDistributedAtHouse(int playerSide, int playerId, int numberOfSeedsToMove, GameState gameState) {
        if (canAddSeedToStore(playerSide, playerId, numberOfSeedsToMove)) {
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
            if (currentPlayerSide == Player_Id.PLAYER_1.getNumVal()) {
                return Player_Id.PLAYER_2.getNumVal();
            } else {
                return Player_Id.PLAYER_1.getNumVal();
            }
        }
        return currentPlayerSide;
    }

    public int determineNextPlayerTurn(boolean lastSeedSownAtStore, GameState gameState) {
        if(lastSeedSownAtStore) {
            return gameState.getPlayerTurn();
        } else {
            if(gameState.getPlayerTurn() == Player_Id.PLAYER_1.getNumVal()) {
                return Player_Id.PLAYER_2.getNumVal();
            } else {
                return Player_Id.PLAYER_1.getNumVal();
            }
        }
    }
}
