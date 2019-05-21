package kalah.game_audit;

import kalah.game_objects.GameState;
import kalah.misc.LogicalConstants;

public class AuditHandler {

    public boolean gameStateAnomalyPresent(GameState gameState) {
        return gameState.playerHasWon(gameState.getPlayerTurn());
    }

    public boolean inputAnomalyPresent(int inputArgument, GameState gameState) {
        // returns boolean about if there is is something that requires further action about the game state or player input
        if (getAnomaly(inputArgument, gameState) == null) {
            return false;
        }
        return true;
    }

    public AuditType getAnomaly(int inputArgument, GameState gameState) {
        // switch case for determining what the thing that requires further action is
        if(gameStateAnomalyPresent(gameState)) {
            return AuditType.EndCondition;
        } else if(inputArgument == LogicalConstants.CANCEL_RESULT_OUTPUT) {
            return AuditType.PlayerQuit;
        } else if(!gameState.isValidHouse(gameState.getPlayerTurn(), inputArgument)) {
            return AuditType.EmptyHouse;
        } else {
            return null;
        }
    }
}
