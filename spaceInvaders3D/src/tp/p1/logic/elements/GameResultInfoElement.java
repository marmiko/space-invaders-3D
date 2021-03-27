package tp.p1.logic.elements;

import tp.p1.logic.Game;
import tp.p1.util.Position;

public class GameResultInfoElement extends MenuElement {

    private static final int ElementMenuID = -1;
    private static final String[] ResultTextures = new String[] {"PlayerWon", "AliensWon", "ErrorNoWinner"};

    private static final float SCALE = 0.7f;

    public GameResultInfoElement(Game game, Position initialPos) throws Exception {
        super(game, initialPos, ElementMenuID, String.format("%s.png", (game.playerWins()?ResultTextures[0]:(game.aliensWin()?ResultTextures[1]:ResultTextures[2]))), SCALE);
    }

}
