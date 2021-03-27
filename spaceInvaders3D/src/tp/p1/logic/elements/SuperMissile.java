package tp.p1.logic.elements;

import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.util.Position;

public class SuperMissile extends Missile {
	private final static int Power = 2;
	public final static int Price = 20;
	
	public static final String MainMessFire = "Cannot fire super missile: ";

	// graphical sources etc.
	// TODO -- uzupełnić źródła, reflectance, color, itd.
	private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/MISSILE.obj";
	private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/MISSILE_TEXTURE.png";
	private final static String SpecularMap = "src/tp/p1/graphic_interface_3D/resources/textures/MISSILE_SPECULAR.jpg";

	private static Mesh MESH;
	private static boolean meshCreated=false;

	public SuperMissile(Game game, Position pos) throws Exception {
		super(game, pos, Power);
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

	public String toString() {
		return "/\\";
	}

}
