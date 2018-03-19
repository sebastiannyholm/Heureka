package searchclient;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public abstract class DataStructure {
	
	private HashSet<State> explored;
	
	public DataStructure() {
		this.explored = new HashSet<State>();
	}
	
	public void addToExplored(State s) {
		this.explored.add(s);
	}
	
	public boolean inExplored(State s) {
		return this.explored.contains(s);
	}
	
	public int countExplored() {
		return this.explored.size();
	}
	
	public abstract void addToFrontier(State s);
	public abstract boolean inFrontier(State s);
	public abstract boolean fronterIsEmpty();
	public abstract int countFrontier();
	public abstract State getNodeFromFrontier();
	
	public static class DataStructureBFS extends DataStructure {
		
		private ArrayDeque<State> frontier;
		private HashSet<State> frontierSet;
		
		public DataStructureBFS() {
			super();
			this.frontier = new ArrayDeque<State>();
			this.frontierSet = new HashSet<State>();
			
		}
	
		@Override
		public void addToFrontier(State s) {
			this.frontier.add(s);
			this.frontierSet.add(s);
		}
		
		@Override
		public boolean inFrontier(State s) {
			return this.frontierSet.contains(s);
		}
		
		@Override
		public State getNodeFromFrontier() {
			State s = this.frontier.pollFirst();
			this.frontierSet.remove(s);
			return s;
		}

		@Override
		public boolean fronterIsEmpty() {
			return this.frontier.isEmpty();
		}
		
		@Override
		public int countFrontier() {
			return this.frontier.size();
		}
	}
	
	public static class DataStructureDFS extends DataStructure {
		
		private Stack<State> frontier;
		private HashSet<State> frontierSet;
		
		public DataStructureDFS() {
			super();
			this.frontier = new Stack<State>();
			this.frontierSet = new HashSet<State>();
			
		}
	
		@Override
		public void addToFrontier(State s) {
			this.frontier.push(s);
			this.frontierSet.add(s);
		}
		
		@Override
		public boolean inFrontier(State s) {
			return this.frontierSet.contains(s);
		}
		
		@Override
		public State getNodeFromFrontier() {
			State s = this.frontier.pop();
			this.frontierSet.remove(s);
			return s;
		}
		
		@Override
		public boolean fronterIsEmpty() {
			return this.frontier.isEmpty();
		}

		@Override
		public int countFrontier() {
			return this.frontier.size();
		}
	}
	
	public static class DataStructureBestFirst extends DataStructure {
		
		private PriorityQueue<State> frontier;
		private HashSet<State> frontierSet;
		
		public DataStructureBestFirst(Heuristic h) {
			super();
			this.frontier = new PriorityQueue<State>(h);
			this.frontierSet = new HashSet<State>();
		}
	
		@Override
		public void addToFrontier(State s) {
			this.frontier.offer(s);
			this.frontierSet.add(s);
		}

		@Override
		public boolean inFrontier(State s) {
			return this.frontierSet.contains(s);
		}
		
		@Override
		public State getNodeFromFrontier() {
			State s = this.frontier.poll();
			this.frontierSet.remove(s);
			return s;
		}
		
		@Override
		public boolean fronterIsEmpty() {
			return this.frontier.isEmpty();
		}
		
		@Override
		public int countFrontier() {
			return this.frontier.size();
		}
	}
}
