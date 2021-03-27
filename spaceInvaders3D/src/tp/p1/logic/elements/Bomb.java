package tp.p1.logic.elements;

import org.joml.Vector3f;
import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.util.Position;


public class Bomb extends Weapon {
	private final static int Power = 1;
	private final static int Shield = 1;
	private Destroyer destroyer;
	private int ownerRef;

	// graphical sources etc.
	// TODO -- uzupełnić źródła, reflectance, color, itd.
	private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/BOMB.obj";
	private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/BOMB_TEXTURE.png";
	private final static String SpecularMap = "src/tp/p1/graphic_interface_3D/resources/textures/BOMB_SPECULAR.png";
	private final static float SCALE = 0.0015f;
	private final static float ROTATION_SPEED = 20.0f;

	private static Mesh MESH;
	private static boolean meshCreated=false;

	public Bomb(Game game, Position pos, Destroyer destroyer) throws Exception {
		super(game, pos, Shield, Power);
		this.destroyer = destroyer;
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

	public boolean performAttack(GameElement other) {
		if(isAlive()) {
			if(other != null && other.isAlive() && other.getPosition().equals(position)) {
				if(other.receiveBombAttack(power)) {
					shield = 0;
				}
			}
		}
		return true;
	}

	
	public boolean receiveMissileAttack(int damage) {
		damage(damage);
		return true;
	}
	
	public boolean receiveShockWaveAttack(int damage) {
		damage(damage);
		return true;
	}
	
	public String toString() {
		return "!";
	}
	
	public void move() {
		position = new Position(position.getX() + 1, position.getY());
		if(isOut()) {
			shield = 0;
		}
	}
	
	

	@Override
	public void computerAction() {
		this.rotation3D = new Vector3f(this.rotation3D.x+ROTATION_SPEED, 0.0f, 0.0f);
	}

	@Override
	public void onDelete() {
		destroyer.enableFire();
	}
	
}
