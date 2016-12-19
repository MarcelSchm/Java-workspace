package lab2.geoPosition;

import java.util.ArrayList;

import lab1.geoPosition.GeoPosition;

public class GeoRoute implements Distance, Comparable<GeoRoute> {
	private String name;
	private ArrayList<GeoPosition> waypoints;

	public GeoRoute(String name) {
		this.name = name;
		waypoints = new ArrayList<GeoPosition>();
	}

	public GeoRoute(String name, ArrayList<GeoPosition> waypoints) {
		super();
		this.name = name;
		this.waypoints = waypoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addWaypoint(GeoPosition waypoint) {
		waypoints.add(waypoint);

	}

	public void removeWaypoint(int index) {
		if (getNumberWaypoints() > index) {
			waypoints.remove(index);
		}
	}

	public int getNumberWaypoints() {
		return waypoints.size();
	}

	public GeoPosition getWaypoint(int index) {
		return waypoints.get(index);
	}

	public GeoPosition[] getWaypoints() {
		GeoPosition[] localArray = new GeoPosition[waypoints.size()];
		return waypoints.toArray(localArray);
	}

	@Override
	public double getDistance() {
		double sum = 0.0;
		for (int i = 0; i < getNumberWaypoints() - 1; i++) {
			sum += getWaypoint(i).distanceInKm(getWaypoint(i + 1));
		}
		return sum;
	}

	@Override
	public int compareTo(GeoRoute other) {
		return Double.compare(this.getDistance(), other.getDistance());
	}

	@Override
	public String toString() {
		return name + " (" + String.format("%.1f", getDistance()) + " km)";
	}

	public boolean[] passedWaypoints(GeoRoute routeToCompare){
		int waypointIndex = 0;
		int compareWaypointIndex = 0;
		boolean[] isReached = new boolean[getNumberWaypoints()];
		for(waypointIndex = 0;waypointIndex < getNumberWaypoints();waypointIndex++){
			for(compareWaypointIndex = 0; compareWaypointIndex < routeToCompare.getNumberWaypoints();compareWaypointIndex++){
				if(getWaypoint(waypointIndex).distanceInKm(routeToCompare.getWaypoint(compareWaypointIndex)) < 0.025){
					isReached[waypointIndex]= true;
				}
			}
			
		}
		for(boolean element: isReached){
			if(element==true){
			}
		}
		
		return isReached;
	}
}
