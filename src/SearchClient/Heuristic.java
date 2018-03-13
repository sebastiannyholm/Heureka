package SearchClient;

import java.util.Comparator;

import Objects.Node;

public class Heuristic implements Comparator<Node> {

	public Heuristic() {
		
	}

	@Override
	public int compare(Node n1, Node n2) {
		return this.g(n1) - this.g(n2);
	}
	
	public int g(Node n) {
		return n.g() + n.h();
	}
	
}
