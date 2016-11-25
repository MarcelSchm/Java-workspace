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
import javax.swing.JPanel;

import lab1.geoPosition.GeoPosition;

public class DrawWithMouse extends MouseAdapter implements ActionListener{
	private MyDrawPanel panel;
	private static JFrame frame;
	private JMenu menuOptions;
	private JMenuBar menuBar;
	private JMenuItem newRoute,deleteRoute;
	private double distance;
	private GeoPosition route;
	private JPanel buttonPanel;
	private int routeNumber;
	private JButton[] buttonArray = new JButton[10];
	Container contentPane;

	public DrawWithMouse() {
		routeNumber = 0;
		distance = 0;
		route = new GeoPosition(0, 0);

		//create Frame and set properties	
		frame = new JFrame("Lab43");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1550, 800);
		frame.setLocation(20, 20);

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


		panel = new MyDrawPanel();
		panel.addMouseListener(this);
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
		buttonArray[routeNumber] = new JButton("Route " +(routeNumber+1));
		buttonPanel.add(buttonArray[routeNumber]);
		frame.setVisible(true); //make visible
		routeNumber++;

	}

	@Override
	public void mousePressed(MouseEvent event) {
		if(1 == event.getButton()){
			System.out.printf("(x,y) = (%d, %d)\n", event.getX(), event.getY());
			panel.addPoint(event.getX(), event.getY());
		}else if (event.getButton() == 3) {
			System.out.println("Clear panel");
			panel.clear();
		}
	}
	public static void main(String[] args) {
		new DrawWithMouse();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		//System.out.println("action");

		if(event.getSource()==buttonArray[0]){
			//System.out.println("neue Route");
			addButton();

		}


	}
}
