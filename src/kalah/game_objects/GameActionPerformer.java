package kalah.game_objects;

import kalah.seed_containers.House;

public class GameActionPerformer {
    private GameActionLogicService gameActionLogicService = new GameActionLogicService();

    public void distributeSeedsAt(int playerId, int house, GameState gameState) {
        // sets up the logic flow for a player's turn
        int playerSide = playerId;
        int houseIndex = house - 1;
        boolean lastSeedSownAtStore = false;
        int numberOfSeedsToMove = gameState.moveSeedsAt(playerSide, houseIndex);
        House targetHouse = gameState.getHouseAt(playerSide, houseIndex).getUpperHouse();

        // don't stop distributing the seeds until there are 0 seeds left to sow
        while (numberOfSeedsToMove > 0) {

            numberOfSeedsToMove = gameActionLogicService.distributeSeedsOnPlayerSide(targetHouse, numberOfSeedsToMove, playerSide, gameState);

            lastSeedSownAtStore = gameActionLogicService.lastSeedDistributedAtHouse(playerSide, playerId, numberOfSeedsToMove, gameState);

            if (gameActionLogicService.addSeedToStoreCondition(playerSide, playerId, numberOfSeedsToMove)) {
                numberOfSeedsToMove--;
            }

            playerSide = gameActionLogicService.determineNextPlayerSide(numberOfSeedsToMove, playerSide);

            targetHouse = gameActionLogicService.getFirstHouseForPlayer(playerSide, gameState);
        }

        gameActionLogicService.rotatePlayerTurnIfNeeded(lastSeedSownAtStore, gameState);
    }
}
