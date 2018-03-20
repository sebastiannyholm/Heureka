package searchclient;

import java.util.LinkedList;

import objects.Edge;
import objects.Vertex;

public class State {
	
	private State parent;
	private Vertex vertex;
	private Edge path;
	
	private int g;
	private int h;
	
	private static final String UNIT = "unit"; 
	
	public State(State p, Vertex v, Edge e) {
		this.parent = p;
		this.vertex = v;
		this.path = e;
		
		this.g = (p == null) ? 0 : p.g() + vertex.distanceTo(p.getVertex());
		this.h = 0; // Just for not being null
	}
	
	public int g() {
		return this.g;
	}
	
	public void calcH(Vertex v) {
		this.h = (int) Math.pow(this.vertex.distanceTo(v),2);
	}
	
	public int h() {
		return this.h;
	}

	public boolean isGoalState(Vertex v) {
		return this.vertex == v;
	}

	public LinkedList<State> getPath(LinkedList<State> path) {
		
		if (this.parent == null) {
			path.add(this);
		} else {
			this.parent.getPath(path);
			path.add(this);
		}
		
		return path;
	}
	
	public Vertex getVertex() {
		return this.vertex;
	}
	
	public Edge getPath() {
		return this.path;
	}

	public LinkedList<State> getChildren() {
		LinkedList<State> children = new LinkedList<State>();
		
		for (Edge e : vertex.getOutgoing())
			children.add(new State(this, e.getTarget(), e));
		
		return children;
	}
	
	@Override
	public int hashCode() {
		return this.vertex.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (obj == null)
			return false;
		else if (this.getClass() != obj.getClass())
			return false;
		
		return this.vertex == ((State) obj).getVertex();
	}
	
	@Override
	public String toString() {
		
		String output = "";
		
		if (this.path == null) {
			output = "Start position:";
		} else if (this.parent.getPath() == null) {
			output = "Follow \t\t" + this.path.toString() + " for " + this.path.getWeight() + " " + UNIT + ". (" + this.g + " " + UNIT + ".)";
		} else if (this.path.getLabel().equals(this.parent.getPath().getLabel())) {
			output = "Continue on \t" + this.path.toString() + " for " + this.path.getWeight() + " " + UNIT + ". (" + this.g + " " + UNIT + ".)";
		} else {
			output = "Turn onto \t"  + this.path.toString() + " for " + this.path.getWeight() + " " + UNIT + ". (" + this.g + " " + UNIT + ".)";
		}
		
		output = output + " " + this.vertex.toString();
		
		return output;
	}
}
