package objects;

import java.util.LinkedList;
import java.util.List;

public class Vertex {
	
	private LinkedList<Edge> edges;
	private Coordinate position;
	
	public Vertex(int x, int y) {
		this.position = new Coordinate(x,y);
		this.edges = new LinkedList<Edge>();
	}
	
	public void addEdge(Edge e) {
		this.edges.add(e);
	}
	
	public boolean exists(int x, int y) {
		return this.position.x == x && this.position.y == y;
	}
	
	public double distanceFromNeighbor(Vertex v) {
		return this.distanceFromNeighbor(v, "direct");
	}
	
	public double distanceFromNeighbor(Vertex v, String type) {
		return type == "manhattan" ? this.position.manhattanDistance(v.getPosition()) : this.position.directDistance(v.getPosition());
	}
	
	public Coordinate getPosition() {
		return this.position;
	}
	
	public String toString() {
		return this.position.toString();  
	}
	
	public LinkedList<Edge> getEdges() {
		return this.edges;
	}

}
