package tp.p1.logic.elements;

import tp.p1.logic.Game;
import tp.p1.util.Position;

public class EndMenuElement extends SelectableMenuElement {
    public static final int MaxSelectableElements = 2;

    private static final String[] AvailableOptions = new String[] {"PLAY_AGAIN", "EXIT"};

    public EndMenuElement(Game game, Position initialPos, int menuID) throws Exception{
        super(game, initialPos, menuID, String.format("%s.png", AvailableOptions[menuID]), MaxSelectableElements);

    }

    protected void action() {
        try{
            if (menuID==0) this.game.initGameAgain();
            else this.game.exit();
        } catch (Exception e) {
            System.err.println("Couldn't perform action correctly (end menu). Reason:");
            System.err.println(e.getMessage());
        }
    }
}
