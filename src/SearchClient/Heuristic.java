package searchclient;

import java.util.Comparator;

public class Heuristic implements Comparator<State> {

	public Heuristic() {
		
	}

	@Override
	public int compare(State s1, State s2) {
		return this.f(s1) - this.f(s2);
	}
	
	public int f(State s) {
		return s.g() + s.h();
	}
	
}
