package searchclient;

import java.util.LinkedList;

import objects.Edge;
import objects.Graph;
import objects.Vertex;

public class SearchClient {

	public static String strategy = "BestSearch";
	//public static String map = "manhattan.txt";
	public static String map = "copenhagen.txt";
	
	private Graph graph;
	private Vertex initialVertex;
	private Vertex goalVertex;
	
	private State initialState;
	
	public SearchClient(String map) { 
		
		this.graph = new Graph(map);
		
		// Get crossing of label1 and label2
		//this.initialVertex = this.graph.getVertexByLabels("street_0", "avenue_0");
		//this.goalVertex = this.graph.getVertexByLabels("street_9", "avenue_9");
		
		this.initialVertex = this.graph.getVertexByLabels("Vestervoldgade", "SktPedersStraede");
		this.goalVertex = this.graph.getVertexByLabels("Noerrevoldgade", "LarslejStraede");
		
		
		this.initialState = new State(null, initialVertex, null);
	}

	public LinkedList<State> Search(DataStructure ds) {
		
		ds.addToFrontier(initialState);
		
		while (!ds.fronterIsEmpty()) {
			
			State frontier = ds.getNodeFromFrontier();
			
			if (frontier.isGoalState(this.goalVertex))
				return frontier.getPath(new LinkedList<State>());
			
			for (State child : frontier.getChildren()) {
				if (!ds.inFrontier(child) && !ds.inExplored(child)) {
					child.calcH(goalVertex);
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
		
		LinkedList<State> path = client.Search(ds);
		
		System.out.println("Path found in - explored states: " + ds.countExplored() + ", frontier: " + ds.countFrontier() + ", total: " + (ds.countExplored() + ds.countFrontier()));
		System.out.println();
		
		for (State s : path) {
			System.out.println(s.toString());
		}
		
		System.out.println("You have arrived at your destination.");
		
    }
}