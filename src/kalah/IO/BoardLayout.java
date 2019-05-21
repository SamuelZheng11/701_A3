package kalah.IO;

import kalah.game_objects.GameState;

import java.util.List;

public interface BoardLayout {
    List<String> getPrintableBoardFor(GameState gameState);
}
