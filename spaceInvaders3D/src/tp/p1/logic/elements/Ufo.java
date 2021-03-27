package tp.p1.logic.elements;

import org.joml.Vector3f;
import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.logic.interfaces.IExecuteRandomActions;
import tp.p1.util.Direction;
import tp.p1.util.Position;

public class Ufo extends EnemyShip{

	private final static int Shield = 1;
	private final static int Points = 25;
	public final static Position InitialPos = new Position(Game.MULT_DIM,Game.DIM_Y);
	private static final int MovementDirection = Direction.LEFT.getDirectionInt();
	public static boolean Appears=false;

	// graphical sources etc.
	private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/UFO.obj";
	private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/UFO_TEXTURE.png";
	private final static String SpecularMap = "src/tp/p1/graphic_interface_3D/resources/textures/UFO_SPECULAR.png";
    private final static float SCALE = 0.003f;
    private final static float ROTATION_SPEED = 10.0f;

	private static Mesh MESH;
	private static boolean meshCreated=false;

    public Ufo(Game game) throws Exception {
		super(game, InitialPos, Shield, MovementDirection, Points);
		this.scale = SCALE;

		if(!meshCreated){
			MESH = OBJLoader.loadMesh(ObjectSource);
			MESH.setTexture(new Texture(TextureSource, SpecularMap));
			meshCreated=true;
		}
	}

	@Override
	public Mesh getMesh(){
		if(Appears) {
			final Mesh mesh = MESH;
			return mesh;
		}
		else{
			return null;
		}
	}

	public void damage(int power) {
		if(Appears) super.damage(power);
	}


	public void onDelete() {
		game.receivePoints(Points);
		game.enableShockWave();
		try {
            game.addObjectSwap(new Ufo(game), position);
        } catch (Exception e) {
		    System.err.println("Ufo couldn't be load correctly from saved state:");
		    System.err.println(e.getMessage());
        }

	}

	public String toString() {
		return "<(+)>";
	}

	public void move() {
		if(Appears) {
			super.move();
			if(isOut()) {
				Appears = false;
				position = InitialPos;
			}
		}
	}

	@Override
	public boolean isUfo(){
		return true;
	}

	@Override
	public void computerAction() {
		super.computerAction();
		if(!Appears &&  IExecuteRandomActions.canGenerateUfo(game)) {
			Appears = true;
		}
		this.rotation3D = new Vector3f(0.0f, this.rotation3D.y+ROTATION_SPEED, 0.0f);
	}

}
