package searchclient;

import java.util.LinkedList;

import routefinding.Graph;
import routefinding.Vertex;
import knowledgebase.KnowledgeBase;
import knowledgebase.Clause;

public class SearchClient {
 
	public static String strategy = "AStar";
	
	// For RouteFinding
	//public static String task = "RouteFinding";
	//public static String file = "manhattan.txt";
	//public static String file = "copenhagen.txt";
	//public static String file = "dtu.txt";
	
	// For KnowledgeBase
	public static String task = "KnowledgeBase";
	public static String file = "kb1.txt";
	
	private State initialState;
	private Object goalState;
	
	public SearchClient() { 
		
		if (task == "RouteFinding") {
			Graph graph = new Graph(file);
			
			// Get crossing of label1 and label2
			//this.initialVertex = this.graph.getVertexByLabels("street_0", "avenue_0");
			//this.goalVertex = this.graph.getVertexByLabels("street_9", "avenue_9");
			
			//this.initialVertex = this.graph.getVertexByLabels("Vestervoldgade", "SktPedersStraede");
			//this.goalVertex = this.graph.getVertexByLabels("Noerrevoldgade", "LarslejStraede");
			
			Vertex initialVertex = graph.getVertexByLabels("AnkerEngelundsVej", "Lundtoftegaardsvej");
			this.initialState = new State.StateRouteFinding(null, graph, initialVertex, null);
			
			this.goalState = graph.getVertexByLabels("Elektrovej", "Hegnet");
		
		} else if (task == "KnowledgeBase") {
			
			KnowledgeBase kb = new KnowledgeBase(file);
			
			this.initialState = new State.StateKnowledgeBase(null, kb, kb.getInput()); 
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
	        case "AStar":
	            ds = new DataStructure.DataStructureBestFirst(new Heuristic.AStar());
	            break;
	        case "Greedy":
	            ds = new DataStructure.DataStructureBestFirst(new Heuristic.Greedy());
	            break;
	        default:
	            ds = new DataStructure.DataStructureBFS();
	            System.err.println("No correct strategy detected, defaulting to (BFS)");    
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