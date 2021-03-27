package tp.p1.logic.elements;

import tp.p1.logic.Game;
import tp.p1.util.Position;

public abstract class Ship extends GameElement {
	
	public Ship(Game game, Position initialPos, int shield){
		super(game, initialPos, shield);
	}

	public Ship(Game game, Position pos, int shield, String objectSource, String texture) throws Exception {
		super(game, pos, shield, objectSource, texture);
	}

	public Ship(Game game, Position pos, int shield, String objectSource, String texture, String specularMap) throws Exception {
		super(game, pos, shield, objectSource, texture, specularMap);
	}
	
	public void move() {}
	
	public boolean receiveExplosionAttack(int damage) {
		damage(damage);
		return true;
	}

}
