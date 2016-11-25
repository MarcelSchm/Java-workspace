package lab3.geoPosition;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawWithMouse extends MouseAdapter{
	private MyDrawPanel panel;

	public DrawWithMouse() {
		panel = new MyDrawPanel();
		panel.addMouseListener(this);
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if(1 == event.getButton()){
			System.out.printf("(x,y) = (%d, %d)\n", event.getX(), event.getY());
			panel.addPoint(event.getX(), event.getY());
		}
	}
	

}
