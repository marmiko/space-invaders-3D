package tp.p1.logic.elements;

import org.joml.Vector3f;
import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.util.Position;

public class Missile extends Weapon {
	protected final static int Power = 1;
	private final static int Shield = 1;

	public static final String MainMessFire = "Cannot fire missile: ";

	// graphical sources etc.
	// TODO -- uzupełnić źródła, reflectance, color, itd.
	private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/MISSILE.obj";
	private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/MISSILE_TEXTURE.png";
	private final static String SpecularMap = "src/tp/p1/graphic_interface_3D/resources/textures/MISSILE_SPECULAR.jpg";
	private final static float SCALE = 0.0012f;
	private final static float SCALE_SUPER = 0.0014f;
	private final static float ROTATION_SPEED = 20.0f;

	private static Mesh MESH;
	private static boolean meshCreated=false;
	


	public Missile(Game game, Position pos) throws Exception {
		super(game, pos, Shield, Power);
		if(!meshCreated){
			MESH = OBJLoader.loadMesh(ObjectSource);
			MESH.setTexture(new Texture(TextureSource, SpecularMap));
			meshCreated=true;
		}
		this.scale = SCALE;
	}

	public Missile(Game game, Position pos, int power) throws Exception {
		super(game, pos, Shield, power);
		this.scale = SCALE_SUPER;
	}

	@Override
	public Mesh getMesh(){
		final Mesh mesh = MESH;
		return mesh;
	}
	
	public void move() {
		position = new Position((position.getX() - 1), position.getY());
		if(isOut()) {
			shield = 0;
		}
	}
	
	public boolean performAttack(GameElement other) {
		if(isAlive()) {
		if(other != null && other.isAlive() && other.getPosition().equals(position)) {
			if(other.receiveMissileAttack(power)) {
			shield = 0;
			}
		}
		}
		return true;
	}
	
	public boolean receiveBombAttack(int damage) {
		damage(damage);
		return true;
	}
		
	public String toString() {
		return "^";
	}

	@Override
	public void computerAction() {
		this.rotation3D = new Vector3f(0.0f, this.rotation3D.y+ROTATION_SPEED, 0.0f);
	}

	@Override
	public void onDelete() {
		game.enableMissile();
	}

}
