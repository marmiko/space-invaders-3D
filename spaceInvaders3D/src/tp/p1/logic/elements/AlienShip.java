package tp.p1.logic.elements;

import org.joml.Vector3f;
import tp.p1.logic.Game;
import tp.p1.util.Direction;
import tp.p1.util.Position;

public abstract class AlienShip extends EnemyShip {
	
	private static final int InitMovementDirection = Direction.LEFT.getDirectionInt();
	protected static int aliens = 0;
	private static int toMoveDown = 0;
	private static boolean moveDownNextMove = false;
	protected int cyclesNextAlienMove = 0;
	private int movementPeriod;
	private static boolean landed = false;

	private boolean shockwaveRotation = false;
	private int rotationCounter = 6;
	private int rotationDirection = 1;

	private static final int RotationCount = 12;
	private static final float RotationStrength = 10.0f;

	public AlienShip(Game game, Position initialPos, int shield, int points){
		super(game, initialPos, shield, InitMovementDirection, points);
		aliens += 1;
		movementPeriod = game.getLevel().getSpeed();
		cyclesNextAlienMove = 0;
	}

	public AlienShip(Game game, Position pos, int shield, String objectSource, String texture, int points) throws Exception {
		super(game, pos, shield, objectSource, texture, InitMovementDirection, points);
		aliens += 1;
		movementPeriod = game.getLevel().getSpeed();
		cyclesNextAlienMove = 0;
	}

	public AlienShip(Game game, Position pos, int shield, String objectSource, String texture, String specularMap, int points) throws Exception {
		super(game, pos, shield, objectSource, texture, specularMap, InitMovementDirection, points);
		aliens += 1;
		movementPeriod = game.getLevel().getSpeed();
		cyclesNextAlienMove = 0;
	}

	public static void resetAliens(){
		aliens = 0;
		toMoveDown = 0;
		landed = false;
	}
	
	public static boolean allAliensDead() {
		return (aliens == 0);
	}
	
	public static boolean haveLanded() {
		return landed;
	}
	
	public static int countAliens() {
		return aliens;
	}
	
	protected static void removeToMoveDown() {
		toMoveDown -= 1;
	}
	
	protected boolean goDownCurrentCycle() {
		return toMoveDown > 0;
	}
	
	public void move() {
		if (cyclesNextAlienMove == 0) {
			if (goDownCurrentCycle()) {
				this.moveDown();
				if(isAlive() && position.getX() >= Game.DIM_X-1*Game.MULT_DIM/2) {
					landed = true;
				}
			}
			else {
				super.move();
				
				int y = position.getY();
				if(!moveDownNextMove && toMoveDown == 0 && (y == 0 || y == Game.DIM_Y-1)) {
					moveDownNextMove = true;
				}
			}
			cyclesNextAlienMove = movementPeriod;
		}
		cyclesNextAlienMove -= 1;
	}
	
	public static void setIfMoveDownNextMove() {
		if(moveDownNextMove) {
			toMoveDown = aliens;
			moveDownNextMove = false;
		}
	}
	
	private void moveDown() {
			int x = position.getX() + Game.MULT_DIM/2 ;
			int y = position.getY();
			
			position = new Position(x, y);
			toMoveDown -= 1;
			changeDirection();
	}
	
	private void changeDirection() {
		movementDirection = -1 * movementDirection;
	}

	protected void aliensCountDown() {
		aliens -= 1;
	}

	@Override
	public boolean receiveShockWaveAttack(int damage) {
		damage(damage);
		shockwaveRotation=true;
		return true;
	}

	@Override
	public void computerAction(){
		super.computerAction();
		if(shockwaveRotation){
			if(rotationCounter != 0){
				this.rotation3D = new Vector3f(this.rotation3D.x, this.rotation3D.y, this.rotation3D.z+this.rotationDirection*RotationStrength);
				this.rotationDirection = -this.rotationDirection;
				rotationCounter--;
			}
			else{
				this.shockwaveRotation = false;
				this.rotationCounter = RotationCount;
			}
		}
	}
	
	public void onDelete() {
		super.onDelete();
		aliensCountDown();
		if(toMoveDown > 0) removeToMoveDown();
	}
	
}