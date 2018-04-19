package knowledgebase;

import java.util.ArrayList;

public class KnowledgeBase {

	private ArrayList<Clause> clauses = new ArrayList<Clause>();
	
	public KnowledgeBase(String kb) {
		
		String[] lines = kb.split("\n");
		
		for (String line : lines) {
			
			char[] chars = line.toCharArray();
			
			Clause c = new Clause(this.clauses.size());
			
			boolean negated = false;
			
			for (int i = 0; i < chars.length; i++) {
				
				if (chars[i] == '<') {
					i++;
					negated = true;
				}
				
				
				Literal l = new Literal(chars[i]);
				
				c.addLiteral(l, negated);
			}
			this.clauses.add(c);
		}
	}
	
	public ArrayList<Clause> getClauses() {
		return this.clauses;
	}
	
	@Override
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean printStuff) {
		String toString = "";
		
		for (Clause c : clauses)
			toString = toString + c.toString(printStuff) + "\n";
		
		return toString;
	}
	
}
