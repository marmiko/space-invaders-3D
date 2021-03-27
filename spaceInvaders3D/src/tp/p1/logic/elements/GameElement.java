package tp.p1.logic.elements;




import org.joml.Vector2d;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.util.vector.Vector;
import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.logic.interfaces.IAttack;
import tp.p1.util.Position;

public abstract class GameElement implements IAttack{

	protected Position position;
	protected Game game;
	protected int shield;

	// variables used for 3D graphics
	protected Mesh mesh;
	protected Vector3f position3D;
	protected float scale;
	protected Vector3f rotation3D;
	private String objectSource;
	private Texture texture=null;
	private Vector4f color;

	protected boolean simpleScale = true;
	protected Vector3f complexScale = null;

	public static final float INITIAL_Z = -8.5f;
	private static final Vector3f INITIAL_ROTATION = new Vector3f(0.0f, 0.0f, 0.0f);
	protected static final float DEFAULT_SCALE = 0.2f;
	private static final Vector4f DEFAULT_COLOR = new Vector4f(0.5f, 0.9f, 0.7f, 1.0f);

	
	

	public GameElement(Game game, Position pos, int shield){
		this.game = game;
		this.position = pos;
		this.shield = shield;

		this.position3D = new Vector3f(transformY3D(position.getY()), transformX3D(position.getX()), INITIAL_Z);
		this.rotation3D = INITIAL_ROTATION;
		this.scale = DEFAULT_SCALE;
	}

	public GameElement(Game game, Position pos, int shield, String objectSource) throws Exception{
		this(game, pos, shield, objectSource, null, null, null);
	}

	public GameElement(Game game, Position pos, int shield, String objectSource, Vector4f color) throws Exception {
		this(game, pos, shield, objectSource, null, null, color);
	}

	public GameElement(Game game, Position pos, int shield, String objectSource, String texture) throws Exception {
		this(game, pos, shield, objectSource, texture, null, null);
	}

	public GameElement(Game game, Position pos, int shield, String objectSource, String texture, String specularMap) throws Exception {
		this(game, pos, shield, objectSource, texture, specularMap, null);
	}

	public GameElement(Game game, Position pos, int shield, String objectSource, String texture, String specularMap, Vector4f color) throws Exception {

		position = pos;
		this.game = game;
		this.shield = shield;
		// initialization of graphical variables
		this.position3D = new Vector3f(transformY3D(position.getY()), transformX3D(position.getX()), INITIAL_Z);
		this.rotation3D = INITIAL_ROTATION;
		this.scale = DEFAULT_SCALE;
		this.objectSource = objectSource;
		if(texture != null) {
			if(specularMap != null) this.texture = new Texture(texture, specularMap);
			else this.texture = new Texture(texture);
		}
		if(color != null) this.color = color;
		else this.color = DEFAULT_COLOR;
		generateMesh();
	}

	public static float transformY3D(float y){
		return 0.105f*(y-Game.DIM_Y/1.95f)+0.5f;
	}

	public static float transformX3D(float x){
		return -0.11f*(x+Game.DIM_X/2.8f)+7.5f;
	}


	private void generateMesh()throws Exception{
		this.mesh = OBJLoader.loadMesh(this.objectSource);
		this.mesh.setTexture(this.texture);
	}

	public Mesh getMesh() {
		final Mesh mesh = this.mesh;
		return mesh;
	}

	public Vector3f getRotation3D() {
		return rotation3D;
	}

	public Vector3f getPosition3D(){
		position3D = new Vector3f(transformY3D(position.getY()), transformX3D(position.getX()), INITIAL_Z);
		return position3D;
	}

	public boolean simpleScale(){
		return simpleScale;
	}

	public float getScale(){
		return scale;
	}

	public Vector3f getComplexScale(){
		return complexScale;
	}

	public Vector4f getColor() { return color; }
	// //////

	public boolean isUfo(){
		return false;
	}

	public Position getPosition() {
		Position pos = new Position(position.getX(), position.getY());
		return pos;
	}
	
	public int getShield() {
		return shield;
	}
	
	public boolean isAlive() {
		return shield > 0;
	}
	
	
	protected void damage(int power) {
		shield = (power >= shield ? 0 : shield- power);
	}

	
	public boolean isOut() {
		return !game.isOnBoard(position);
	}

	// ////
	public void mouseHover(Vector2d mousePos){}
	// ////

	public abstract void move();
	public abstract void computerAction();
	public abstract void onDelete();
	public abstract String toString();

}
