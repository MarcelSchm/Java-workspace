package lab3.geoPosition;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import lab1.geoPosition.GeoPosition;


@SuppressWarnings("serial")
public class MyDrawPanel extends JPanel implements ActionListener{
	private JPanel buttonPanel;
	private int routeNumber;
	private double distance;
	private GeoPosition route;
	private JMenu menuOptions;
	private JMenuItem newRoute,deleteRoute;
	private JButton[] buttonArray = new JButton[10];
	private JFrame frame;
	private ArrayList<Integer> waypoints;

	public MyDrawPanel() {	
		routeNumber = 0;
		distance = 0;
		route = new GeoPosition(0, 0);

		//create Frame and set properties	
		frame = new JFrame("Lab3");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1550, 800);
		frame.setLocation(20, 20);

		// Create menubar and add to frame
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		menuOptions = new JMenu("Optionen");
		newRoute = new JMenuItem("Route hinzufügen");
		deleteRoute = new JMenuItem("Route komplett löschen");
		menuBar.add(menuOptions);
		menuOptions.add(newRoute);
		menuOptions.add(deleteRoute);

		//create label
		JLabel distanceInformation = new JLabel("Entfernung: " + distance +" km");
		JLabel locationInformation = new JLabel("Position:(lat , lon) = (" + route.getLatitude() + " , " + route.getLongitude() + ")");

		//create ButtonPanel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(distanceInformation);
		buttonPanel.add(locationInformation);
		addButton();

		//Event handling
		menuOptions.addActionListener(this);
		buttonArray[0].addActionListener(this);



		// create Contents
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		//	contentPane.add(distanceInformation); //add label
		contentPane.add(new imagePanel()); // add picture
		contentPane.add(buttonPanel); //add button



		frame.pack(); //auto format for window
		frame.setVisible(true); //show
	}

	public void addButton(){
		//System.out.println("addButton");
		buttonArray[routeNumber] = new JButton("Route " +(routeNumber+1));
		buttonPanel.add(buttonArray[routeNumber]);
		frame.setVisible(true); //make visible
		routeNumber++;

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		//System.out.println("action");

		if(event.getSource()==buttonArray[0]){
			//System.out.println("neue Route");
			addButton();
		}

	}
	public void addPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}


	public static void main(String[] args) {
		new MyDrawPanel();
	}



}



