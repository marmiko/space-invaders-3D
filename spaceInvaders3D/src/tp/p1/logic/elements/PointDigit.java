package tp.p1.logic.elements;

import org.joml.Vector3f;
import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.logic.Game;
import tp.p1.util.Position;

import static java.lang.Math.pow;

public class PointDigit extends GameElement {

    public static final int COUNT = 4;

    private final static int Shield = 10000;

    protected int digitID;

    // graphical sources etc.
    // TODO -- uzupełnić źródła, reflectance, color, itd.
    private final static float Z_VALUE = GameElement.INITIAL_Z+1;
    private final static float SCALE = 0.001f;
    private final static Vector3f ROTATION = new Vector3f(90.0f, 0.0f, 0.0f);

    public PointDigit(Game game, int digitID) {
        super(game, getIDPosition(digitID), Shield);
        this.digitID = digitID;
        this.scale = SCALE;
        this.rotation3D = ROTATION;
    }

    private static Position getIDPosition(int i){
        return new Position(3,(i-1)*-5);
    }

    public void damage(int power){}

    @Override
    public boolean canBeRemoved(){return false;}

    @Override
    public Mesh getMesh(){
        return DigitMeshes.getMesh((game.getPoints()/(int)pow(10, this.digitID))%10);
    }

    @Override
    public Vector3f getPosition3D(){
        Vector3f basic3DPos = super.getPosition3D();
        return new Vector3f(basic3DPos.x, basic3DPos.y, Z_VALUE);
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
