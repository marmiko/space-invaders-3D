package tp.p1.logic;

import java.util.*;

import org.joml.Vector3f;
import tp.p1.graphic_interface_3D.SceneLight;
import tp.p1.graphic_interface_3D.Window;
import tp.p1.graphic_interface_3D.Camera;
import tp.p1.graphic_interface_3D.Renderer;
import tp.p1.exceptions.*;
import tp.p1.graphic_interface_3D.lights.PointLight;
import tp.p1.graphic_interface_3D.lights.SpotLight;
import tp.p1.logic.board.Board;
import tp.p1.logic.board.BoardInitializer;
import tp.p1.logic.elements.*;
import tp.p1.util.Position;

import static org.lwjgl.glfw.GLFW.*;

public class Game {

public static final int MULT_DIM = 10;
public static final int DIM_X = 8*MULT_DIM;
public static final int DIM_Y = 10*MULT_DIM;
public static final double VERSION = 1.00;

private int currentCycle; 
private Random rand; 
private Level level;

Board board;

private UCMShip player;

private static final int UCMHitLightLength = 20;
private static final Vector3f UCMHitLightColor = new Vector3f(1.0f, 0.0f, 0.0f);

private int UCMHitLightCounter=UCMHitLightLength;
private boolean UCMHitLightOn=false;

private boolean doExit; 
private BoardInitializer initializer;
private boolean paused=false;
// main menu

private boolean menuState=true;

// graphical variables, etc.

private Window window;

private final Renderer renderer;
private final Camera camera;

private SceneLight sceneLight;

private static final float AmbientStrength = 0.2f;
private static final float MenuAmbientStrength = 0.75f;
private static final Vector3f[] PointLightsColors = {new Vector3f(0.678f,0.467f,0.906f),new Vector3f(1.0f,1.0f,1.0f),new Vector3f(0.678f,0.467f,0.906f),new Vector3f(0.471f,0.827f,0.98f)};
private static final Vector3f[] PointLightsPos = {
		new Vector3f(0.0f,25.0f, GameElement.INITIAL_Z),
		new Vector3f(GameElement.transformY3D(Game.DIM_Y+3), GameElement.transformX3D(-Game.MULT_DIM),GameElement.INITIAL_Z+5.0f), //right light
		new Vector3f(GameElement.transformY3D(0),GameElement.transformX3D(-Game.MULT_DIM),GameElement.INITIAL_Z-5), //back light
		new Vector3f(GameElement.transformY3D(-3), GameElement.transformX3D(-Game.MULT_DIM),GameElement.INITIAL_Z+2.0f)}; //left light
private static final float[] PointLightsStrengths = {0.0f,0.4f,1.0f,0.4f};


	private static final float SpotLightStrength = 5.0f;

// //////

public Game (Level gameLevel, Random random) {
	rand = random; 
	level = gameLevel; 
	initializer = new BoardInitializer();
	// graphical inits

	renderer = new Renderer();
	camera = new Camera();

	// //////
	}

	public Game (Random random) {
		rand = random;
		initializer = new BoardInitializer();
		// graphical inits

		renderer = new Renderer();
		camera = new Camera();

		// //////
	}

	private void initRendererAndLights() throws Exception {
		renderer.init();

		sceneLight = new SceneLight();

		int i;
		float ambientStr = AmbientStrength;

		Vector3f[] pointLightsPos = PointLightsPos;
		Vector3f[] colors = PointLightsColors;


		float[] strengths = PointLightsStrengths;
		for(i=0;i<SceneLight.NUM_POINT_LIGHTS;i++){
			sceneLight.addPointLight(new PointLight(colors[i], pointLightsPos[i], strengths[i]));
		}

		sceneLight.setAmbientStrength(ambientStr);
	}

private void initUfoSpotlight(){
	if(board!=null) {
		Vector3f ufoPosV = board.getUfo3DPos();
		if(ufoPosV!=null){

			float spotLightStrength = SpotLightStrength;
			Vector3f spotLightPos = ufoPosV;
			Vector3f spotLightDir = new Vector3f(0,1,0);
			Vector3f spotLightColor = new Vector3f(-0.1f,-0.1f,-0.1f);
			sceneLight.addSpotLight(new SpotLight(spotLightColor, spotLightPos, spotLightStrength, spotLightDir, 12.5f));
		}
	}
}

private void initGameElements() {
	currentCycle = 0;
	board = initializer.initialize (this, level);
	try{
		player = new UCMShip(this, new Position(DIM_X-1, DIM_Y/2));
	} catch (Exception e){
		System.err.println("UCM Ship initialization failed:");
		System.err.println(e.getMessage());
		this.exit();
	}
	board.add(player);
}

public void initGame(Window window) throws Exception {
	initRendererAndLights();
	this.setMenuState(true);
	initMainMenu(window);
	this.window=window;
}

public void initGame(Level level) {
	this.level= level;
	this.setMenuState(false);
	this.setGameAmbientLight();
	initGameElements();
	initUfoSpotlight();
	window.gameClearColor();
}

public void initGameAgain() throws Exception {
	AlienShip.resetAliens();
	LifeHeart.resetLifeHearts();
	this.initGame(window);
}

public void initMainMenu(Window window){
	window.menuClearColor();
	this.setMenuAmbientLight();
	board = initializer.initializeMainMenu(this);
}

private void initEndMenu(){
	window.menuClearColor();
	this.setMenuAmbientLight();
	this.setMenuState(true);
	board = initializer.initializeEndMenu(this);
}

private void setMenuAmbientLight(){
	sceneLight.setAmbientStrength(MenuAmbientStrength);
}

private void setGameAmbientLight(){
	sceneLight.setAmbientStrength(AmbientStrength);
}

public void UCMGotHitLight(){
	this.UCMHitLightCounter = UCMHitLightLength;
	this.UCMHitLightOn = true;
	sceneLight.changePointLightCol(1, UCMHitLightColor);
	sceneLight.changePointLightStr(1, 1.0f);
}

public Random getRandom() {
	return rand; 
	}

public Level getLevel() { 
	return level; 
	}

public void addObject(GameElement object) {
	board.add(object); 
	} 

public void addObjectSwap(GameElement object, Position pos) {
	board.addSwap(object, pos);
}

public String positionToString(Position pos) { 
	return board.toString(pos);
	}

public boolean isFinished() { return (playerWins() || aliensWin()) && !menuState; }

public boolean isExited() { return doExit;}

public boolean aliensWin() {
	return !player.isAlive() || AlienShip.haveLanded();
}

public boolean playerWins () {
	return AlienShip.allAliensDead(); 
	}

public void update() {
	board.computerAction();
	board.update();
	currentCycle += 1;

	if(this.isFinished()) this.initEndMenu();
}


private void setMenuState(boolean ifMenuState){
	this.menuState = ifMenuState;
}

public boolean isOnBoard(Position pos) { 
	return (pos.xInRange(0, DIM_X-1) && pos.yInRange(0, DIM_Y-1));
	}

public void exit() {
	menuState = false;
	doExit = true;
}

public void move(int numCells) throws OffWorldException {
		player.moveCom(numCells);
		
}

public void shootMissile() throws MissileFiredException {
	player.fireMissile();
	}

public void shootSuperMissile() throws MissileFiredException, NoSuperMissileException {
	
	player.fireSuperMissile();
		
}

public void buySuperMissile() throws NoEnoughPointsException {
	player.buySuperMissile();
}

public void shockWave() throws NoShockwaveException {
	player.useShockwave();
}

public void receivePoints(int points) {
	player.receivePoints(points);
}

public int getPoints(){
	return player.getPlayerPoints();
}

public boolean playerHasShockWave(){
	return player.checkShockwave();
}

public void enableShockWave() {
	player.enableShockwave();
}

public void enableMissile() {
	player.enableMissile();
}

public String toString(){
	return "space invaders";
}

// graphical methods

public void render(Window window){
	if(!this.isMenuState()){
		if(this.UCMHitLightOn){
			if(this.UCMHitLightCounter == 0){
				sceneLight.changePointLightCol(1, PointLightsColors[1]);
				sceneLight.changePointLightStr(1, PointLightsStrengths[1]);
			}
			else{
				this.UCMHitLightCounter--;
			}
		}
		if(Ufo.Appears){
			sceneLight.setSpotLightStrength(SpotLightStrength);
			sceneLight.updateSpotLightPos(board.getUfo3DPos());
		}
		else{
			sceneLight.disableSpotLight();
		}
	}
	renderer.render(window, camera, board.getAllGameElements(), sceneLight);
}

public void input(Window window){
	if(menuState)menuInput(window);
	else gameInput(window);
}

public boolean isMenuState(){
	return menuState;
}

private void togglePause(){
	paused=!paused;
}

public boolean isPaused(){
	return paused;
}

private void menuInput(Window window){
	this.board.checkHover(this.window.getMousePos());
	if (window.isKeyPressed(GLFW_KEY_A) || window.isKeyPressed(GLFW_KEY_LEFT)) {
		try{
			SelectableMenuElement.changeSelected(-1);
		} catch (Exception ie){}
	} else if (window.isKeyPressed(GLFW_KEY_D) || window.isKeyPressed(GLFW_KEY_RIGHT)) {
		try{
			SelectableMenuElement.changeSelected(1);
		} catch (Exception ie){}
	}
	if (window.isKeyPressed(GLFW_KEY_ENTER)) {
		try {
			SelectableMenuElement.confirm();
		} catch (Exception ie) {
		}
	}
}

private void gameInput(Window window){
	if(!isPaused()){
		if (window.isKeyPressed(GLFW_KEY_W) || window.isKeyPressed(GLFW_KEY_UP)) {
			try{
				shootMissile();
			} catch (Exception ie){}
		} else if (window.isKeyPressed(GLFW_KEY_S) || window.isKeyPressed(GLFW_KEY_DOWN)) {
			try{
				shockWave();
			} catch (Exception ie){}
		} else if (window.isKeyPressed(GLFW_KEY_SPACE)) {
			try {
				shootSuperMissile();
			} catch (Exception ie) {
			}
		}
		if (window.isKeyPressed(GLFW_KEY_A) || window.isKeyPressed(GLFW_KEY_LEFT)) {
			try{
				move(-1);
			} catch (Exception ie){}
		} else if (window.isKeyPressed(GLFW_KEY_D) || window.isKeyPressed(GLFW_KEY_RIGHT)) {
			try{
				move(1);
			} catch (Exception ie){}
		}
		if (window.isKeyPressed(GLFW_KEY_B)) {
			try {
				buySuperMissile();
			} catch (Exception ie) {}
		}
		if (window.isKeyPressed(GLFW_KEY_K)) {
			try {
				enableShockWave();
			} catch (Exception ie) {}
		}
		if (window.isKeyPressed(GLFW_KEY_U)) {
			try {
				killUCM();
			} catch (Exception ie) {}
		}
	}
	if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
		try {
			togglePause();
		} catch (Exception ie) {}
	}
	if (window.isKeyPressed(GLFW_KEY_Q)) {
		try {
			this.UCMGotHitLight();
		} catch (Exception ie) {}
	}
}

public void cleanUp(){
	renderer.cleanUp();
}

// //////

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
// testing codes
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

public boolean killUCM() {
	try{
		Destroyer temp = new Destroyer(this, new Position(-2, -2));
	Position tempPos = new Position(player.getPosition().getX()-1, player.getPosition().getY());
	for(int i=player.getShield(); i>0; i--) {
		board.add(new Bomb(this, tempPos, temp));
	}
	temp.receiveMissileAttack(temp.getShield());
	return true;
	} catch (Exception ignore) {}
	return false;
}
}
