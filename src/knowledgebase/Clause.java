package knowledgebase;

import java.util.HashMap;
import java.util.Map;

public class Clause {

	private HashMap<Literal, Boolean> literals = new HashMap<Literal, Boolean>();
	
	public Clause() {
		
	}
	
	public Clause(Clause c1, Clause c2) {
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
	}
	
	public void addLiteral(Literal l, Boolean b) {
		this.literals.put(l, b);
	}
	
	public HashMap<Literal, Boolean> getLiterals() {
		return this.literals;
	}
	
	@Override
	public String toString() {
		
		String toString = "";
		
		int counter = 1;
		for (Map.Entry<Literal, Boolean> entry : literals.entrySet()) {
			toString = toString + (entry.getValue() ? "!" : "") + entry.getKey();
			
			if (counter < literals.size()) {
				toString = toString + " v ";
			}
			
			counter++;
		}
		
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
		
		return this.hashCode() == ((Clause) obj).hashCode();
	}
}