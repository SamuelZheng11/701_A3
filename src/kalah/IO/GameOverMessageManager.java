package kalah.IO;

import kalah.game_objects.GameState;
import kalah.misc.PlayerId;

public class GameOverMessageManager {
    private GameState gameState;

    public GameOverMessageManager(GameState gameState) {
        this.gameState = gameState;
    }

    public String getGameOverMessage() {
        return PrintableConstants.GAME_OVER_MESSAGE;
    }

    public String getPlayerScoreFor(PlayerId playerId) {
        return "\tplayer " + (playerId.getPlayerValue()+1) + ":" + gameState.getPlayerFinalScore(playerId.getPlayerValue());
    }

    public String getResultMessage(GameState gameState) {
        // determine who has the highest score or if its a draw by adding store score and house score
        if(gameState.getPlayerFinalScore(PlayerId.PLAYER_1.getPlayerValue()) > gameState.getPlayerFinalScore(PlayerId.PLAYER_2.getPlayerValue())) {
            return PrintableConstants.PLAYER_1_WINS_MESSAGE;
        } else if (gameState.getPlayerFinalScore(PlayerId.PLAYER_1.getPlayerValue()) < gameState.getPlayerFinalScore(PlayerId.PLAYER_2.getPlayerValue())) {
            return PrintableConstants.PLAYER_2_WINS_MESSAGE;
        } else {
            return PrintableConstants.TIE_MESSAGE;
        }
    }
}
