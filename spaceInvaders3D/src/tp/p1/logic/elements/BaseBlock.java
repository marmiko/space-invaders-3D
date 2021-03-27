package tp.p1.logic.elements;

import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.util.Position;

public class BaseBlock extends GameElement {

    protected static final int Shield = 100000;

    public static final Position FirstBlockPos = new Position(Game.DIM_X+3, -3*Game.MULT_DIM/4);

    // graphical sources etc
    // TODO -- uzupełnić źródła, reflectance, color, itd.
    private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/moonCube.obj";
    private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/moonTexture.jpg";
    private final static String SpecularMap = "src/tp/p1/graphic_interface_3D/resources/textures/moonSpecular.jpg";
    private final static float SCALE = 0.003f;

    private static Mesh MESH;
    private static boolean meshCreated=false;


    public BaseBlock(Game game, Position position) throws Exception {
        super(game, position, Shield);
        this.scale = SCALE;
        if(!meshCreated){
            MESH = OBJLoader.loadMesh(ObjectSource);
            MESH.setTexture(new Texture(TextureSource, SpecularMap));
            meshCreated=true;
        }
    }

    @Override
    public Mesh getMesh(){
        final Mesh mesh = MESH;
        return mesh;
    }

    @Override
    public void move(){}

    @Override
    public void computerAction(){}

    @Override
    public void onDelete(){this.shield = Shield;}

    @Override
    public String toString(){
       return "------- Base --- Block -------";
    }

}
