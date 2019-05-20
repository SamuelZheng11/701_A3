package kalah.game_objects;

import kalah.misc.PlayerId;
import kalah.seed_containers.House;

public class GameActionLogicService {
    public boolean CaptureCondition(House targetHouse, int playerSide, GameState gameState) {
        // determine if the last seed sown constitutes a capture event
        return targetHouse.getOppositeHouse().getSeedCount() > 0 && targetHouse.getSeedCount() == 0 && playerSide == gameState.getPlayerTurn();
    }

    public boolean addSeedToStoreCondition(int playerSide, int playerId, int numberOfSeedsToMove) {
        // determines if a store can but put into a store (if it is not the player's store then do no put one in)
        return playerSide == playerId && numberOfSeedsToMove != 0;
    }

    public int distributeSeedsOnPlayerSide(House targetHouse, int numberOfSeedsToMove, int playerSide, GameState gameState) {
        // puts one seed in each house on the specified side, starting at the specified house going up
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
        // determines if the last seed was put in the player's store
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
        // determines which side of the board it is on, used to distribute seeds if there is an excess after putting one
        // in the player's store
        if(numberOfSeedsToMove != 0) {
            if (currentPlayerSide == PlayerId.PLAYER_1.getPlayerValue()) {
                return PlayerId.PLAYER_2.getPlayerValue();
            } else {
                return PlayerId.PLAYER_1.getPlayerValue();
            }
        }
        return currentPlayerSide;
    }

    private boolean replayTurn(boolean lastSeedSownAtStore) {
        // determine who's turn is it on the next move
        if(lastSeedSownAtStore) {
            return true;
        }
        return false;
    }

    public House getFirstHouseForPlayer(int playerId, GameState gameState){
        return gameState.getFirstHouseForPlayer(playerId);
    }

    public void rotatePlayerTurnIfNeeded(boolean lastSeedSownAtStore, GameState gameState) {
        // only rotate the turns if it is needed according to Kalah rules
        if (replayTurn(lastSeedSownAtStore)) {
            return;
        } else {
            gameState.rotatePlayerTurn();
        }
    }
}
