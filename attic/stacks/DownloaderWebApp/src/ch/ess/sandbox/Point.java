package ch.ess.sandbox;

import javax.persistence.Embeddable;

@Embeddable
public class Point {
	
	private Double latitude;
	
	private Double longitude;

	public Point() {
		super();
	}

	public Point( Double latitude, Double longitude ) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude( Double latitude ) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude( Double longitude ) {
		this.longitude = longitude;
	}
	
	public String toString() {
		return new StringBuffer().append( latitude ).append( "," ).append( longitude ).toString();
	}
}
