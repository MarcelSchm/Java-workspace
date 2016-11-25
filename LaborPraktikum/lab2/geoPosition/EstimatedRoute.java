package lab2.geoPosition;

import java.util.ArrayList;
import java.util.Collections;

public class EstimatedRoute {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GeoRoute route1 = RouteData.createAlsterRoute1();
		GeoRoute route2 = RouteData.createAlsterRoute2();
		GeoRoute route3 = RouteData.createAlsterRoute3();
		ArrayList<GeoRoute> flightRoutes = RouteData.createFlightRoutes();

		System.out.println(route1.toString());
		System.out.println(route2.toString());
		System.out.println(route3.toString());
		System.out.println();

		Collections.sort(flightRoutes);
		for (GeoRoute geoRoute : flightRoutes) {
			System.out.println(geoRoute.toString());
		}

	}
}