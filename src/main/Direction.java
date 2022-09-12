package hw7;

import hw4.Edge;
import java.util.*;

public class Direction implements Comparable<Direction>{
	private Arch source;
	private Arch sink;
	private Double weight;
	private LinkedList<Edge<Arch,Double>> routes;

	/**
	 * Construct a new Direction object.
	 *
	 * @param Arch source the starting construction
	 * @param Arch sink the ending construction
	 * @requires none
	 * @modifies none
	 * @effects construct a new Direction object
	 * @throws none
	 * @return none
	 */
	public Direction(Arch source, Arch sink){
		this.source = source;
		this.sink = sink; 
		this.weight = 0.0d;
		this.routes = new LinkedList<Edge<Arch,Double>>();
	}
	
	/**
	 * Copy and construct the given Direction object.
	 *
	 * @param DIrection other will be copied
	 * @requires none
	 * @modifies none
	 * @effects copy and construct the given Direction object
	 * @throws none
	 * @return none
	 */
	public Direction(Direction other){
		this.source = other.source;
		this.sink = other.sink;
		this.weight = other.weight; 
		this.routes = new LinkedList<Edge<Arch,Double>>(other.routes);
	}
	
	/**
	 * Get the ending construction.
	 *
	 * @param none
	 * @requires none
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return the the ending construction
	 */
	public Arch get_sink() {
		return this.sink;
	}
	
	/**
	 * Get the total weight of the path.
	 *
	 * @param none
	 * @requires none
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return the total weight of the path
	 */
	public Double get_weight() {
		return this.weight;
	}
	
	/**
	 * Get the collection of the routes that the path contains.
	 *
	 * @param none
	 * @requires none
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return the collection of the routes that the path contains
	 */
	public LinkedList<Edge<Arch,Double>> get_routes() {
		return this.routes;
	}
	
	/**
	 * add the given route into the current path.
	 *
	 * @param Edge route will be inserted into the path
	 * @requires none
	 * @modifies this.routes
	 * @modifies this.sink
	 * @modifies this.weight
	 * @effects add the given route into this,routes, change this, 
	 * 			sink to the children, add the given route's weight to total weight
	 * @throws none
	 * @return none
	 */
	public void add_route(Edge<Arch, Double> route) {
		this.sink = route.getChildren();
		this.weight += route.getLabel();
		this.routes.add(route);
	}

	/**
	 * Compare the 2 Direction objects
	 *
	 * @param Arch other the object will be compared
	 * @requires others != null
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return 0 if this == other; < 0 if this < other; > 0 if this > other
	 */
	@Override
	public int compareTo(Direction other) { 
		return this.weight.compareTo(other.weight);
	}
}