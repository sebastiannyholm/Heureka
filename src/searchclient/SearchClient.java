package searchclient;

import java.util.LinkedList;

import routefinding.Graph;
import routefinding.Vertex;
import knowledgebase.KnowledgeBase;
import knowledgebase.Clause;

public class SearchClient {	
	
	// Choose your strategy - BFS, DFS, AStar or Greedy
	public static String strategy = "AStar";
	
	// Choose your state space - RouteFinding or KnowledgeBase
	public static String task = "RouteFinding";
	
	// For RouteFinding
	// Define your file (the map to use) - manhattan.txt, copenhagen.txt or dtu.txt 
	public static String fileRF = "dtu.txt";
	// Choose one of the predefined or make one yourself - 1,2 or 3 - only works if dtu.txt is chosen as map.
	public static int level = 1;
	
	// For KnowledgeBase
	// Define your file (the knowledge to use) - kb1.txt, kb2.txt, kb3.txt or kb4.txt (kb4 is not solvable)
	public static String fileKB = "kb1.txt";

	
	
	
	
	// General objects
	private State initialState;
	private Object goalState;
	
	public SearchClient() { 
		
		if (task == "RouteFinding") {
			
			Graph graph = new Graph(fileRF);
			Vertex initialVertex = null;
			
			// Get crossing of label1 and label2
			if (fileRF == "dtu.txt") {
				
				if (level == 1) initialVertex = graph.getVertexByLabels("AnkerEngelundsVej", "Lundtoftegaardsvej");
				else if (level == 2) initialVertex = graph.getVertexByLabels("Akademivej", "Kollegiebakken");
				else if (level == 3) initialVertex = graph.getVertexByLabels("SkylabVej", "Skylab");
				
				if (level == 1) this.goalState = graph.getVertexByLabels("Elektrovej", "Hegnet");
				else if (level == 2) this.goalState = graph.getVertexByLabels("Elektrovej", "Hegnet");
				else if (level == 3) this.goalState = graph.getVertexByLabels("Kollegiebakken", "Bygning421Vej");
				
			} else if (fileRF == "manhattan.txt") {
				
				initialVertex = graph.getVertexByLabels("street_0", "avenue_0");
				this.goalState = graph.getVertexByLabels("street_9", "avenue_9");
				
			} else if (fileRF == "copenhagen.txt") {
				
				initialVertex = graph.getVertexByLabels("Vestervoldgade", "SktPedersStraede");
				this.goalState = graph.getVertexByLabels("Noerrevoldgade", "LarslejStraede");
				
			}
			
			this.initialState = new State.StateRouteFinding(null, graph, initialVertex, null);
		
		} else if (task == "KnowledgeBase") {
			
			KnowledgeBase kb = new KnowledgeBase(fileKB);
			
			this.initialState = new State.StateKnowledgeBase(null, kb, kb.getInput()); 
			this.goalState = new Clause(0); // Empty clause
			
		} else {
			System.out.println("Choose either RouteFinding or KnowledgeBase as your task");
			System.exit(0);
		}
	}

	public LinkedList<State> Search(Strategy ds) {
		
		ds.addToFrontier(this.initialState);
		
		while (!ds.fronterIsEmpty()) {
			
			State currentState = ds.getStateFromFrontier();
			
			if (currentState.isGoalState(this.goalState)) {
				return currentState.getPath(new LinkedList<State>());	
			}
			
			ds.addToExplored(currentState);
			
			for (State childState : currentState.getChildren()) {
				
				if (!ds.inFrontier(childState) && !ds.inExplored(childState)) {
					
					childState.calcH(this.goalState);
					
					ds.addToFrontier(childState);
				}
			}
			
		}
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		
		SearchClient client = new SearchClient();
		
		Strategy ds;
		
		switch (strategy) {
	        case "BFS":
	            ds = new Strategy.StrategyBFS();
	            break;
	        case "DFS":
	            ds = new Strategy.StrategyDFS();
	            break;
	        case "AStar":
	            ds = new Strategy.StrategyBestFirst(new Heuristic.AStar());
	            break;
	        case "Greedy":
	            ds = new Strategy.StrategyBestFirst(new Heuristic.Greedy());
	            break;
	        default:
	            ds = new Strategy.StrategyBFS();
	            System.err.println("No correct strategy detected, defaulting to (BFS)");    
		}
		
		LinkedList<State> path = client.Search(ds);
		
		if (path == null) {
			System.out.println("You goal does not exist.");
		} else {
			
			System.out.println("Path found in - explored states: " + ds.countExplored() + ", frontier: " + ds.countFrontier() + ", total: " + (ds.countExplored() + ds.countFrontier()) + ", solution length: " + path.size());
			System.out.println();
			
			for (State s : path) {
				System.out.println(s.toString());
			}
			
			System.out.println();
			System.out.println("Search completed.");

		}
		
    }
}