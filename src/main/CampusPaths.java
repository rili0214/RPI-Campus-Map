package hw7;

import java.io.*;

/**
 *  This program is a route-finding tool. It takes the names or ids of two buildings on the RPI Troy Campus, 
 *  and generate directions for the shortest route between them, using the graph ADTs to represent buildings 
 *  and pathways between them. It contains several steps:
 * 		Step 1: Parsing the Data
 * 		Step 2: Set the model
 * 		Step 3: Controller and View
 * 
 *	The controller supports 4 methods:
 *		1. b lists all buildings
 *		2. r prints directions for the shortest route between any two buildings
 *		3. m prints a menu of all commands
 *		4. q quits the program
 */
public class CampusPaths {
	public static void main(String[] argv) {
		Campus RPI = new Campus();
		RPI.createNewCampus("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");
		BufferedReader openfile = new BufferedReader(new InputStreamReader(System.in));
		String chars = "";
		boolean next = true; 
		while (next) {
			try {
				chars = openfile.readLine();
			} catch (IOException e) {
				break;
			}
			if (chars.equals("b")) {
				System.out.print(RPI.get_constructions());
			} else if (chars.equals("r")) {
				System.out.print("First building id/name, followed by Enter: ");
				String start;
				try {
					start = openfile.readLine();
				} catch (IOException e) {
					break;
				}
				System.out.print("Second building id/name, followed by Enter: ");
				String end;
				try {
					end = openfile.readLine();
				} catch (IOException e) {
					break;
				}
				System.out.print(RPI.findShortestPath(start, end));
			} else if (chars.equals("q")) {
				next = false;
				break;
			} else if (chars.equals("m")) {
				System.out.println("b lists all buildings");
				System.out.println("r prints directions for the shortest route between any two buildings");
				System.out.println("q quits the program");
				System.out.println("m prints a menu of all commands");
			} else {
				System.out.println("Unknown option");
			}
		}
	}
}