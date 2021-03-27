package tp.p1.util;

import tp.p1.logic.Game;

public class Position {
	private int x;
	private int y;
	
	public Position(int X, int Y) {
		x = X;
		y = Y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equals(Position pos) {
		if((pos.getX() <= x+(Game.MULT_DIM-1)/4 && pos.getX() >= x-(Game.MULT_DIM+1)/4) &&
				(pos.getY() <= y+(Game.MULT_DIM-1)/4 && pos.getY() >= y-(Game.MULT_DIM+1)/4)) return true;
		return false;
	}
	
	public boolean xInRange(int x1, int x2) {
		return (x >= x1 && x <= x2);
	}
	
	public boolean yInRange(int y1, int y2) {
		return (y >= y1 && y <= y2);
	}
	
	public String toString() {
		return  x + "," + y;
	}
}
