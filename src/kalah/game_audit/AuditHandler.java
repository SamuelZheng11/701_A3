package kalah.game_audit;

import kalah.game_objects.GameState;
import kalah.misc.Constants;

public class AuditHandler {

    public boolean gameStateAnomalyPresent(GameState gameState) {
        return gameState.playerHasWon(gameState.getPlayerTurn());
    }

    public boolean inputAnomalyPresent(int inputArgument, GameState gameState) {
        if (getAnomaly(inputArgument, gameState) == null) {
            return false;
        }
        return true;
    }

    public AuditType getAnomaly(int inputArgument, GameState gameState) {
        if(gameStateAnomalyPresent(gameState)) {
            return AuditType.EndCondition;
        } else if(inputArgument == Constants.CANCEL_RESULT_OUTPUT) {
            return AuditType.PlayerQuit;
        } else if(!gameState.isValidHouse(gameState.getPlayerTurn(), inputArgument)) {
            return AuditType.EmptyHouse;
        } else {
            return null;
        }
    }
}
