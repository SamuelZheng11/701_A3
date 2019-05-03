package kalah.game_objects;

import kalah.seed_containers.House;

public class GameActionPerformer {
    private static GameActionPerformer gameActionPerformer;
    private GameActionLogicService gameActionLogicService = new GameActionLogicService();

    private GameActionPerformer() {}

    public static GameActionPerformer getGameActionPerformer() {
        if (gameActionPerformer == null) {
            gameActionPerformer = new GameActionPerformer();
        }
        return gameActionPerformer;
    }

    public void distributeSeedsAt(int playerId, int house, GameState gameState) {
        int playerSide = playerId;
        int houseIndex = house - 1;
        boolean lastSeedSownAtStore = false;
        int numberOfSeedsToMove = gameState.moveSeedsAt(playerSide, houseIndex);
        House targetHouse = gameState.getHouseAt(playerSide, houseIndex).getUpperHouse();

        while (numberOfSeedsToMove > 0) {

            numberOfSeedsToMove = gameActionLogicService.distributeSeedsOnPlayerSide(targetHouse, numberOfSeedsToMove, playerSide, gameState);

            lastSeedSownAtStore = gameActionLogicService.lastSeedDistributedAtHouse(playerSide, playerId, numberOfSeedsToMove, gameState);

            if (gameActionLogicService.addSeedToStoreCondition(playerSide, playerId, numberOfSeedsToMove)) {
                numberOfSeedsToMove--;
            }

            playerSide = gameActionLogicService.determineNextPlayerSide(numberOfSeedsToMove, playerSide);

            targetHouse = gameActionLogicService.getFirstHouseForPlayer(playerSide, gameState);
        }

        gameActionLogicService.setPlayerTurnTo(lastSeedSownAtStore, gameState);
    }
}
