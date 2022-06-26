package it.polito.tdp.yelp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;


public class Model {
	YelpDao dao;
	Graph <Business,DefaultWeightedEdge>grafo;
	Map<String,Business>idMap;
	
	public Model()
	{
		dao = new YelpDao();
		idMap= new HashMap<>();
	}
	
	public List<String> getCities()
	{
		return dao.getAllCittà();
	}
	public String creaGrafo(String c)
	{
	      
		  grafo = new SimpleWeightedGraph<Business,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		  
		  for(Business b : dao.getAllBusines(c))
		  {
			  idMap.put(b.getBusinessId(), b);
		  }
		  Graphs.addAllVertices(grafo, dao.getAllBusines(c));
		  
		  for(Adiacenza a : dao.getAllArchi(c))
		  {
			  Graphs.addEdgeWithVertices(grafo, idMap.get(a.getB1()),idMap.get(a.getB2()), a.getPeso());
		  }
		  
		  
		  return  "#Numero di vertici : " + grafo.vertexSet().size()  + "Archi " + grafo.edgeSet().size();
		  
	}
	public List<Business>getVertici( String c)
	{
		return dao.getAllBusines(c);
	}
	public String getPiuLungo(Business b)
	{
		List<Business>vicini= Graphs.neighborListOf(grafo, b);
		Business bok = null;
		double distance = 0;
		for( Business b2 : vicini)
		{
		//peso di un arco
			if(grafo.getEdgeWeight(grafo.getEdge(b, b2))>distance)
			{
				bok=b2;
				distance=grafo.getEdgeWeight(grafo.getEdge(b, b2));
				
			}
		}
		
		return "Locale più distante è :" + bok + " =  " + distance;
		
	}
	
	
		
	}
