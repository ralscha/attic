package ch.rasc.stats;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressComponents {

	@JsonProperty("long_name")
	private String longName;

	@JsonProperty("short_name")
	private String shortName;

	private List<String> types;

	public String getLongName() {
		return this.longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public List<String> getTypes() {
		return this.types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

}
