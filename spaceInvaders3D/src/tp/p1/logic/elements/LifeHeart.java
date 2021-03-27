package tp.p1.logic.elements;

import org.joml.Vector3f;
import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.util.Position;

public class LifeHeart extends GameElement {

    public static int HeartCount=UCMShip.Shield;

    private final static int Shield = 10000;

    protected int heartID;

    // graphical sources etc.
    // TODO -- uzupełnić źródła, reflectance, color, itd.
    private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/SQUARE.obj";
    private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/HEART_TEXTURE.png";
    private final static float Z_VALUE = GameElement.INITIAL_Z+2;

    private static float ShakeStrength = 2.0f;
    private static int ShakePeriod = 5;

    private int shakePeriod = ShakePeriod;

    private static Mesh MESH;
    private static boolean meshCreated=false;

    public LifeHeart(Game game, int heartID) throws Exception {
        super(game, getIDPosition(heartID), Shield);
        this.heartID = heartID;
        if(!meshCreated){
            MESH = OBJLoader.loadMesh(ObjectSource);
            MESH.setTexture(new Texture(TextureSource));
            meshCreated=true;
        }
    }

    private static Position getIDPosition(int i){
        return new Position(Game.DIM_X-(2+i)*Game.MULT_DIM, 0);
    }

    public static void resetLifeHearts(){
        HeartCount = UCMShip.Shield;
    }

    public static void removeHeart(){
        HeartCount--;
    }

    @Override
    public Mesh getMesh(){
        final Mesh mesh = MESH;
        return mesh;
    }

    @Override
    public Vector3f getPosition3D(){
        Vector3f basic3DPos = super.getPosition3D();
        return new Vector3f(basic3DPos.x, basic3DPos.y, Z_VALUE);
    }


    @Override
    public void computerAction() {
        if(this.heartID>HeartCount-1){
            this.shield = 0;
        }
        else{
            if(HeartCount==1){
                this.rotation3D = new Vector3f(0.0f, 0.0f, this.rotation3D.z+ShakeStrength);
                if(this.shakePeriod==0){
                    this.shakePeriod = ShakePeriod;
                    ShakeStrength = -1*ShakeStrength;
                }
                else{
                    this.shakePeriod--;
                }
            }
        }
    }


    @Override
    public void onDelete() {}

    public void move(){}

    public String toString() {
        return "";
    }

}
