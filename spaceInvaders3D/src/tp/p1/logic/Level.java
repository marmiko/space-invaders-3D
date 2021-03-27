package tp.p1.logic;

import tp.p1.util.Position;

public enum Level { EASY(4, 2, 0.1, 3, 0.0002, 1, 1, "easy", new Position(Game.MULT_DIM+Game.MULT_DIM/2, 3*Game.MULT_DIM), new Position(2*Game.MULT_DIM+Game.MULT_DIM/2, 4*Game.MULT_DIM)),
					HARD(8, 2, 0.3, 3, 0.0001, 2, 1, "hard", new Position(Game.MULT_DIM+Game.MULT_DIM/2, 3*Game.MULT_DIM), new Position(3*Game.MULT_DIM+Game.MULT_DIM/2, 4*Game.MULT_DIM)),
					INSANE(8, 4, 0.5, 2, 0.00005, 2,1, "insane", new Position(Game.MULT_DIM+Game.MULT_DIM/2, 3*Game.MULT_DIM), new Position(3*Game.MULT_DIM+Game.MULT_DIM/2, 3*Game.MULT_DIM));
	private int numCarrierShips, numDestroyers, Speed, maxInRow = 4, numRowsOfCarriers, numRowsOfDestroyers;
	private double Firepower, Ufo;
	private String Name;
	private Position carrierShipsInitialPosition, destroyersInitialPosition;
	
	Level(int crrshp, int dest, double frpw, int spd, double ufo, int nroc, int nrod, String name, Position carrierPosition, Position destroyerPosition){
		numCarrierShips = crrshp;
		numDestroyers = dest;
		Speed = spd;
		Firepower = frpw;
		Ufo = ufo;
		Name = name;
		carrierShipsInitialPosition = carrierPosition;
		destroyersInitialPosition = destroyerPosition;
		numRowsOfCarriers = nroc;
		numRowsOfDestroyers = nrod;
	}
	
	public int getNumCarrierShips() {
		return numCarrierShips;
	}
	
	public int getNumDestroyers() {
		return numDestroyers;
	}
	
	public int getSpeed() {
		return Speed;
	}
	
	public double getFirepower() {
		return Firepower;
	}
	
	public double getUfo() {
		return Ufo;
	}

	public Position getCarrierShipsInitPosition() {
		return carrierShipsInitialPosition;
	}
	
	public Position getDestroyersInitPosition() {
		return destroyersInitialPosition;
	}

	public int getNumCarriersPerRowe() {
		return numCarrierShips/numRowsOfCarriers;
	}
	
	public int getNumDestroyersPerRow() {
		return numDestroyers/numRowsOfDestroyers;
	}

	public static Level parse(String param) {
		for(Level level : Level.values()) {
			if ( level.Name.equalsIgnoreCase(param.toLowerCase()) ) return level;
		}
		return null;
	}

	public String toString() {
		return Name;
	}
	
}
