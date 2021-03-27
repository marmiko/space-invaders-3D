package tp.p1.logic.elements;

import tp.p1.exceptions.*;
import tp.p1.graphic_interface_3D.Mesh;
import tp.p1.graphic_interface_3D.OBJLoader;
import tp.p1.graphic_interface_3D.Texture;
import tp.p1.logic.Game;
import tp.p1.util.Position;

public class UCMShip extends Ship{

	private boolean shockwave = false;
	private boolean canFire = true;
	private int playerPoints;
	private boolean fakeAlive = false;
	public final static int Shield = 3;
	private int superMissiles;
	

	public static final String MissileExistMess = "one type of missiles already exists on board";
	public static final String NoSuperMissileMess = "player does not have super missile";
	public static final String MoveMess = "Cannot make move: player is to close to the boarder";
	public static final String NoShockMess = "no shockwave available";

	// graphical sources etc.
	// TODO -- uzupełnić źródła, reflectance, color, itd.
	private final static String ObjectSource = "/tp/p1/graphic_interface_3D/resources/models/UCM.obj";
	private final static String TextureSource = "src/tp/p1/graphic_interface_3D/resources/textures/UCM_TEXTURE.png";
	private final static String SpecularMap = "src/tp/p1/graphic_interface_3D/resources/textures/UCM_SPECULAR.png";
	private final static String NormalMap = "src/tp/p1/graphic_interface_3D/resources/textures/UCM_NORMAL.png";
	private final static float SCALE = 0.003f;

	private static Mesh MESH;
	private static boolean meshCreated=false;
	

	public UCMShip(Game game, Position initialPos) throws Exception {
		super(game, initialPos, Shield);
		superMissiles = 0;
		playerPoints = 0;
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

	public int getPlayerPoints() {
		return this.playerPoints;
	}

	public int leftSuperMissiles() {
		return superMissiles;
	}

	public void buySuperMissile() throws NoEnoughPointsException {
		int price = SuperMissile.Price;
		if(playerPoints >= price) {
			playerPoints -= price;
			superMissiles += 1;
			}
		else {
		throw new NoEnoughPointsException(String.format("Cannot buy super missile: player does not have at least %d points", price));
	}
	}
	
	public void fireSuperMissile() throws MissileFiredException, NoSuperMissileException {
		if(!canFire) {
			throw new MissileFiredException(SuperMissile.MainMessFire + MissileExistMess);
		}
		
		if(superMissiles<=0) {
			throw new NoSuperMissileException(SuperMissile.MainMessFire + NoSuperMissileMess);
		}
		
		superMissiles -= 1;
		SuperMissile superMiss;
		try {
			superMiss = new SuperMissile(game, position);
			game.addObject(superMiss);
		} catch (Exception e) {
			System.err.println("Could not fire SuperMissile - unknown error");
		}
	}
	
	public void fireMissile() throws MissileFiredException {
		if(!canFire) {
		throw new MissileFiredException(Missile.MainMessFire + MissileExistMess);
	}
		Missile miss;
		try {
			miss = new Missile(game, position);
			game.addObject(miss);
			disableMissile();
		} catch (Exception e) {
			System.err.println("Could not fire Missile - unknown error");
		}
	}

	public void disableMissile() {
		canFire = false;
	}
	
	public void enableMissile() {
		canFire = true;
	}
	
	public void receivePoints(int points) {
		playerPoints += points;
	}
	
	public void move() {}
	

	public void enableShockwave() {
		shockwave = true;
	}
	
	private void disableShockwave() {
		shockwave = false;
	}
	
	public boolean checkShockwave() {
		return shockwave;
	}
	
	public void useShockwave() throws NoShockwaveException {
		if(!shockwave) {
			throw new NoShockwaveException(Shockwave.MainMessFire + NoShockMess);
		}

		game.addObject(new Shockwave(game));
		disableShockwave();
	}
	
	
	
	public void moveCom(int dist) throws OffWorldException {
			if (position.getY()+ dist >= 0 && position.getY()+dist < Game.DIM_Y) {
				position = new Position(position.getX() ,(position.getY() + dist));
			}
	else {
		throw new OffWorldException(MoveMess);
	}
}
	
	public boolean receiveBombAttack(int damage) {
		damage(damage);
		return true;
	}

	@Override
	protected void damage(int damage){
		super.damage(damage);
		LifeHeart.removeHeart();
		this.game.UCMGotHitLight();
	}
	
	public String toString() {
		if (shield > 0) return "/-^-\\";
		else return "_+.+_";
	}



	@Override
	public void computerAction() {}


	@Override
	public void onDelete() {
		game.addObjectSwap(this, position);
		fakeAlive = true;
	}
	
	public boolean isAlive() {
		if(!fakeAlive) return super.isAlive();
		else {
			fakeAlive = false;
			return true;
		}
	}
}
