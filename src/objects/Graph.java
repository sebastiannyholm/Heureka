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
		    	String[] parts = line.split(" "); // 0: start x, 1: start y, 2: label, 3: x end, 4: y end 
		    	
		    	Vertex source = this.createOrGetVertex(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		    	Vertex target = this.createOrGetVertex(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
		    	
		    	Edge e = new Edge(parts[2], source, target);
		    	
		    	source.addEdge(e);
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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Vertex v : vertices)
			sb.append(v.toString()); 
		
		return sb.toString();
	}
	
	
}
