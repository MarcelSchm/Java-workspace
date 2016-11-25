package lab1.geoPosition;

public class GeoPosition {
	private double latitude;
	private double longitude;

	public GeoPosition(double latitude, double longitude) {
		// super(); //auto-generated
		if (latitude >= -90 && latitude <= 90) {
			this.latitude = latitude;
		} else {
			System.out.println("Breite nicht im Wertebereich");
		}
		if (longitude >= -180 && longitude <= 180) {
			this.longitude = longitude;
		} else {
			System.out.println("Länge nicht im Wertebereich");
		}

	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public boolean isNorthernHemisphere() {
		if (latitude > 0) {
			return true;
		} else
			return false;
	}

	public boolean isSouthernHemisphere() {
		if (latitude < 0) {
			return true;
		} else
			return false;
	}

	public double distanceInKm(GeoPosition other) {
		return distanceInKm(this, other);
	}

	public static double distanceInKm(GeoPosition a, GeoPosition b) {

		return 6378.388 * Math.acos( 																	// 63788.388*acos(
				(Math.sin(a.latitude / 180 * Math.PI) * Math.sin(b.latitude / 180 * Math.PI)) 			// sin(lat1)*sin(lat2)
						+ (Math.cos(a.latitude / 180 * Math.PI) * Math.cos(b.latitude / 180 * Math.PI) // +cos(lat1)*cos(lat2)
								* Math.cos((b.longitude - a.longitude) / 180 * Math.PI))); 				// *cos(lon2-lon1))
	}

	public static double localDistanceInKm(GeoPosition a, GeoPosition b) {
		double deltaY = 0;
		double deltaX = 0;
		deltaY = 111.3 * Math.abs(a.latitude - b.latitude);
		deltaX = 111.3 * Math.cos(((a.latitude + b.latitude) / 2) / 180 * Math.PI)
				* Math.abs(a.longitude - b.longitude);
		return Math.sqrt((deltaX * deltaX) + deltaY * deltaY);
	}

	public String toString() {
		return "(" + latitude + ", " + longitude + ")";
	}

};
