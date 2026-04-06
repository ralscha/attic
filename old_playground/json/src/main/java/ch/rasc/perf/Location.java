package ch.rasc.perf;

/**
 * Information about location.
 *
 * @author Andrey Bloschetsov
 */
public class Location {

	public static final Integer TYPE_HOME = 1;

	public static final Integer TYPE_WORK = 2;

	public static final Integer TYPE_OTHER = 3;

	private Integer type;

	private String country;

	private String state;

	private String city;

	private String place;

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
