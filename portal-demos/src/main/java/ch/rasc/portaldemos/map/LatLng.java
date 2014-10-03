package ch.rasc.portaldemos.map;

public class LatLng {
	private final double lat;

	private final double lng;

	public LatLng(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

}
