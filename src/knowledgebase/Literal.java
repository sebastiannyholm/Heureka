package knowledgebase;

public class Literal {
	
	private char label;
	
	public Literal(char label) {
		this.label = label;
	}
	
	public char getLabel() {
		return this.label;
	}
	
	@Override
	public String toString() {
		return Character.toString(this.label);
	}
	
	@Override
	public int hashCode() {
		return Character.toString(this.label).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (obj == null)
			return false;
		else if (this.getClass() != obj.getClass())
			return false;
		
		return this.label == ((Literal) obj).getLabel();
	}
}
