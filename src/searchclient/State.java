package searchclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

import knowledgebase.Clause;
import knowledgebase.KnowledgeBase;
import knowledgebase.Literal;
import routefinding.Edge;
import routefinding.Graph;
import routefinding.Vertex;

public abstract class State {

	protected State parent;
	protected int g;
	protected int h;
	
	public State(State p) {
		this.parent = p;
		
		this.h = 0;
	}
	
	public int g() {
		return this.g;
	}
	
	public int h() {
		return this.h;
	}
	
	public LinkedList<State> getPath(LinkedList<State> path) {
		
		if (this.parent == null) {
			path.add(this);
		} else {
			this.parent.getPath(path);
			path.add(this);
		}
		
		return path;
	}

	public abstract void calcH(Object obj);
	public abstract boolean isGoalState(Object obj);
	public abstract LinkedList<State> getChildren();
	public abstract int hashCode();
	public abstract boolean equals(Object obj);
	public abstract String toString();
	
	public static class StateRouteFinding extends State {
		
		private Graph graph;
		private Vertex vertex;
		private Edge edgeToVertex;
		
		private static final String UNIT = "unit";
		
		public StateRouteFinding(StateRouteFinding p, Graph g, Vertex v, Edge e) {
			super(p);
			this.graph = g;
			this.vertex = v;
			this.edgeToVertex = e;
			
			StateRouteFinding parent = (StateRouteFinding) this.parent;
			this.g = (parent == null) ? 0 : parent.g() + e.getWeight();
		}
		
		@Override
		public void calcH(Object obj) {
			if (obj instanceof Vertex)
			{
				Vertex v = (Vertex) obj;
				this.h = this.vertex.distanceTo(v);	
			}
		}
		
		@Override
		public boolean isGoalState(Object obj) {
			if (obj instanceof Vertex)
			{
				Vertex v = (Vertex) obj;
				return this.vertex == v;	
			}
			return false;
		}
	
		@Override
		public LinkedList<State> getChildren() {
			LinkedList<State> children = new LinkedList<State>();
			
			for (Edge e : vertex.getOutgoing())
				children.add(new StateRouteFinding(this, this.graph, e.getTarget(), e));
			
			return children;
		}
		
		@Override
		public int hashCode() {
			return this.vertex.toString().hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			else if (obj == null)
				return false;
			else if (this.getClass() != obj.getClass())
				return false;
			
			return this.vertex == ((StateRouteFinding) obj).getVertex();
		}
		
		@Override
		public String toString() {
			
			StateRouteFinding parent = (StateRouteFinding) this.parent;
			
			String output = "";
			
			if (this.edgeToVertex == null) {
				output = "Start position:";
			} else if (parent.getEdgeToVertex() == null) {
				output = "Follow \t\t" + this.edgeToVertex.toString() + " for " + this.edgeToVertex.getWeight() + " " + UNIT + ". (" + this.g + " " + UNIT + ".)";
			} else if (this.edgeToVertex.getLabel().equals(parent.getEdgeToVertex().getLabel())) {
				output = "Continue on \t" + this.edgeToVertex.toString() + " for " + this.edgeToVertex.getWeight() + " " + UNIT + ". (" + this.g + " " + UNIT + ".)";
			} else {
				output = "Turn onto \t"  + this.edgeToVertex.toString() + " for " + this.edgeToVertex.getWeight() + " " + UNIT + ". (" + this.g + " " + UNIT + ".)";
			}
			
			output = output + " " + this.vertex.toString();
			
			return output;
		}
		
		public Vertex getVertex() {
			return this.vertex;
		}
		
		public Edge getEdgeToVertex() {
			return this.edgeToVertex;
		}
	}
	
	public static class StateKnowledgeBase extends State {
		
		private KnowledgeBase kb;
		private ArrayList<Clause> clauses;
		private Clause clause;
		
		public StateKnowledgeBase(StateKnowledgeBase p, KnowledgeBase kb, Clause c) {
			super(p);
			
			StateKnowledgeBase parent = (StateKnowledgeBase) this.parent;
			
			this.g = (parent == null) ? 0 : parent.g() + 1;
			this.kb = kb;
			
			this.clauses = parent != null ? new ArrayList<Clause>(parent.getClauses()) : new ArrayList<Clause>();
			if (parent != null) this.clauses.add(parent.getClause());
			
			this.clause = c;	
		}

		public void calcH2(Clause prevClause, Clause combinedClause) {
			
			int score = 0;
			
			for (Map.Entry<Literal, Boolean> entry : prevClause.getLiterals().entrySet()) {
				if (combinedClause.getLiterals().containsKey(entry.getKey())) {
					if (combinedClause.getLiterals().get(entry.getKey()) == entry.getValue()) { 
						// Literal is in both, but the same
						score = score + 1;
					} else { // Literal is in both, one negated and one not
						score = score + 0;
					}
				} else { // Literal is only in prevClause
					score = score + 2;
				}
			}
			
			for (Map.Entry<Literal, Boolean> entry : combinedClause.getLiterals().entrySet()) {
				if (!prevClause.getLiterals().containsKey(entry.getKey())) {
					// Literal is only in combinedClause
					score = score + 2;
				}
			}
			
			this.h = score;
		}
		
		@Override
		public void calcH(Object obj) {
			//this.h = this.clause.getLiterals().size();	
		}
		
		@Override
		public boolean isGoalState(Object obj) {
			if (obj instanceof Clause)
			{
				Clause c = (Clause) obj;
				return this.clause != null ? this.clause.equals(c) : this.kb.getClauses().isEmpty();	
			}
			return false;
		}

		@Override
		public LinkedList<State> getChildren() {
			LinkedList<State> children = new LinkedList<State>();
			
			ArrayList<Clause> allClauses = new ArrayList<Clause>();
			allClauses.addAll(this.kb.getClauses());
			allClauses.addAll(this.clauses);
			
			for (Clause c : allClauses) {
				if (c.equals(this.clause)) continue;
				
				Clause newC = new Clause(c, this.clause, this.clause.getCounter() + 1);
				
				if (!this.kb.getClauses().contains(newC)) {
					StateKnowledgeBase s = new StateKnowledgeBase(this, this.kb, newC);
					s.calcH2(this.clause, c);
					children.add(s);
				}
			}
			
			return children;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof StateKnowledgeBase) {
				StateKnowledgeBase state = (StateKnowledgeBase) obj;
				
				ArrayList<Clause> allClauses = new ArrayList<Clause>(state.clauses);
				allClauses.add(state.clause);
				
				for (Clause c : state.getClauses()) {
					if (!allClauses.contains(c)) {
						return false;
					}
				}
				
				return allClauses.contains(this.clause);
			}
			
			return false;
		}

		@Override
		public String toString() {
			
			StateKnowledgeBase parent = (StateKnowledgeBase) this.parent;
			
			String output = "";
			
			if (parent == null) {
				output = "Start:\n" + this.kb.toString() + "----------\n" + this.clause.toString() + "\n----------";
			} else {
				output = this.clause.toString(true) + "\n----------";
			}
			
			return output;
		}
		
		public ArrayList<Clause> getClauses() {
			return this.clauses;
		}
		
		public Clause getClause() {
			return this.clause;
		}
	}

}