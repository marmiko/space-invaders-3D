package tp.p1.logic.elements;

import org.joml.Vector3f;
import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.util.Position;

public class ShockWaveIcon extends GameElement {
    private final static int Shield = 10000;

    private final static Position Position = new Position(Game.DIM_X-(LifeHeart.HeartCount+2)*Game.MULT_DIM, 0);

    // graphical sources etc.
    // TODO -- uzupełnić źródła, reflectance, color, itd.
    private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/SQUARE.obj";
    private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/SHOCKWAVE_ICON_TEXTURE.png";
    private final static float Z_VALUE = GameElement.INITIAL_Z+2;

    private static Mesh MESH;
    private static boolean meshCreated=false;

    public ShockWaveIcon(Game game) throws Exception {
        super(game, Position, Shield);
        if(!meshCreated){
            MESH = OBJLoader.loadMesh(ObjectSource);
            MESH.setTexture(new Texture(TextureSource));
            meshCreated=true;
        }
    }

    @Override
    public Mesh getMesh() {
        if (game.playerHasShockWave()){
            Mesh mesh = MESH;
            return mesh;
        }
        else{
            return null;
        }
    }

    @Override
    public Vector3f getPosition3D(){
        Vector3f basic3DPos = super.getPosition3D();
        return new Vector3f(basic3DPos.x, basic3DPos.y, Z_VALUE);
    }

    @Override
    public void onDelete() {}

    public void move(){}

    public String toString() {
        return "";
    }

    @Override
    public void computerAction(){}


}
