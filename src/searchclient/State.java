package searchclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

import knowledgebase.Clause;
import knowledgebase.KnowledgeBase;
import routefinding.Edge;
import routefinding.Graph;
import routefinding.Vertex;

public abstract class State {

	protected State parent;
	protected int g;
	protected int h;
	
	public State(State p) {
		this.parent = p;
	
		this.g = (p == null) ? 0 : p.g() + 1;
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
			
			this.clauses = this.parent != null ? new ArrayList<Clause>(((StateKnowledgeBase) this.parent).getClauses()) : new ArrayList<Clause>();
			if (c != null) this.clauses.add(c);
			
			//Collections.sort(clauses);
			
			this.clause = c;
			
			this.kb = kb;
		}

		@Override
		public void calcH(Object obj) {
			// TODO Auto-generated method stub
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
			
			for (Clause c1 : kb.getClauses()) {
				for (Clause c2 : kb.getClauses()) {
					if (!c1.equals(c2)) {
						children.add(new StateKnowledgeBase(this, this.kb, new Clause(c1, c2)));
					}
				}
				for (Clause c2 : clauses) {
					if (!c1.equals(c2)) {
						children.add(new StateKnowledgeBase(this, this.kb, new Clause(c1, c2)));
					}
				}
			}
				
				
			
			return children;
		}

		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public String toString() {
			
			StateKnowledgeBase parent = (StateKnowledgeBase) this.parent;
			
			String output = "";
			
			if (this.clause == null) {
				output = "Start:";
			} else if (parent.clause == null) {
				output = "Next do:";
			} else {
				output = "DONE?";
			}
			
			output = output + " " + this.kb.toString();
			
			return output;
		}
		
		public ArrayList<Clause> getClauses() {
			return this.clauses;
		}
	}

}