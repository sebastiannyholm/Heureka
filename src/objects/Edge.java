package objects;

public class Edge {

	private String label;
	private Vertex source;
	private Vertex target;
	private double weight;
	
	public Edge(String label, Vertex source, Vertex target) {
		this.label = label;
		this.source = source;
		this.target = target;
	}
	
	public double getWeight() {
		if (this.weight == 0)
			this.weight = source.distanceFromNeighbor(target);
		
		return this.weight;
	}
	
	public String getLabel() {
		return this.label;
	}

	public Vertex getTarget() {
		return this.target;
	}
	
}
