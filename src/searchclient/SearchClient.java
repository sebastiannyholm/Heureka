package searchclient;

import java.util.LinkedList;

import objects.Edge;
import objects.Graph;
import objects.Node;
import objects.Vertex;

public class SearchClient {

	public static String strategy = "BFS";
	public static String map = "copenhagen.txt";
	
	private Graph graph;
	private LinkedList<Edge> initialEdges;
	private LinkedList<Edge> goalEdges;
	
	private LinkedList<Node> initialStates;
	
	public SearchClient(String map) {
		
		this.initialStates = new LinkedList<Node>(); 
		
		this.graph = new Graph(map);
		
		this.initialEdges = this.graph.getEdgesByLabel("Vestervoldgade");
		this.goalEdges = this.graph.getEdgesByLabel("Larsbjoernsstraede");
		
		for (Edge e : initialEdges)
			this.initialStates.add(new Node(null, e));
	}

	public String Search(DataStructure ds) {
		
		for (Node n : initialStates)
			ds.addToFrontier(n);
		
		while (!ds.fronterIsEmpty()) {
			
			Node frontier = ds.getNodeFromFrontier();
			
			if (frontier.isGoalState(this.goalEdges))
				return frontier.getPath();
			
			for (Node child : frontier.getChildren()) {
				if (!ds.inFrontier(child) && !ds.inExplored(child)) {
					ds.addToFrontier(child);
				}
			}
			
			ds.addToExplored(frontier);
		}
		
		return null;
		
	}
	
	public Graph getGraph() {
		return this.graph;
	}
	
	public static void main(String[] args) throws Exception {
		
		SearchClient client = new SearchClient(map);
		
		DataStructure ds;
		
		switch (strategy) {
	        case "BFS":
	            ds = new DataStructure.DataStructureBFS();
	            break;
	        case "DFS":
	            ds = new DataStructure.DataStructureDFS();
	            break;
	        case "BestSearch":
	            ds = new DataStructure.DataStructureBestFirst(new Heuristic());
	            break;
	        default:
	            ds = new DataStructure.DataStructureBFS();
	            System.err.println("Default strategy chosed (BFS)");    
		}
		
		System.out.println(client.Search(ds));
		
		
    }
}