package hw8;

import hw7.Arch;
import hw7.Campus;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

public class View extends JPanel {
	private static final long serialVersionUID = 1L;
	
	final int horizontal_boundary = 60, vertical_boundary = 70,  width = 1024, height = 768;
	int tmp1 = 20, hz_source = 0, vt_source = 0, hz_sink = 0, vt_sink = 0, hz_margin, vt_margin, hz_ceiling, vt_ceiling;
	String tmp2 = null;
	Dimension element;
	Campus RPI;
	BufferedImage environment;
	Vector<Line2D> tie;
	boolean preffered_1 = false, preffered_2 = false;
	
	public View(Campus other){
		this.element = new Dimension(getWidth(), getHeight());
		this.RPI = other;
		BufferedImage fullImage = new BufferedImage(1,1,1);
		try {
			fullImage = ImageIO.read(new File("hw8/data/RPI_campus_map_2010_extra_nodes_edges.png"));
		} catch(IOException e) {}
		this.environment = fullImage.getSubimage(horizontal_boundary, vertical_boundary, width, height);
		reset_graph();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D concrete = (Graphics2D) g;
		concrete.drawImage(this.environment, 0, 0, getWidth(), getHeight(), this.hz_margin, this.vt_margin, this.hz_ceiling, this.vt_ceiling, null);
		int blog = 9;
		BasicStroke tmp3 = new BasicStroke(2f); 
		concrete.setStroke(tmp3);
		for(Line2D line : this.tie){
			concrete.setColor(Color.yellow);
			concrete.drawLine(hz_extent(line.getX1()), vt_extent(line.getY1()), hz_extent(line.getX2()), vt_extent(line.getY2()));
			int digit = 2*blog/3;
			Ellipse2D mark = new Ellipse2D.Double(hz_extent(line.getX1())-digit/2, vt_extent(line.getY1())-digit/2, digit, digit);
			Ellipse2D speck = new Ellipse2D.Double(hz_extent(line.getX2())-digit/2, vt_extent(line.getY2())-digit/2, digit, digit);
			concrete.fill(mark);
			concrete.fill(speck);
		}
		if(this.preffered_1) {
			Ellipse2D dot = new Ellipse2D.Double(hz_extent(hz_source)-blog/2, vt_extent(vt_source)-blog/2, blog, blog);
			concrete.setColor(Color.red);
			concrete.fill(dot);
			concrete.setColor(Color.blue);
			concrete.draw(dot);
		} else if(this.preffered_2) {
			Ellipse2D dot = new Ellipse2D.Double(hz_extent(hz_sink)-blog/2, vt_extent(vt_sink)-blog/2, blog, blog);
			concrete.setColor(Color.black);
			concrete.fill(dot);
			concrete.setColor(Color.blue);
			concrete.draw(dot);
		}
	}
	
	private int hz_extent(double origX){
		return (int)Math.round((origX-this.horizontal_boundary)*((double)getWidth()/this.width));
	}
	private int vt_extent(double origY){
		return (int)Math.round((origY-this.vertical_boundary)*((double)getHeight()/this.height));
	}
	
	public void reset_graph(){
		this.preffered_1 = false;
		this.preffered_2 = false;
		this.hz_source = -1;
		this.vt_source = -1;
		this.hz_sink = -1;
		this.vt_sink = -1;
		this.tie = new Vector<Line2D>();
		reset_path();
		setPreferredSize(this.element);
		repaint();
	}
	
	private void reset_path(){
		this.tie.clear();
		this.hz_margin = 0;
		this.vt_margin = 0;
		this.hz_ceiling = width; 
		this.vt_ceiling = height;
	}
	
	public void amending(String startend, String buildingName){
		reset_path();
		String cstrn = this.RPI.get_constructions();
		Arch construction = new Arch(cstrn, tmp2, 0.0d , 0.0d);
		if(startend.equals("begin")){ 
			this.hz_source = (int)Math.round(construction.get_x());
			this.vt_source = (int)Math.round(construction.get_y());
			this.preffered_1 = true;
		}
		if(startend.equals("final")){ 
			this.hz_sink = (int)Math.round(construction.get_x());
			this.vt_sink = (int)Math.round(construction.get_y());
			this.preffered_2 = true;
		}
		repaint();
	}

	public void reset_all(String wholeroute){
		if (wholeroute.equals("begin")) { 
			this.preffered_1 = false; 
		} else if (wholeroute.equals("final")) { 
			this.preffered_2 = false; 
		}
		repaint();
	}
}