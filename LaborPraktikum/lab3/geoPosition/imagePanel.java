package lab3.geoPosition;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
 class imagePanel extends JPanel{
	
	public Dimension getPreferredSize() {
		return new Dimension(1024, 768);
	}
	
@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		try {
			BufferedImage image = ImageIO.read(new File("C:/Users/Marcel/Dropbox/Scripte/7. Semester/Java/workspace/Praktikum/lab3/geoPosition/OSM_Map.png"));
			g.drawImage(image, 0, 0, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
