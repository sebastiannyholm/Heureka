package searchclient;

import java.util.Comparator;

public abstract class Heuristic implements Comparator<State> {

	public Heuristic() {
		
	}

	@Override
	public int compare(State s1, State s2) {
		return this.f(s1) - this.f(s2);
	}
	
	public abstract int f(State s);
	
	public static class AStar extends Heuristic {
		
		public AStar() {
			
		}
		
		public int f(State s) {
			return s.g() + s.h();
		}
		
	}
	
	public static class Greedy extends Heuristic {
		
		public Greedy() {
			
		}
		
		public int f(State s) {
			return s.h();
		}
		
	}
	
}
