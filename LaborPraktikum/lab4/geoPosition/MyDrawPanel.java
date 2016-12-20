package lab4.geoPosition;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyDrawPanel extends JPanel {

	private ArrayList<Integer> waypointXRoute = new ArrayList<Integer>();
	private ArrayList<Integer> waypointYRoute = new ArrayList<Integer>();
	private ArrayList<Integer> waypointXVirtualRoute = new ArrayList<Integer>();
	private ArrayList<Integer> waypointYVirtualRoute = new ArrayList<Integer>();
	private ArrayList<Boolean> passedWaypoints= new ArrayList<Boolean>();
	public MyDrawPanel() {
	}

	public Dimension getPreferredSize() {
		return new Dimension(768, 512);
	}

	public void addRoutePoint(int x, int y) {
		waypointXRoute.add(x);
		waypointYRoute.add(y);
		repaint();

	}
	public void addVirtualRoutePoint(int x, int y) {
		waypointXVirtualRoute.add(x);
		waypointYVirtualRoute.add(y);
		repaint();

	}

	public void clearRouteAll() {
		if(waypointXRoute.isEmpty()==false){
			waypointXRoute.clear();
			waypointYRoute.clear();
			repaint();
		}
	}
	public void clearVirtualRouteAll() {
		if(waypointXVirtualRoute.isEmpty()==false){
			waypointXVirtualRoute.clear();
			waypointYVirtualRoute.clear();
			repaint();
		}
	}

	public void removeLast(){
		waypointXRoute.remove(waypointXRoute.size() - 1);
		waypointYRoute.remove(waypointYRoute.size() - 1);
		//System.out.println("waypoint Größe" + waypointX.size());
		repaint();
	}
	public void clearPassedWaypoints(){
		if(passedWaypoints.isEmpty()==false){
		passedWaypoints.clear();
		}
		repaint();
	}
	
	public void setPassedWaypoints(boolean[] passedWaypointsArray) {
		clearPassedWaypoints();
		for( boolean element: passedWaypointsArray){
			passedWaypoints.add(element);
		}
		//System.out.println("wegpunkte: ");
		for(boolean element: passedWaypoints){
		//System.out.println(element);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			BufferedImage image = ImageIO.read(new File("OSM_BerlinerTor.png"));//relative to main folder
			g.drawImage(image, 0, 0, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawString("Start at Magenta Circle", 10, 20);
		g.drawString("Right mouse button to delete last", 10, 40);
		g.setColor(Color.GREEN);
		((Graphics2D) g).setStroke(new BasicStroke(3)); //thickness of lines
		int numberRoutePoints = waypointXRoute.size();
		if(numberRoutePoints > 0){
			g.setColor(Color.MAGENTA);
			g.fillOval(waypointXRoute.get(0) - 8, waypointYRoute.get(0) -8, 16, 16);
			((Graphics2D) g).setStroke(new BasicStroke(2)); //thickness of lines
			g.setColor(Color.BLACK);
			g.drawOval(waypointXRoute.get(0) - 8, waypointYRoute.get(0) -8, 16, 16);
			((Graphics2D) g).setStroke(new BasicStroke(3)); //thickness of lines
			
		}
		if (numberRoutePoints > 1) {
			for (int i = 1; i < numberRoutePoints; i++) {
				g.setColor(Color.red);
				g.drawLine(
						waypointXRoute.get(i - 1), 
						waypointYRoute.get(i - 1), 
						waypointXRoute.get(i), 
						waypointYRoute.get(i)
						);
				g.setColor(Color.ORANGE);
				g.drawOval(waypointXRoute.get(numberRoutePoints - 1) - 3, waypointYRoute.get(numberRoutePoints - 1) - 3, 6, 6);
			}
			//System.out.println("Tabelle leer?" + passedWaypoints.isEmpty());
			for (int j = 0;j <numberRoutePoints;j++){ //big red circles as waypoints
				if(!passedWaypoints.isEmpty()){
					if(passedWaypoints.get(j)){
						g.setColor(Color.GREEN);
					}
					else {
						g.setColor(Color.RED);
					}
				} else {
					if(j>0){ // exception for first circle
					g.setColor(Color.RED);
					}
				}

				g.fillOval(waypointXRoute.get(j) - 8, waypointYRoute.get(j) -8, 16, 16);
				((Graphics2D) g).setStroke(new BasicStroke(2)); //thickness of lines
				g.setColor(Color.BLACK);
				g.drawOval(waypointXRoute.get(j) - 8, waypointYRoute.get(j) -8, 16, 16);
				((Graphics2D) g).setStroke(new BasicStroke(3)); //thickness of lines
			}

		}
		int numberVirtualRoutePoints = waypointXVirtualRoute.size();
		if (numberVirtualRoutePoints > 1) {
			for (int i = 1; i < numberVirtualRoutePoints; i++) {
				g.setColor(Color.BLUE);
				g.drawLine(
						waypointXVirtualRoute.get(i - 1), 
						waypointYVirtualRoute.get(i - 1), 
						waypointXVirtualRoute.get(i), 
						waypointYVirtualRoute.get(i)
						);
			}
		}
	}


}










