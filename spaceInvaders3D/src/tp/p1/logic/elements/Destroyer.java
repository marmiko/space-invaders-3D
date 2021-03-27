package tp.p1.logic.elements;

import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.logic.interfaces.IExecuteRandomActions;
import tp.p1.util.Position;

public class Destroyer extends AlienShip{

	private boolean canShootBomb;
	private boolean toFire = false;
	private static final int Shield = 2;
	private static final int Points = 10;

	// graphical sources etc.
	// TODO -- uzupełnić źródła, reflectance, color, itd.
	private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/DESTROYER.obj";
	private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/DESTROYER_TEXTURE.png";
	private final static String SpecularMap = "src/tp/p1/graphic_interface_3D/resources/textures/DESTROYER_SPECULAR.png";
	private final static float SCALE = 0.003f;

	private static Mesh MESH;
	private static boolean meshCreated=false;

	public Destroyer(Game Game, Position InitialPos) throws Exception {
		super(Game,InitialPos,Shield, Points);
		canShootBomb = true;
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

	public void move() {
		super.move();
		if(toFire) {
			fire();
		}
	}

	public void enableFire() {
		canShootBomb = true;
	}

	private void fire() {
		Bomb bomb;
		try {
			bomb = new Bomb(game, position, this);
			game.addObject(bomb);
			toFire = false;
			canShootBomb = false;
		} catch (Exception e) {
			System.err.println("Coould not drop bomb - unknown error");
		}
	}

	public String toString() {
		return String.format("!<%d>!", shield);
	}

	@Override
	public void computerAction() {
		super.computerAction();
		if(canShootBomb) { 
			toFire = IExecuteRandomActions.canGenerateBomb(game);
		}
	}

}
