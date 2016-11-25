package lab1.geoPosition;

import java.util.Locale;

public class GeoApp {
	
	public static String toChart(GeoPosition a, GeoPosition haw){
		 return String.format(Locale.US, "%+010.6f", a.getLatitude())+ "\t " +String.format(Locale.US, "%+011.6f", a.getLongitude()) + "\t"
					+ GeoPosition.distanceInKm(haw,a) + "\t" + GeoPosition.localDistanceInKm(a, haw) + "\t" + variation(a,haw);
	}
	
	public static double variation(GeoPosition other, GeoPosition haw) {
		double accurate = other. distanceInKm(haw);
		double local = GeoPosition.localDistanceInKm(haw,other);
		if (Math.abs(accurate-local)>0.0001){
		return Math.abs((accurate - local)) / accurate * 100;
		}
		else return 0;
	}

	public static void main(String[] args) {

		GeoPosition haw = new GeoPosition(53.557078, 10.023109);
		GeoPosition eiffel = new GeoPosition(48.858363, 2.294481);
		GeoPosition palmaDeMallorca = new GeoPosition(39.562553, 2.661947);
		GeoPosition lasVegas = new GeoPosition(36.156214, -115.148736);
		GeoPosition copacabana = new GeoPosition(-22.971177, -43.182543);
		GeoPosition waikikiBeach = new GeoPosition(21.281004, -157.837456);
		GeoPosition surfersParadise = new GeoPosition(-28.002695, 153.431781);
		GeoPosition northPole = new GeoPosition(90, haw.getLongitude());
		GeoPosition southPole = new GeoPosition(-90, haw.getLongitude());
		GeoPosition equator = new GeoPosition(0, haw.getLongitude());

		System.out.println("Ort\t\t   Breitengrad\t Längengrad\tEntfernung km(genau)\tEntfernung km(lokal)\tAbweichung %	");
		System.out.println("HAW Hamburg\t   "+haw.getLatitude()+ "\t "+haw.getLongitude()+"\t"+haw.distanceInKm(haw)+"\t\t\t"+GeoPosition.localDistanceInKm(haw,haw)+"\t\t\t"+variation(haw,haw));
		System.out.println("Eiffelturm\t   "+toChart(eiffel, haw));
		System.out.println("Palma De Mallorca  "+toChart(palmaDeMallorca, haw));
		System.out.println("Las Vegas\t   "+toChart(lasVegas,haw));
		System.out.println("Copacabana\t   "+toChart(copacabana,haw));
		System.out.println("Waikiki Beach\t   "+toChart(waikikiBeach,haw));
		System.out.println("Surfer´s Paradise  "+toChart(surfersParadise,haw));
		
		System.out.println("\nNordPol\t\t   "+toChart(northPole,haw));
		System.out.println("Südpol\t\t   "+toChart(southPole,haw));
		System.out.println("Äquator\t\t   "+toChart(equator,haw));
	}

}
