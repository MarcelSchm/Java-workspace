package lab3.geoPosition;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class MyDrawPanel extends JPanel {
	private ArrayList<Integer> waypointX = new ArrayList<Integer>();;
	private ArrayList<Integer> waypointY = new ArrayList<Integer>();;
	
	public MyDrawPanel() {
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(1024, 768);
	}

	public void addPoint(int x, int y) {
		waypointX.add(x);
		waypointY.add(y);
		repaint();
		
	}
	public void clear() {
		waypointX.clear();
		waypointY.clear();
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			BufferedImage image = ImageIO.read(new File("C:/Users/Marcel/Dropbox/Scripte/7. Semester/Java/workspace/Praktikum/lab3/geoPosition/OSM_Map.png"));
			g.drawImage(image, 0, 0, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawString("Left mouse button to set new point", 10, 20);
		g.drawString("Right mouse button to clear panel", 10, 40);

		int numberPoints = waypointX.size();
		if (numberPoints > 1) {
			for (int i = 1; i < numberPoints; i++) {
				g.drawLine(
					waypointX.get(i - 1), 
					waypointY.get(i - 1), 
					waypointX.get(i), 
					waypointY.get(i)
				);
			}
		}
	}
	public static void main(String[] args) {
		//new MyDrawPanel();
	}



}



