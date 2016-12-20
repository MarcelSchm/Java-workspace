package lab4.geoPosition;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lab1.geoPosition.GeoPosition;
import lab2.geoPosition.GeoRoute;
import lab4.geoPosition.MyDrawPanel;

public class MyGUI extends MouseAdapter implements ActionListener{

	private MyDrawPanel panel;
	private static JFrame frame;
	private JMenu menuOptions;
	private JMenuBar menuBar;
	private JMenuItem deleteRoutepoints;
	private double distance,lat,lon;
	private GeoRoute route,virtualRoute;
	private JPanel labelPanel;
	private int mouseX,mouseY;
	private Container contentPane;
	private JLabel locationInformation,routeDistanceInformation,virtualRouteDistanceInformation;
	private boolean isDragged,isReleased;

	public MyGUI() {

		distance = 0;
		route = new GeoRoute("Route");
		virtualRoute = new GeoRoute("Virtuelle Route");

		//create Frame and set properties	
		frame = new JFrame("Lab4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1550, 800);
		frame.setLocation(20, 20);
		lat = 0.0;
		lon = 0.0;

		// Create menubar and add to frame
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		menuOptions = new JMenu("Optionen");
		deleteRoutepoints = new JMenuItem("Alle Routenpunkte  löschen");
		menuBar.add(menuOptions);
		menuOptions.add(deleteRoutepoints);

		//create label
		routeDistanceInformation = new JLabel("vorgegebene Strecke: " + String.format("%2.3f", distance) +" km");
		virtualRouteDistanceInformation = new JLabel("Jetzige Entfernung: " + String.format("%2.3f", distance) +" km");
		locationInformation = new JLabel("Position:(lat , lon) = (" + String.format("%07.4f", lat) + " , " + String.format("%07.4f", lon) + ") ");

		//create Panel
		panel = new MyDrawPanel();
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.add(routeDistanceInformation);
		labelPanel.add(virtualRouteDistanceInformation);
		labelPanel.add(locationInformation);

		//Event handling (button handling in addButton)
		menuOptions.addActionListener(this);
		deleteRoutepoints.addActionListener(this);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);


		// create Contents
		contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.add(panel); // add picture
		contentPane.add(labelPanel);

		//readRoute
		try {
			readRoute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	public void readRoute() throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("Route.txt")));
		while(bufferedReader.ready()){
			String[] coordinatesOfFile = new String[2];
			coordinatesOfFile = bufferedReader.readLine().split(",");
			//System.out.println(coordinatesOfFile[0] + "," + coordinatesOfFile[1]);
			lat = map(Integer.parseInt(coordinatesOfFile[0]), 0, 768,53.5566389, 53.5631389);
			lon = map(Integer.parseInt(coordinatesOfFile[1]), 0, 512,10.008555555555555,10.025);
			route.addWaypoint(new GeoPosition(lat, lon)); 

			distance = route.getDistance();
			routeDistanceInformation.setText("Entfernung: " + String.format("%2.3f", distance) +" km");
			frame.pack(); //rearrange Size
			frame.setVisible(true); //make visible

			panel.addRoutePoint(Integer.parseInt(coordinatesOfFile[0]), Integer.parseInt(coordinatesOfFile[1]));


		}
		bufferedReader.close();
	}

	public double map(double x, double inMin, double inMax, double outMin, double outMax){ //map a range of values to another range of values

		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		// TODO Auto-generated method stub
		isDragged=true;
		super.mouseDragged(event);
		if (isReleased == false) {
			if ((event.getX() >= 0 && event.getX() <= 768) && (event.getY() >= 0 && event.getY() <= 512)) {
				lat = map(event.getX(), 0, 768, 53.5566389, 53.5631389);
				lon = map(event.getY(), 0, 512, 10.008555555555555, 10.025);
				virtualRoute.addWaypoint(new GeoPosition(lat, lon)); //TODO
				distance = virtualRoute.getDistance();//TODO
				locationInformation.setText("Position:(lat , lon) = (" + String.format("%06.4f", lat) + " , "
						+ String.format("%06.4f", lon) + ")");
				virtualRouteDistanceInformation
				.setText("Jetzige Entfernung: " + String.format("%2.3f", distance) + " km");
				frame.pack(); //rearrange Size
				frame.setVisible(true); //make visible
				panel.addVirtualRoutePoint(event.getX(), event.getY());
				panel.setPassedWaypoints(route.passedWaypoints(virtualRoute));
			} else {
				JOptionPane.showMessageDialog(frame, "Sie laufen außerhalb der Map. Bitte innerhalb bleiben",
						"Bereichsüberschreitung", JOptionPane.INFORMATION_MESSAGE);
				isReleased = true;
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Sie müssen erst die vorhandene Route löschen, um eine neue zu starten", "Neue Route nicht möglich", JOptionPane.ERROR_MESSAGE );
		}


	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseReleased(e);
		isReleased = true;
		if(isDragged == true){
			JOptionPane.showMessageDialog(frame, "Sie haben die Route beendet. Ihre gelaufene Strecke beträgt: " +
					String.format("%2.3f", distance) + " km. \n Bitte löschen Sie die Route, bevor Sie eine neue Starten",
					"Route beendet", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		super.mouseMoved(event);
		lat = map(event.getX(), 0, 768,53.5566389, 53.5631389);
		lon = map(event.getY(), 0, 512,10.008555555555555,10.025);
		locationInformation.setText("Position:(lat , lon) = (" + String.format("%06.4f", lat) + " , " + String.format("%06.4f", lon) + ")");
		frame.pack(); //rearrange Size
		frame.setVisible(true); //make visible

	}

	public void updateRoute(){
		panel.clearRouteAll();
		for(int i = 0;i < route.getNumberWaypoints();i++){
			lat = route.getWaypoint(i).getLatitude();
			lon = route.getWaypoint(i).getLongitude();
			mouseX = (int) map(lat,53.5566389, 53.5631389, 0, 768);
			mouseY = (int) map(lon,10.008555555555555,10.025, 0, 512);
			panel.addRoutePoint(mouseX, mouseY);
		}
		distance = route.getDistance();
		routeDistanceInformation.setText("Entfernung: " + String.format("%2.3f", distance) +" km");
		frame.pack(); //rearrange Size
		frame.setVisible(true); //make visible
	}

	@Override
	public void actionPerformed(ActionEvent event) {


		if(event.getSource() == deleteRoutepoints){
			panel.clearVirtualRouteAll();
			int i = 0; //counter 
			while(i < virtualRoute.getNumberWaypoints()){
				virtualRoute.removeWaypoint(i);
			}
			panel.clearPassedWaypoints();
			updateRoute();
			isDragged = false;
			isReleased = false;
		}
	}

	public static void main(String[] args) {
		new MyGUI();
	}

}
