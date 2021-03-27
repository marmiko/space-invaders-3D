package tp.p1.logic.elements;

import tp.p1.logic.Game;
import tp.p1.util.Position;

public abstract class EnemyShip extends Ship {
	
	protected int movementDirection;
	protected int points;

	private int scaleChangeCounter = 0;
	private static final int SCALE_CHANGE_INTERVAL = 3;
	private static final float SCALE_DIVIDER = 2.0f;
	private float normalScale;

	public EnemyShip(Game game, Position initialPos, int shield, int movementDirection, int points){
		super(game, initialPos, shield);
		this.movementDirection = movementDirection;
		this.points = points;
	}

	public EnemyShip(Game game, Position pos, int shield, String objectSource, String texture, int movementDirection, int points) throws Exception {
		super(game, pos, shield, objectSource, texture);
		this.points = points;
		this.movementDirection = movementDirection;
	}

	public EnemyShip(Game game, Position pos, int shield, String objectSource, String texture, String specularMap, int movementDirection, int points) throws Exception {
		super(game, pos, shield, objectSource, texture, specularMap);
		this.points = points;
		this.movementDirection = movementDirection;
	}
	
	public int getPoints() {
		return points;
	}
	
	public boolean receiveMissileAttack(int damage) {
		damage(damage);
		return true;
	}

	@Override
	public void damage(int power) {
		this.normalScale = this.scale;
		this.scale = this.scale/SCALE_DIVIDER;
		this.scaleChangeCounter = SCALE_CHANGE_INTERVAL;
		super.damage(power);
	}

	public void computerAction(){
		if(this.scaleChangeCounter>0) {
			this.scaleChangeCounter--;
			if(this.scaleChangeCounter==0) this.scale = this.normalScale;
		}
	}
	
	public void move() {
		int x = position.getX();
		int y = position.getY() + movementDirection;
		
		
		Position pos = new Position(x, y);
		this.position = pos;
	}
	
	
	public void onDelete() {
		game.receivePoints(points);
	}
}
