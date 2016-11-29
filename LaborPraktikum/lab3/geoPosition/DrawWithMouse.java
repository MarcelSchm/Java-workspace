package lab3.geoPosition;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lab1.geoPosition.GeoPosition;
import lab2.geoPosition.GeoRoute;

public class DrawWithMouse extends MouseAdapter implements ActionListener{
	private MyDrawPanel panel;
	private static JFrame frame;
	private JMenu menuOptions;
	private JMenuBar menuBar;
	private JMenuItem newRoute,deleteRoute;
	private double distance;
	private GeoRoute[] route;
	private JPanel buttonPanel;
	private int buttonRouteNumber;
	private int routeNumber;
	private JButton[] buttonArray = new JButton[10];
	private Container contentPane;
	private double lat,lon;
	JLabel locationInformation;
	JLabel distanceInformation;

	public DrawWithMouse() {
		buttonRouteNumber = 0;
		routeNumber = 0;
		distance = 0;
		route = new GeoRoute[10];
		route[routeNumber] = new GeoRoute("route " + routeNumber); //TODO

		//create Frame and set properties	
		frame = new JFrame("Lab3");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1550, 800);
		frame.setLocation(20, 20);
		lat = 0.0;
		lon = 0.0;

		// Create menubar and add to frame
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		menuOptions = new JMenu("Optionen");
		newRoute = new JMenuItem("Route hinzufügen");
		deleteRoute = new JMenuItem("Route komplett löschen");
		menuBar.add(menuOptions);
		menuOptions.add(newRoute);
		menuOptions.add(deleteRoute);

		//create label
		distanceInformation = new JLabel("Entfernung: " + String.format("%2.3f", distance) +" km");
		locationInformation = new JLabel("Position:(lat , lon) = (" + String.format("%2.3f", lat) + " , " + String.format("%2.3f", lon) + ") ");

		//create ButtonPanel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(distanceInformation);
		buttonPanel.add(locationInformation);
		addButton();

		//Event handling
		menuOptions.addActionListener(this);
		buttonArray[0].addActionListener(this); //TODO
		newRoute.addActionListener(this);
		deleteRoute.addActionListener(this);
		panel = new MyDrawPanel();
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		
		
		// create Contents
		contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.add(panel); // add picture
		contentPane.add(buttonPanel); //add button


		//frame.add(panel);
		frame.pack();
		frame.setVisible(true);

	}

	public void addButton(){
		//System.out.println("addButton");
		buttonArray[buttonRouteNumber] = new JButton("Route " +(buttonRouteNumber+1));
		buttonPanel.add(buttonArray[buttonRouteNumber]);
		frame.setVisible(true); //make visible
		buttonRouteNumber++;

	}

	public double map(int x, int inMin, int inMax, double outMin, double outMax){ //map a range of values to another range of values

		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		if(1 == event.getButton()){
			System.out.printf("(x,y) = (%d, %d)\n", event.getX(), event.getY());
			lat = map(event.getX(), 0, 768, 53.3325, 54.5720556);
			lon = map(event.getY(), 0, 1024, 8.4375, 11.24725);
			locationInformation.setText("Position:(lat , lon) = (" + String.format("%2.3f", lat) + " , " + String.format("%2.3f", lon) + ")");
			route[routeNumber].addWaypoint(new GeoPosition(lat, lon)); //TODO
			distance = route[routeNumber].getDistance();//TODO
			distanceInformation.setText("Entfernung: " + String.format("%2.3f", distance) +" km");
			frame.pack(); //rearrange Size
			frame.setVisible(true); //make visible

			panel.addPoint(event.getX(), event.getY());
		}else if (event.getButton() == 3) {
			System.out.println("delete last");
			if(route[routeNumber].getNumberWaypoints() > 0){//TODO
				if(route[routeNumber].getNumberWaypoints() >= 2){//if less no distance to calculate
					distance -= GeoPosition.distanceInKm(route[routeNumber].getWaypoint(route[routeNumber].getNumberWaypoints() - 2), route[routeNumber].getWaypoint(route[routeNumber].getNumberWaypoints() - 1));
				} else{
					distance = 0;// only 1 Waypoint => no distance;	
				}
				route[routeNumber].removeWaypoint(route[routeNumber].getNumberWaypoints() - 1);//TODO
				distanceInformation.setText("Entfernung: " + String.format("%2.3f", distance) +" km");
				frame.pack(); //rearrange Size
				frame.setVisible(true); //make visible
				panel.removeLast();
			} else{
				JOptionPane.showMessageDialog(frame, "Letzter Wegpunkt wurde bereits gelöscht. Bitte erst neuen Punkt eingeben", "Achtung", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		super.mouseMoved(event);
		lat = map(event.getX(), 0, 768, 53.3325, 54.5720556);
		lon = map(event.getY(), 0, 1024, 8.4375, 11.24725);
		locationInformation.setText("Position:(lat , lon) = (" + String.format("%2.3f", lat) + " , " + String.format("%2.3f", lon) + ")");
		
		
	}

	public static void main(String[] args) {
		new DrawWithMouse();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		//System.out.println("action");

		if(event.getSource()==buttonArray[0]){
			
		}
		if(event.getSource() == newRoute){
			addButton();	
		}
		
		if(event.getSource() == deleteRoute){
			panel.clearAll();
			distance = 0;
			int i = 0; //counter 
			while(i<route[routeNumber].getNumberWaypoints()){//TODO
				route[routeNumber].removeWaypoint(i);
			}
		}
	}
	
}
