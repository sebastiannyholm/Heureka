package Objects;

public class Node {
	
	private Node parent;
	
	private int g;
	private int h;
	
	public Node(Node p) {
		
		this.parent = p;
		
		this.g = (p == null) ? 0 : p.g() + 1;
		this.h = 0; // Just for not being null
	}
	
	public int g() {
		return this.g;
	}
	
	public int h() {
		return this.h;
	}
}
