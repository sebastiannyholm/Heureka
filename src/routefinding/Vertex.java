package routefinding;

import java.util.LinkedList;

public class Vertex {
	
	private LinkedList<Edge> outgoing;
	private LinkedList<Edge> ingoing;
	
	private Coordinate position;
	
	public Vertex(int x, int y) {
		this.position = new Coordinate(x,y);
		this.outgoing = new LinkedList<Edge>();
		this.ingoing = new LinkedList<Edge>();
	}
	
	public void addOutgoing(Edge e) {
		this.outgoing.add(e);
	}
	
	public void addIngoing(Edge e) {
		this.ingoing.add(e);
	}
	
	public boolean exists(int x, int y) {
		return this.position.x == x && this.position.y == y;
	}
	
	public int distanceTo(Vertex v) {
		return this.distanceTo(v, "direct");
	}
	
	public int distanceTo(Vertex v, String type) {
		return type == "manhattan" ? this.position.manhattanDistance(v.getPosition()) : this.position.directDistance(v.getPosition());
	}
	
	public Coordinate getPosition() {
		return this.position;
	}
	
	@Override
	public String toString() {
		return this.position.toString();  
	}
	
	public LinkedList<Edge> getOutgoing() {
		return this.outgoing;
	}
	
	public LinkedList<Edge> getIngoing() {
		return this.ingoing;
	}

}
