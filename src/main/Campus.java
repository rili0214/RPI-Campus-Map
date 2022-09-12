package hw7;

import hw4.Graph;
import hw4.Edge;
import java.io.*;
import java.util.*;

public class Campus {
	private Graph<Arch, Double> campus;
	private Map<String, Arch> costructions;
	private Map<String, String> number;

	/**
	 * Construct a new Campus object.
	 *
	 * @param none
	 * @requires none
	 * @modifies none
	 * @effects construct a new Campus object
	 * @throws none
	 * @return none
	 */
	public Campus(){
		this.campus = new Graph<Arch, Double>();
		this.costructions = new HashMap<String, Arch>();
		this.number = new HashMap<String, String>();
	}

	/**
	 * create a new Campus
	 *
	 * @param String file1 where the campus constructions data stored
	 * @param String file2 where the campus roads data stored
	 * @requires none
	 * @modifies this.campus
	 * @modifies this.constructions
	 * @modifies this.number
	 * @effects create a new Campus from the given files
	 * @throws IOException
	 * @return none
	 */
	public void createNewCampus(String file1, String file2) {
		clear();
		try{
			Set<Arch> container1 = new TreeSet<Arch>();
			CampusParser.readNodeData(file1, container1);
			Iterator<Arch> itr = container1.iterator();
			while (itr.hasNext()) {
				Arch tmp = itr.next();
				this.campus.addNode(tmp);
				this.costructions.put(tmp.get_number(), tmp);
				this.number.put(tmp.get_label(), tmp.get_number());
			}
			List<List<String>> container2 = new LinkedList<List<String>>();
			CampusParser.readEdgeData(file2, container2);
			Iterator<List<String>> it = container2.iterator();
			while (it.hasNext()) {
				List<String> tmp = it.next();
				Arch CHAR1 = this.costructions.get(tmp.get(0));
				Arch CHAR2 = this.costructions.get(tmp.get(1));
				Double x1 = CHAR1.get_x();
				Double y1 = CHAR1.get_y();
				Double x2 = CHAR2.get_x();
				Double y2 = CHAR2.get_y();
				Double dist = Math.pow(Math.pow(x2-x1,2)+Math.pow(y2-y1,2), 0.5);
				Edge<Arch, Double> edge1 = new Edge<Arch, Double>(CHAR1, CHAR2, dist);
				Edge<Arch, Double> edge2 = new Edge<Arch, Double>(CHAR2, CHAR1, dist);
				this.campus.addEdge(edge1);
				this.campus.addEdge(edge2);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Find the shortest path between two given constructions.
	 *
	 * @param String CHAR1 the starting construction
	 * @param String CHAR2 the ending construction
	 * @requires none
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return a string containing the shortest path and the directions
	 */
	public String findShortestPath(String CHAR1, String CHAR2) {
		Arch source, sink;
		String statement = "";
		try {
			Integer.parseInt(CHAR1);
			source = this.costructions.get(CHAR1);
		} catch (Exception e) {
			source = this.costructions.get(this.number.get(CHAR1));
		}
		try {
			Integer.parseInt(CHAR2);
			sink = this.costructions.get(CHAR2);
		} catch (Exception e) {
			sink = this.costructions.get(this.number.get(CHAR2));
		}
		if ((source == null && sink == null && CHAR1.equals(CHAR2)) || 
				(source.get_label().equals("") && sink.get_label().equals("") && CHAR1.equals(CHAR2))) {
			return "Unknown building: [" + CHAR1 + "]\n";
		} else if ((source == null && sink == null) ||(source.get_label().equals("") && sink.get_label().equals(""))) {
			return "Unknown building: [" + CHAR1 + "]\n" + "Unknown building: [" + CHAR2 + "]\n";
		} else if ((source == null && sink != null) || (source.get_label().equals("") && !sink.get_label().equals(""))) {
			return "Unknown building: [" + CHAR1 + "]\n";
		} else if ((source != null && sink == null) || (!source.get_label().equals("") && sink.get_label().equals(""))) {
			return "Unknown building: [" + CHAR2 + "]\n";
		}
		
		PriorityQueue<Direction> queue = new PriorityQueue<Direction>();
		Map<Arch, Direction> remaining = new HashMap<Arch, Direction>();
		Direction shortest = new Direction(source, source);
		queue.add(shortest);
		while (queue.size() != 0) {
			shortest = queue.poll();
			Arch following = shortest.get_sink();
			if (following.equals(sink)) {
				statement += "Path from " + source.get_label() + " to " + sink.get_label() + ":\n";
				Iterator<Edge<Arch, Double>> itr = shortest.get_routes().iterator();
				while (itr.hasNext()) {
					Edge<Arch, Double> current_edge = itr.next();
					if (!current_edge.getChildren().get_label().equals("")) {
						statement += "\tWalk " + towards_to(current_edge) + " to (" + current_edge.getChildren().get_label() + ")\n";
					} else {
						statement += "\tWalk " + towards_to(current_edge) + " to (Intersection " + current_edge.getChildren().get_number() + ")\n";
					}
				}
				return statement + String.format("Total distance: %.3f pixel units.\n", shortest.get_weight());
			} else if (remaining.containsKey(following)) {
				continue;
			}
			Iterator<Edge<Arch, Double>> itr1 = this.campus.sortednext(following).iterator(); 
			while (itr1.hasNext()) {
				Edge<Arch, Double> current_edge = itr1.next();
				if(remaining.containsKey(current_edge.getParents())) {
					continue;
				} else if(remaining.containsKey(current_edge.getChildren())) {
					continue;
				}
				Direction post = new Direction(shortest);
				post.add_route(current_edge);
				queue.add(post);
			}
			remaining.put(following, shortest);
		}
		return statement + "There is no path from " + source.get_label() + " to " + sink.get_label() + ".\n";
	}
	
	/** 
	 * Get all the constructions on campus.
	 *
	 * @param none
	 * @requires none
	 * @modifies none
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return a String contains the constructions on campus.
	 */
	public String get_constructions() {
		String str = "";
		TreeSet<Arch> cstrns = new TreeSet<Arch>(this.campus.getNodes());
		Iterator<Arch> itr = cstrns.iterator();
		while(itr.hasNext()) {
			Arch current = itr.next();
			if (current.get_label().equals("")) {
				continue;
			} else {
				str += current.get_label() + "," + current.get_number() + "\n";
			}
		}
		return str;
	}

	/** 
	 * Get the direction should towards to.
	 *
	 * @param Edge route that will be given direction
	 * @requires none
	 * @modifies none
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return a String the given route will be directed to
	 */
	public String towards_to(Edge<Arch, Double> route) {
		Arch source = route.getParents();
		Arch sink = route.getChildren();
		Double x1 = source.get_x();
		Double y1 = source.get_y();
		Double x2 = sink.get_x();
		Double y2 = sink.get_y();
		Double angle = Math.toDegrees(Math.atan2(x2-x1, y2-y1));
		if (157.5 <= angle || angle < -157.5){
			return "North";
		} else if (-157.5 <= angle && angle < -112.5) {
			return "NorthWest";
		} else if (-112.5 <= angle && angle < -67.5) {
			return "West";
		} else if (-67.5 <= angle && angle < -22.5) {
			return "SouthWest";
		} else if (-22.5 <= angle && angle < 22.5) {
			return "South";
		} else if (22.5 <= angle && angle < 67.5) {
			return "SouthEast"; 
		} else if (67.5 <= angle && angle < 112.5) {
			return "East";
		} else {
			return "NorthEast";
		}
	}
	
	/**
	 * remove the whole Campus object
	 *
	 * @param none
	 * @requires none
	 * @modifies campus
	 * @modifies constructions
	 * @modifies number
	 * @effects remove the whole Campus object
	 * @throws none
	 * @return none
	 */
	public void clear(){
		this.campus.clear();
		this.costructions.clear();
		this.number.clear();
	}
}