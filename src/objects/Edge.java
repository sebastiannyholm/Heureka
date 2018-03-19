package objects;

public class Edge {

	private String label;
	private Vertex source;
	private Vertex target;
	private int weight;
	
	public Edge(String label, Vertex source, Vertex target) {
		this.label = label;
		this.source = source;
		this.target = target;
		this.weight = source.distanceTo(target);
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public String getLabel() {
		return this.label;
	}

	public Vertex getSource() {
		return this.source;
	}
	
	public Vertex getTarget() {
		return this.target;
	}
	
}
