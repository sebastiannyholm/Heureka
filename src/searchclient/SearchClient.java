package searchclient;

import java.util.LinkedList;

import routefinding.Graph;
import routefinding.Vertex;
import searchclient.State.StateKnowledgeBase;
import knowledgebase.KnowledgeBase;
import knowledgebase.Literal;
import knowledgebase.Clause;

public class SearchClient {

	//public static String task = "RouteFinding";
	public static String task = "KnowledgeBase"; 
	public static String strategy = "BestSearch";
	
	// For RouteFinding
	//public static String map = "manhattan.txt";
	//public static String map = "copenhagen.txt";
	public static String map = "dtu.txt";
	
	// For KnowledgeBase
	
	private State initialState;
	private Object goalState;
	
	public SearchClient() { 
		
		if (task == "RouteFinding") {
			Graph graph = new Graph(map);
			
			// Get crossing of label1 and label2
			//this.initialVertex = this.graph.getVertexByLabels("street_0", "avenue_0");
			//this.goalVertex = this.graph.getVertexByLabels("street_9", "avenue_9");
			
			//this.initialVertex = this.graph.getVertexByLabels("Vestervoldgade", "SktPedersStraede");
			//this.goalVertex = this.graph.getVertexByLabels("Noerrevoldgade", "LarslejStraede");
			
			Vertex initialVertex = graph.getVertexByLabels("NielsKoppelsAlle", "ParkeringsVej");
			this.initialState = new State.StateRouteFinding(null, graph, initialVertex, null);
			
			this.goalState = graph.getVertexByLabels("Elektrovej", "Hegnet");
		
		} else if (task == "KnowledgeBase") {
			
			String kbStr = "a<b\n" 
						 + "b<cd\n" 
					     + "bc<d\n" 
						 + "d";
			
			KnowledgeBase kb = new KnowledgeBase(kbStr);
			
			Clause a = new Clause(kb.getClauses().size());
			a.addLiteral(new Literal('a'), true);
			
			this.initialState = new State.StateKnowledgeBase(null, kb, a); 
			this.goalState = new Clause(0); // Empty clause
			
		} else {
			
		}
	}

	public LinkedList<State> Search(DataStructure ds) {
		
		ds.addToFrontier(this.initialState);
		
		while (!ds.fronterIsEmpty()) {
			
			State currentNode = ds.getNodeFromFrontier();
			
			if (currentNode.isGoalState(this.goalState)) {
				return currentNode.getPath(new LinkedList<State>());	
			}
			
			for (State childNode : currentNode.getChildren()) {
				
				if (!ds.inFrontier(childNode) && !ds.inExplored(childNode)) {
					
					childNode.calcH(this.goalState);
					
					ds.addToFrontier(childNode);
				}
			}
			
			ds.addToExplored(currentNode);
		}
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		
		SearchClient client = new SearchClient();
		
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
		
		if (path == null) {
			System.out.println("You goal does not exist.");
		} else {
			
			System.out.println("Path found in - explored states: " + ds.countExplored() + ", frontier: " + ds.countFrontier() + ", total: " + (ds.countExplored() + ds.countFrontier()));
			System.out.println();
			
			for (State s : path) {
				System.out.println(s.toString());
			}
			
			System.out.println();
			System.out.println("Search completed.");

		}
		
    }
}