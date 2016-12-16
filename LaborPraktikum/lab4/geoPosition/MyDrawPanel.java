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

	private ArrayList<Integer> waypointX = new ArrayList<Integer>();
	private ArrayList<Integer> waypointY = new ArrayList<Integer>();

	public MyDrawPanel() {
	}

	public Dimension getPreferredSize() {
		return new Dimension(768, 512);
	}

	public void addPoint(int x, int y) {
		waypointX.add(x);
		waypointY.add(y);
		repaint();

	}

	public void clearAll() {
		if(waypointX.isEmpty()==false){
			waypointX.clear();
			waypointY.clear();
			repaint();
		}
	}

	public void removeLast(){
		waypointX.remove(waypointX.size() - 1);
		waypointY.remove(waypointY.size() - 1);
		//System.out.println("waypoint Größe" + waypointX.size());
		repaint();
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
		g.drawString("Left mouse button to set new point", 10, 20);
		g.drawString("Right mouse button to delete last", 10, 40);
		g.setColor(Color.GREEN);
		((Graphics2D) g).setStroke(new BasicStroke(3)); //thickness of lines
		int numberPoints = waypointX.size();
		if(numberPoints > 0){
			g.setColor(Color.GREEN);
			g.drawOval(waypointX.get(0) - 3, waypointY.get(0) - 3, 6, 6);
		}
		if (numberPoints > 1) {
			for (int i = 1; i < numberPoints; i++) {
				g.setColor(Color.GREEN);
				g.drawLine(
						waypointX.get(i - 1), 
						waypointY.get(i - 1), 
						waypointX.get(i), 
						waypointY.get(i)
						);
				g.setColor(Color.ORANGE);
				g.drawOval(waypointX.get(numberPoints - 1) - 3, waypointY.get(numberPoints - 1) - 3, 6, 6);
			}
			for (int j = 1;j <numberPoints;j++){
				g.setColor(Color.RED);
				g.fillOval(waypointX.get(j) - 8, waypointY.get(j) -8, 16, 16);
				((Graphics2D) g).setStroke(new BasicStroke(2)); //thickness of lines
				g.setColor(Color.BLACK);
				g.drawOval(waypointX.get(j) - 8, waypointY.get(j) -8, 16, 16);
				((Graphics2D) g).setStroke(new BasicStroke(3)); //thickness of lines
			}

		}


	}
}

