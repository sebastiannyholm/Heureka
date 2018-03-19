package objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Graph {

	private List<Vertex> vertices;
	private List<Edge> edges;
	
	public Graph(String map) {
		
		this.vertices = new LinkedList<Vertex>();
		this.edges = new LinkedList<Edge>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("src/maps/" + map))) {
			
			String line;
		    while ((line = br.readLine()) != null) {
		    	String[] tokens = line.split(" "); // 0: start x1, 1: start y1, 2: label, 3: end x2, 4: end y2 
		    	
		    	Vertex source = this.createOrGetVertex(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
		    	Vertex target = this.createOrGetVertex(Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
		    	
		    	Edge e = new Edge(tokens[2], source, target);
		    	
		    	source.addOutgoing(e);
		    	target.addIngoing(e);
		    	
		    	this.edges.add(e);
		    }
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	private Vertex createOrGetVertex(int x, int y) {
		Vertex newVertex = this.getVertexByPosition(x,y);
		
		if (newVertex != null)
			return newVertex;
		
		// If vertex doesn't exist, create new
		newVertex = new Vertex(x, y);
		
		this.vertices.add(newVertex);
	
		return newVertex;
	}
	
	public Vertex getVertexByPosition(int x, int y) {
		for (Vertex v : vertices)
			if (v.exists(x,y))
				return v;
		
		return null;
	}
	
	public LinkedList<Edge> getEdgesByLabel(String label) {
		
		LinkedList<Edge> edges = new LinkedList<Edge>();
		
		for (Edge e : this.edges)
			if (e.getLabel().equals(label))
				edges.add(e);
		
		return edges; 
	}
	
	public Vertex getVertexByLabels(String label1, String label2) {
		for (Vertex v : this.vertices) {
			
			boolean bool1 = false;
			boolean bool2 = false;
			
			for (Edge e : v.getIngoing()) {
				if (e.getLabel().equals(label1)) {
					bool1 = true;
				} else if (e.getLabel().equals(label2)) {
					bool2 = true;
				}
			}
			
			for (Edge e : v.getOutgoing()) {
				if (e.getLabel() == label1) {
					bool1 = true;
				} else if (e.getLabel().equals(label2)) {
					bool2 = true;
				}
			}
			
			if (bool1 && bool2)
				return v;
		}
		
		return null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Vertex v : vertices)
			sb.append(v.toString()); 
		
		return sb.toString();
	}
	
	
}
