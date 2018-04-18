package knowledgebase;

import java.util.HashMap;
import java.util.Map;

public class Clause {

	private HashMap<Literal, Boolean> literals = new HashMap<Literal, Boolean>();
	private int counter;
	private int c1;
	private int c2;
	
	public Clause(int counter) {
		this.counter = counter;
	}
	
	public Clause(Clause c1, Clause c2, int counter) {
		for (Map.Entry<Literal, Boolean> entry : c1.getLiterals().entrySet()) {
			if (c2.getLiterals().containsKey(entry.getKey())) {
				if (c2.getLiterals().get(entry.getKey()) == entry.getValue()) {
					this.addLiteral(entry.getKey(), entry.getValue());
				}
			} else {
				this.addLiteral(entry.getKey(), entry.getValue());
			}
		}
		
		for (Map.Entry<Literal, Boolean> entry : c2.getLiterals().entrySet()) {
			if (c1.getLiterals().containsKey(entry.getKey())) {
				if (c1.getLiterals().get(entry.getKey()) == entry.getValue()) {
					this.addLiteral(entry.getKey(), entry.getValue());
				}
			} else {
				this.addLiteral(entry.getKey(), entry.getValue());
			}
		}
		
		this.c1 = c1.getCounter();
		this.c2 = c2.getCounter();
		this.counter = counter;
	}
	
	public void addLiteral(Literal l, Boolean b) {
		this.literals.put(l, b);
	}
	
	public int getCounter() {
		return this.counter;
	}
	
	public HashMap<Literal, Boolean> getLiterals() {
		return this.literals;
	}
	
	@Override
	public String toString() {
		return this.toString(false);
	}
	
	public String toString(boolean printStuff) {
		
		String toString = this.counter + ". ";
		
		if (printStuff) 
			toString = toString + "(" + this.c1 + "," + this.c2 + ") ";
		
		int counter = 1;
		for (Map.Entry<Literal, Boolean> entry : literals.entrySet()) {
			toString = toString + (entry.getValue() ? "!" : "") + entry.getKey();
			
			if (counter < literals.size()) {
				toString = toString + " v ";
			}
			
			counter++;
		}
		
		if (literals.isEmpty())
			toString = toString + "{}";
		
		return toString;
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (obj == null)
			return false;
		else if (this.getClass() != obj.getClass())
			return false;
		
		Clause c = (Clause) obj;
		
		for (Map.Entry<Literal, Boolean> entry : literals.entrySet()) {
			if (!(c.getLiterals().containsKey(entry.getKey()) && c.getLiterals().get(entry.getKey()) == entry.getValue())) {
				return false;
			}
		}
		
		for (Map.Entry<Literal, Boolean> entry : c.getLiterals().entrySet()) {
			if (!(literals.containsKey(entry.getKey()) && literals.get(entry.getKey()) == entry.getValue())) {
				return false;
			}
		}
		
		return true;
	}

}