package hw8;

import hw7.Campus;

import java.awt.*;
import javax.swing.*;

/**
 * This Program works as a GUI for the RPI campus tour. It support the following features:
 *  	1. Load the map 
 *  	2. Display the map of RPI campus
 *  	3. Allow the user to select two buildings for finding a route
 *  	4. Highlight the selected buildings and/or path endpoints on the map
 *  	5. Draw the shortest route between the selected buildings on the map
 *  	6. As the window is resized, make the map shrink or grow to fit the window
 *  	7. Maintain the proportions of the map
 *  	8. Place the map in a ScrollPane so it can be displayed full-size.
 *  	9. Allow the user to drag the map with the mouse to change the portion that is shown
 *  	10. Add zoom buttons, possibly with a way to recenter the image for zooming if a hand 
 *  		is not available to drag it
 *  	11. Allow the user to reset the GUI by clicking a reset button
 *  	12. Operate robustly
 *		
 */
public class RPICampusPathsMain {
	
	public static void main(String[] args){
		Campus RPI = new Campus();
		RPI.createNewCampus("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");
		
		JFrame window = new JFrame("Welcome to RPI Campus");
		window.setSize(1024, 768);
		
		JPanel graph = new View(RPI);
		graph.setPreferredSize(new Dimension(400, 400));
		JScrollPane scroll = new JScrollPane(graph);
		scroll.setPreferredSize(new Dimension(400, 400));
		window.getContentPane().add(scroll, BorderLayout.CENTER);
		
		JPanel loading = new Controller(RPI, (View)graph);
		loading.setPreferredSize(new Dimension(600, 100));
		window.getContentPane().add(loading, BorderLayout.SOUTH);
		
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}