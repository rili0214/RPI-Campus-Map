package hw8;

import hw7.Campus;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controller extends JPanel {
	private static final long serialVersionUID = 1L;
    String user_input = "Choose one building to start: ";
	View scenario;
	Vector<String> constructions;
	JComboBox<String> topbottom, bottomup;
	
	public Controller(Campus RPI, View graph){
		String construction = RPI.get_constructions();
		String[] newStr = construction.split(" ");
		for (int i = 0; i < newStr.length; i++) {
            constructions.add(newStr[i]);
        }
		scenario = graph;
		setLayout(new GridBagLayout());
		
		JLabel start_ = new JLabel("THE STARTING IS AT: ");
		GridBagConstraints eye = new GridBagConstraints();
		eye.gridx = 0; 
		eye.gridy = 0; 
		eye.anchor = GridBagConstraints.LINE_END;
		add(start_, eye);
		topbottom = new JComboBox<String>(constructions);
		topbottom.addActionListener(new newlistner("begin", topbottom));
		GridBagConstraints ear = new GridBagConstraints();
		ear.gridx = 2; 
		ear.gridy = 0;
		add(topbottom, ear);
		
		JLabel end_ = new JLabel("\nTHE ENDING IS AT: ");
		GridBagConstraints nose = new GridBagConstraints();
		nose.gridx = 0; 
		nose.gridy = 2; 
		nose.anchor = GridBagConstraints.LINE_END;
		add(end_, nose);
		bottomup = new JComboBox<String>(constructions);
		bottomup.addActionListener(new newlistner("final", bottomup));
		GridBagConstraints tongue = new GridBagConstraints();
		tongue.gridx = 2; 
		tongue.gridy = 2;
		add(bottomup, tongue);
		
		Button findButton = new Button("LOADING FOR THE PATH >>>>>>> ");
		GridBagConstraints body = new GridBagConstraints();
		body.gridx = 4; 
		body.gridy = 0; 
		body.gridheight = 4; 
		body.fill = GridBagConstraints.BOTH;
		add(findButton, body);
		
		Button resetButton = new Button("~~~RESET~~~");
		resetButton.addActionListener(
			new ActionListener() { 
				public void actionPerformed (ActionEvent e) { 
					reset(); 
				} 
			}
		);
		GridBagConstraints manas = new GridBagConstraints();
		manas.gridx = 6; 
		manas.gridy = 0; 
		manas.gridheight = 4; 
		manas.fill = GridBagConstraints.BOTH;
		add(resetButton, manas);
		
	}
	
	public void reset() {
		topbottom.setSelectedItem(user_input);
		bottomup.setSelectedItem(user_input);
		scenario.reset_graph();
	}
	
	private class newlistner implements ActionListener {
		JComboBox<String> action;
		final String bottom;
		public newlistner(String top, JComboBox<String> start) {
			this.bottom = top; 
			this.action = start;
		}
		public void actionPerformed(ActionEvent e){
			String tmp = (String) action.getSelectedItem();
			if(tmp.equals(user_input)) { 
				scenario.reset_all(bottom); 
			} else { 
				scenario.amending(bottom, tmp); 
			}
		}
	}
}