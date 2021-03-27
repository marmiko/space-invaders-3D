package tp.p1.logic.elements;

import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.util.Position;

public class CarrierShip extends AlienShip {

	protected static final int Shield = 3;
	private static final int Points = 5;

	// graphical sources etc.
	// TODO -- uzupełnić źródła, reflectance, color, itd.
	private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/CARRIER.obj";
	private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/CARRIER_TEXTURE.png";
	private final static String SpecularMap = "src/tp/p1/graphic_interface_3D/resources/textures/CARRIER_SPECULAR.png";
	private final static float SCALE = 0.003f;

	private static Mesh MESH;
	private static boolean meshCreated = false;

	public CarrierShip(Game game, Position InitialPos) throws Exception {
		super(game, InitialPos, Shield, Points);
		this.scale = SCALE;

		if (!meshCreated) {
			MESH = OBJLoader.loadMesh(ObjectSource);
			MESH.setTexture(new Texture(TextureSource, SpecularMap));
			meshCreated = true;
		}
	}

	public String toString() {
		return String.format("-<%d>-", shield);
	}


	@Override
	public Mesh getMesh() {
		final Mesh mesh = MESH;
		return mesh;
	}

}
