package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.IO.BoardLayout.HorizontalBoardLayout;
import kalah.IO.GameOverMessageManager;
import kalah.IO.IOHandler;
import kalah.IO.SeedDigitFormatter;
import kalah.game_audit.AuditHandler;
import kalah.game_audit.AuditType;
import kalah.game_objects.GameActionPerformer;
import kalah.game_objects.GameState;
import kalah.misc.LogicalConstants;

public class GameManager {
    private GameState gameState;
    private GameActionPerformer gameActionPerformer;
    private IOHandler ioHandler;
    private AuditHandler auditHandler;

    public GameManager(IO io) {
        // Setup the Kalah game and necessary support classes
        gameState = new GameState(LogicalConstants.DEFAULT_NUMBER_OF_PLAYERS, LogicalConstants.DEFAULT_NUMBER_OF_HOUSES);
        gameActionPerformer = new GameActionPerformer();
        ioHandler = new IOHandler(io, new HorizontalBoardLayout(new SeedDigitFormatter()));
        auditHandler = new AuditHandler();
    }

    public void start() {
        while (true) {
            ioHandler.drawGameState(gameState);

            if (auditHandler.gameStateAnomalyPresent(gameState)) {
                // this if statement does not have any switch statements because there is only one type of game state anomaly
                // and that is when all houses on one side is 0, In future a switch case could be added if there are more
                // types of game state anomaly
                ioHandler.setGameOverMessageManager(new GameOverMessageManager(gameState));
                ioHandler.printGameSummary(gameState);
                break;
            }

            int playerTurn = gameState.getPlayerTurn();
            int inputArgument = ioHandler.getUserInput(playerTurn);

            if (!auditHandler.inputAnomalyPresent(inputArgument, gameState)) {
                gameActionPerformer.distributeSeedsAt(playerTurn, inputArgument, gameState);
            } else {
                AuditType auditType = auditHandler.getAnomaly(inputArgument, gameState);
                if (auditType == AuditType.EmptyHouse) {
                    ioHandler.userSelectedAnEmptyHouseMessage();
                } else if (auditType == AuditType.PlayerQuit) {
                    ioHandler.setGameOverMessageManager(new GameOverMessageManager(gameState));
                    ioHandler.printEndGameMessageSet(gameState);
                    break;
                } else if (auditType == AuditType.EndCondition) {
                    ioHandler.setGameOverMessageManager(new GameOverMessageManager(gameState));
                    ioHandler.printGameSummary(gameState);
                    break;
                }
            }
        }
    }
}
