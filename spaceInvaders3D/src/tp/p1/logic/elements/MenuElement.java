package tp.p1.logic.elements;

import org.joml.Vector3f;
import tp.p1.logic.Game;
import tp.p1.util.Position;

public class MenuElement extends GameElement {

    public static final int UP=-15;

    private final static int Shield = 10000;

    protected int menuID;

    // graphical sources etc.
    // TODO -- uzupełnić źródła, reflectance, color, itd.
    private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/rectangle.obj";
    protected final static float BiggerScale = 0.55f;
    protected final static float NormalScale = 0.45f;


    public MenuElement(Game game, Position initialPos, int menuID, String textureFileName) throws Exception {
        super(game, initialPos, Shield, ObjectSource, String.format("src/tp/p1/graphic_interface_3D/resources/textures/%s", textureFileName));
        this.menuID = menuID;
        this.scale = NormalScale;
    }

    public MenuElement(Game game, Position initialPos, int menuID, String textureFileName, float scale) throws Exception {
        super(game, initialPos, Shield, ObjectSource, String.format("src/tp/p1/graphic_interface_3D/resources/textures/%s", textureFileName));
        this.menuID = menuID;
        this.scale = scale;
    }

    @Override
    public Vector3f getPosition3D(){
        position3D = new Vector3f(0.105f*(position.getY()-Game.DIM_Y/1.9f)+0.5f, -0.075f*(position.getX()+Game.DIM_X/2.8f)+4.0f, INITIAL_Z);
        return position3D;
    }


    @Override
    public void computerAction() {}


    @Override
    public void onDelete() {}

    public void move(){}

    public String toString() {
        return "";
    }
}
