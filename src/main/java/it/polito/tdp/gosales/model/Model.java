package it.polito.tdp.gosales.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	private GOsalesDAO dao;
	private SimpleWeightedGraph<Products, DefaultWeightedEdge> grafo;
	private Map<Integer, Products> idMap;
	
	public Model() {
		this.dao = new GOsalesDAO();
		this.idMap = this.dao.getAllProducts();
	}
	public void creaGrafo(int anno, String colore) {
		this.grafo = new SimpleWeightedGraph<Products, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		
		//vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(colore));
		
		//archi
		List<Arco> archi = this.dao.getArchi(anno,colore,idMap);
		for (Arco a: archi) {
			Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getPeso());
		}
		
	}
	
	public List<Integer> getAnno(){
		return this.dao.getAnno();
	}
	public List<String> getColore(){
		return this.dao.getColore();
	}
	public int numV() {
		return this.grafo.vertexSet().size();
	}
	public int numA() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Arco> archi(int anno, String colore){
		return this.dao.getArchi(anno, colore, idMap);
	}
	
}
