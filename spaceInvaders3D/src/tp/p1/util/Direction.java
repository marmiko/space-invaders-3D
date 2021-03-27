package tp.p1.util;

public enum Direction {
	RIGHT(1, "right", "r"),
	LEFT(-1, "left", "l");
	
	private int directionInt;
	private String name;
	private String shortName;
	
	Direction (int directionInt, String name, String shortName) {
		this.directionInt = directionInt;
		this.name = name;
		this.shortName = shortName;
	}

	public int getDirectionInt() {
		return this.directionInt;
	}
	
	public static Direction parse(String name) {
		for(Direction dir : Direction.values()) {
			if(dir.name.equalsIgnoreCase(name) || dir.shortName.equalsIgnoreCase(name)) return dir;
		}
		return null;
	}
	
}
