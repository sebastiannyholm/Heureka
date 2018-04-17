package knowledgebase;

import java.util.ArrayList;

public class KnowledgeBase {

	private ArrayList<Clause> clauses = new ArrayList<Clause>();
	
	public KnowledgeBase(String kb) {
		
		String[] lines = kb.split(System.getProperty("line.separator"));
		
		for (String line : lines) {
			
			char[] chars = line.toCharArray();
			
			Clause c = new Clause();
			boolean negated = false;
			
			for (int i = 0; i < chars.length; i++) {
				
				if (chars[i] == '<') {
					i++;
					negated = true;
				}
				
				
				Literal l = new Literal(chars[i]);
				
				c.addLiteral(l, negated);
			}
			clauses.add(c);
		}
	}
	
	public ArrayList<Clause> getClauses() {
		return this.clauses;
	}
	
	@Override
	public String toString() {
		
		String toString = "";
		
		for (Clause c : clauses) {
			toString = toString + c.toString() + "\n";
		}
		
		return toString;
	}
	
}
