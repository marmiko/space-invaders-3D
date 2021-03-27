package tp.p1.logic.elements;

import tp.p1.logic.Game;
import tp.p1.logic.Level;
import tp.p1.util.Position;

public class MainMenuElement extends SelectableMenuElement {

    public static final int MaxSelectableElements = 3;

    private static final Level[] AvailableGameLevels = new Level[] {Level.parse("EASY"), Level.parse("HARD"), Level.parse("INSANE")};

    private Level level;

    public MainMenuElement(Game game, Position initialPos, int menuID) throws Exception{
        super(game, initialPos, menuID, String.format("%s.png", AvailableGameLevels[menuID].name()), MaxSelectableElements);
        this.level = AvailableGameLevels[this.menuID];
    }

    protected void action() {
        try{
            this.game.initGame(level);
        } catch (Exception e) {
            System.err.println("Couldn't initialize game correctly. Reason:");
            System.err.println(e.getMessage());
        }
    }

}
