package fi.jyu.it.ties456.week39.group8;

/**
 * Seperate class to control, if the values for longitude and latitude are
 * valid
 * 
 * @author Jonas
 *
 */
public class Location {

	private Double longitude;
	private Double latitude;

	/**
	 * 
	 * @param latitude
	 * @param longitude
	 * @throws Exception
	 */
	public Location(Double latitude, Double longitude) throws Exception {

		if (latitude <= 90 && latitude >= -90) {
			this.latitude = latitude;
		} else {
			System.err.println("Given latitude is not in the area of validity!");
			throw new Exception("Given latitude is not in the area of validity");
		}
		if (longitude <= 180 && longitude >= -180) {
			this.longitude = longitude;
		} else {
			System.err.println("Given longitude is not in the area of validity!");
			throw new Exception("Given longitude is not in the area of validity");
		}
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

}
