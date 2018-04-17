package searchclient;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import routefinding.Graph;
import routefinding.Vertex;
import searchclient.State.StateKnowledgeBase;
import knowledgebase.KnowledgeBase;
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
			
			this.initialState = new State.StateKnowledgeBase(null, kb, null); 
			this.goalState = new Clause(); // Empty clause
			
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
			
//			try {
//				TimeUnit.SECONDS.sleep(0);
//				System.out.println(((StateKnowledgeBase) currentNode).getClauses().toString() + ", " + ((StateKnowledgeBase) currentNode).getClauses().size());
//				
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			
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
		
		System.out.println("Path found in - explored states: " + ds.countExplored() + ", frontier: " + ds.countFrontier() + ", total: " + (ds.countExplored() + ds.countFrontier()));
		System.out.println();
		
		for (State s : path) {
			System.out.println(s.toString());
		}
		
		System.out.println("You have arrived at your destination.");
		
    }
}