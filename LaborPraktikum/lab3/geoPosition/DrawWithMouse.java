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
	private JMenuItem newRoute,deleteRoutepoints,deleteRoute;
	private double distance,lat,lon;;
	private GeoRoute[] route;
	private JPanel buttonPanel;
	private int buttonRouteNumber,routeNumber,mouseX,mouseY;
	private JButton[] buttonArray = new JButton[10];
	private Container contentPane;
	JLabel locationInformation,distanceInformation;

	public DrawWithMouse() {
		buttonRouteNumber = 0;
		routeNumber = 0;
		distance = 0;
		route = new GeoRoute[10];

		for(int i = 0; i<10;i++){
			route[i] = new GeoRoute("route " + i);
		}

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
		deleteRoutepoints = new JMenuItem("Alle Routenpunkte  löschen");
		deleteRoute = new JMenuItem("Ausgewählte Route entfernen");
		menuBar.add(menuOptions);
		menuOptions.add(newRoute);
		menuOptions.add(deleteRoutepoints);
		menuOptions.addSeparator();
		menuOptions.add(deleteRoute);

		//create label
		distanceInformation = new JLabel("Entfernung: " + String.format("%2.3f", distance) +" km");
		locationInformation = new JLabel("Position:(lat , lon) = (" + String.format("%06.3f", lat) + " , " + String.format("%06.3f", lon) + ") ");
		
		//create DrawPanel
		panel = new MyDrawPanel();
		
		//create ButtonPanel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(distanceInformation);
		buttonPanel.add(locationInformation);
		addButton();

		//Event handling (button handling in addButton)
		menuOptions.addActionListener(this);
		newRoute.addActionListener(this);
		deleteRoutepoints.addActionListener(this);
		deleteRoute.addActionListener(this);
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
		if(buttonRouteNumber <= 9){
			buttonArray[buttonRouteNumber] = new JButton("Route " +String.format("%02d",(buttonRouteNumber+1)));
			buttonPanel.add(buttonArray[buttonRouteNumber]);
			routeNumber = buttonRouteNumber;
			updateRoute();
			
			//Event handling
			buttonArray[buttonRouteNumber].addActionListener(this); //TODO
			buttonArray[buttonRouteNumber].setActionCommand("Button " + buttonRouteNumber);
			frame.setVisible(true); //make visible
			buttonRouteNumber++;
		}
		else{
			JOptionPane.showMessageDialog(frame, "Es können nicht mehr als 10 Routen hinzugefügt werden", "Routen Maximum erreicht", JOptionPane.WARNING_MESSAGE);
		}
	}

	public double map(double x, double inMin, double inMax, double outMin, double outMax){ //map a range of values to another range of values

		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if(1 == event.getButton()){
			System.out.printf("(x,y) = (%d, %d)\n", event.getX(), event.getY());
			lat = map(event.getX(), 0, 768, 53.3325, 54.5720556);
			lon = map(event.getY(), 0, 1024, 8.4375, 11.24725);
			locationInformation.setText("Position:(lat , lon) = (" + String.format("%06.3f", lat) + " , " + String.format("%06.3f", lon) + ")");
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
		locationInformation.setText("Position:(lat , lon) = (" + String.format("%06.3f", lat) + " , " + String.format("%06.3f", lon) + ")");
		frame.pack(); //rearrange Size
		frame.setVisible(true); //make visible

	}

	public static void main(String[] args) {
		new DrawWithMouse();
	}

	public void updateRoute(){
		panel.clearAll();
		for(int i = 0;i < route[routeNumber].getNumberWaypoints();i++){
			lat = route[routeNumber].getWaypoint(i).getLatitude();
			lon = route[routeNumber].getWaypoint(i).getLongitude();
			mouseX = (int) map(lat, 53.3325, 54.5720556, 0, 768);
			mouseY = (int) map(lon, 8.4375, 11.24725, 0, 1024);
			panel.addPoint(mouseX, mouseY);
		}
		distance = route[routeNumber].getDistance();
		distanceInformation.setText("Entfernung: " + String.format("%2.3f", distance) +" km");
		frame.pack(); //rearrange Size
		frame.setVisible(true); //make visible
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		//System.out.println("action");
		switch(event.getActionCommand()){
		case "Button 0": routeNumber = 0;
		updateRoute();
		break;
		case "Button 1": routeNumber = 1;
		updateRoute();
		break;
		case "Button 2": routeNumber = 2;
		updateRoute();
		break;
		case "Button 3": routeNumber = 3;
		updateRoute();
		break;
		case "Button 4": routeNumber = 4;
		updateRoute();
		break;
		case "Button 5": routeNumber = 5;
		updateRoute();
		break;
		case "Button 6": routeNumber = 6;
		updateRoute();
		break;
		case "Button 7": routeNumber = 7;
		updateRoute();
		break;
		case "Button 8": routeNumber = 8;
		updateRoute();
		break;
		case "Button 9": routeNumber = 9;
		updateRoute();
		break;
		}

		if(event.getSource() == newRoute){
			addButton();	
		}

		if(event.getSource() == deleteRoutepoints){
			panel.clearAll();
			int i = 0; //counter 
			while(i < route[routeNumber].getNumberWaypoints()){
				route[routeNumber].removeWaypoint(i);
			}
			updateRoute();
		}
		if(event.getSource() == deleteRoute){
			System.out.println("LÖSCHEN!!!");
			int i = 0;
			while(i < route[routeNumber].getNumberWaypoints()){
				route[routeNumber].removeWaypoint(i);
			}
			updateRoute();
			buttonArray[routeNumber].remove(buttonArray[routeNumber]);
			frame.pack(); //rearrange Size
			frame.setVisible(true); //make visible
			
		}
	}

}
