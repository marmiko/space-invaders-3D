package tp.p1.logic.elements;

import org.joml.Vector4f;
import tp.p1.logic.Game;
import tp.p1.util.Position;

public abstract class Weapon extends GameElement {
	
	protected int power;



	public Weapon(Game game, Position initialPos, int shield, int power){
		super(game, initialPos, shield);
		this.power = power;
	}

}
