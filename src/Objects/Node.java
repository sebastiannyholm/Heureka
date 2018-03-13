package objects;

import java.util.LinkedList;

public class Node {
	
	private Node parent;
	private Edge edge;
	
	private int g;
	private int h;
	
	public Node(Node p, Edge e) {
		
		this.parent = p;
		this.edge = e;
		
		this.g = (p == null) ? 0 : p.g() + 1;
		this.h = 0; // Just for not being null
	}
	
	public int g() {
		return this.g;
	}
	
	public int h() {
		return this.h;
	}

	public boolean isGoalState(LinkedList<Edge> goalEdges) {
		return goalEdges.contains(edge);
	}

	public String getPath() {
		return this.parent == null ? this.edge.getLabel() + "(" + String.format( "%.2f", this.edge.getWeight()) + ")" : this.parent.getPath() + " -> " + this.edge.getLabel() + "(" + String.format( "%.2f", this.edge.getWeight()) + ")";
	}

	public LinkedList<Node> getChildren() {
		LinkedList<Node> children = new LinkedList<Node>();
		
		for (Edge e : edge.getTarget().getEdges())
			children.add(new Node(this, e));
		
		return children; 
	}
}
