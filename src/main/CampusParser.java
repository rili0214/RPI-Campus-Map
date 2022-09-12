package hw7;

import java.util.*;
import java.io.*;

public class CampusParser {
	
	/**
	 * @param: filename     		The path to the file that contains the RPI campus data
	 * @param: constructions       	The collection that stores the constructions on RPi 
	 * 								campus
	 * @requires: filename != null && constructions != null
	 * @modifies: constructions
	 * @effects: add Arch objects into constructions
	 * @throws: IOException 		if file cannot be read of file not a CSV file following
	 *                      		the proper format.
	 * @returns: None
	 */
	public static void readNodeData(String filename, Set<Arch> constructions) throws IOException {
		BufferedReader open_file = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = open_file.readLine()) != null) {
			int i = line.indexOf("\",\"");
			if ((i != -1) || (line.charAt(0) == '\"') || (line.charAt(line.length() - 1) == '\"')) {
				throw new IOException("File " + filename + " not a CSV (\"CHARACTER\",\"BOOK\") file.");
			}
			String[] tmp = line.split(",");
			Arch construction = new Arch(tmp[0], tmp[1], Double.parseDouble(tmp[2]), Double.parseDouble(tmp[3]));
			constructions.add(construction);
		}
		open_file.close();
	}
	
	/**
	 * @param: filename     		The path to the file that contains the RPI campus data
	 * @param: constructions       	The collection that stores the the roads on RPi campus
	 * @requires: filename != null && roads != null
	 * @modifies: roads
	 * @effects: add all the String objects into roads
	 * @throws: IOException 		if file cannot be read of file not a CSV file following
	 *                      		the proper format.
	 * @returns: None
	 */
	public static void readEdgeData(String filename,  List<List<String>> roads) throws IOException {
		BufferedReader open_file = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = open_file.readLine()) != null) {
			int i = line.indexOf("\",\"");
			if ((i != -1) || (line.charAt(0) == '\"') || (line.charAt(line.length() - 1) == '\"')) {
				throw new IOException("File " + filename + " not a CSV (\"CHARACTER\",\"BOOK\") file.");
			}
			String[] arrangement = line.split(",");
			List<String> tmp = new LinkedList<String>();
			tmp.add(arrangement[0]);
			tmp.add(arrangement[1]);
			roads.add(tmp);
		}
		open_file.close();
	}
}