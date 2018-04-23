package knowledgebase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KnowledgeBase {

	private ArrayList<Clause> clauses = new ArrayList<Clause>();
	private Clause input = new Clause(0);
	
	public KnowledgeBase(String file) {
		
		try (BufferedReader br = new BufferedReader(new FileReader("src/maps/" + file))) {
			
			String line;
			boolean lastLine = false;
		    while ((line = br.readLine()) != null) {
		    	
		    	if (line.contains("-")) {
		    		lastLine = true;
		    		continue;
		    	}
		    	
		    	String[] tokens = line.split(" ");
				
				Clause c = new Clause(this.clauses.size());
				
				boolean negated = false;
				
				for (int i = 0; i < tokens.length; i++) {
					
					if (tokens[i].charAt(0) == '<') {
						i++;
						negated = true;
					}
					
					Literal l = new Literal(tokens[i].charAt(0));
					
					c.addLiteral(l, negated);
				}
				
				if (lastLine) {
					this.input = c;
		    	} else {
		    		this.clauses.add(c);
		    	}
			}
		    
		    
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Clause> getClauses() {
		return this.clauses;
	}
	
	public Clause getInput() {
		return this.input;
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
