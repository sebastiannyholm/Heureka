package SearchClient;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

import Objects.Node;

public abstract class DataStructure {
	
	private HashSet<Node> explored;
	
	public DataStructure() {
		
		
		this.explored = new HashSet<Node>();
	}
	
	public void addToExplored(Node n) {
		this.explored.add(n);
	}
	
	public boolean inExplored(Node n) {
		return this.explored.contains(n);
	}
	
	public abstract void addToFrontier(Node n);
	public abstract boolean inFrontier(Node n);
	public abstract Node getNodeFromFrontier();
	
	public class DataStructureBFS extends DataStructure {
		
		private ArrayDeque<Node> frontier;
		private HashSet<Node> frontierSet;
		
		public DataStructureBFS() {
			this.frontier = new ArrayDeque<Node>();
			this.frontierSet = new HashSet<Node>();
			
		}
	
		@Override
		public void addToFrontier(Node n) {
			this.frontier.add(n);
			this.frontierSet.add(n);
		}
		
		@Override
		public boolean inFrontier(Node n) {
			return this.frontierSet.contains(n);
		}
		
		@Override
		public Node getNodeFromFrontier() {
			Node n = this.frontier.pollFirst();
			this.frontierSet.remove(n);
			return n;
		}
	}
	
	public class DataStructureDFS extends DataStructure {
		
		private Stack<Node> frontier;
		private HashSet<Node> frontierSet;
		
		public DataStructureDFS() {
			this.frontier = new Stack<Node>();
			this.frontierSet = new HashSet<Node>();
			
		}
	
		@Override
		public void addToFrontier(Node n) {
			this.frontier.push(n);
			this.frontierSet.add(n);
		}
		
		@Override
		public boolean inFrontier(Node n) {
			return this.frontierSet.contains(n);
		}
		
		@Override
		public Node getNodeFromFrontier() {
			Node n = this.frontier.pop();
			this.frontierSet.remove(n);
			return n;
		}
	}
	
	public class DataStructureBestFirst extends DataStructure {
		
		private PriorityQueue<Node> frontier;
		private HashSet<Node> frontierSet;
		
		public DataStructureBestFirst(Heuristic h) {
			this.frontier = new PriorityQueue<Node>(h);
			this.frontierSet = new HashSet<Node>();
		}
	
		@Override
		public void addToFrontier(Node n) {
			this.frontier.offer(n);
			this.frontierSet.add(n);
		}

		@Override
		public boolean inFrontier(Node n) {
			return this.frontierSet.contains(n);
		}
		
		@Override
		public Node getNodeFromFrontier() {
			Node n = this.frontier.poll();
			this.frontierSet.remove(n);
			return n;
		}
	}
}
