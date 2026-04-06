package ch.rasc.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Geometry {

	private Area bounds;

	@JsonProperty("location_type")
	private LocationType locationType;

	private LatLng location;

	private Area viewport;

	public Area getBounds() {
		return this.bounds;
	}

	public void setBounds(Area bounds) {
		this.bounds = bounds;
	}

	public LocationType getLocationType() {
		return this.locationType;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public LatLng getLocation() {
		return this.location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	public Area getViewport() {
		return this.viewport;
	}

	public void setViewport(Area viewport) {
		this.viewport = viewport;
	}

}
