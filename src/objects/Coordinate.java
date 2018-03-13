package objects;

public class Coordinate {
	
	public int x;
	public int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int manhattanDistance(Coordinate c) {
		return Math.abs(x - c.x) + Math.abs(y - c.y);
	}
	
	public double directDistance(Coordinate c) {
		return Math.sqrt(Math.pow(x - c.x, 2) + Math.pow(y - c.y, 2));
	}
	
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
