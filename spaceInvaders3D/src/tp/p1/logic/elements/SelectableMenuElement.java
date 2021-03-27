package tp.p1.logic.elements;

import org.joml.Vector2d;
import org.joml.Vector3f;
import tp.p1.graphic_interface_3D.Window;
import tp.p1.logic.Game;
import tp.p1.util.Position;


public abstract class SelectableMenuElement extends MenuElement {

    private static int selectedMenuID  = 0;
    private static int maxSelectableElements;
    private static boolean wasConfirmed = false;


    SelectableMenuElement(Game game, Position initialPos, int menuID, String textureFileName, int maxSelectableElements) throws Exception{
        super(game, initialPos, menuID, textureFileName);
        SelectableMenuElement.maxSelectableElements=maxSelectableElements;
    }

    @Override
    public void computerAction() {
        if(wasConfirmed && selectedMenuID == this.menuID){
            if (this.scale != BiggerScale) this.scale = BiggerScale;
            action();
            wasConfirmed = false;
        }
        else if (selectedMenuID == this.menuID){
            this.scale = BiggerScale;
        }
        else if (this.scale == BiggerScale){
            this.scale = NormalScale;
        }
    }

    public static void changeSelected(int changeDirection) throws Exception {
        if ((changeDirection == 1 || changeDirection == -1)){
            int newSelection = selectedMenuID+changeDirection;
            if (newSelection==-1) selectedMenuID = maxSelectableElements-1;
            else if (newSelection==maxSelectableElements) selectedMenuID = 0;
            else selectedMenuID = newSelection;
        }
        else throw new Exception(String.format("Invalid changeDirection presented. Was: %d. Should be 1 or -1.", changeDirection));
    }

    public static void confirm(){
        wasConfirmed=true;
    }

    protected abstract void action();

    // ////
    @Override
    public void mouseHover(Vector2d mousePos){
        float w = Window.MouseHoverRadius;
        Vector3f pos = this.getPosition3D().mul(Game.MULT_DIM);
        float xHigh = pos.x + w;
        float xLow = pos.x - w;
        float yHigh = pos.y + w;
        float yLow = pos.y - w;

        if((mousePos.x > xLow && mousePos.x < xHigh)
                && (mousePos.y > yLow && mousePos.y < yHigh)){
            selectedMenuID = this.menuID;
        }
        System.out.println(mousePos + " || " + xLow + ", " + yLow + " // " + xHigh + ", " + yHigh);
    }

    // ////

}
