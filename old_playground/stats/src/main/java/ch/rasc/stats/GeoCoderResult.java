package ch.rasc.stats;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoCoderResult {

	@JsonProperty("address_components")
	private List<AddressComponents> addressComponents;

	@JsonProperty("formatted_address")
	private String formattedAddress;

	private Geometry geometry;

	private List<String> types;

	@JsonProperty("partial_match")
	private boolean partialMatch;

	public String getFormattedAddress() {
		return this.formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public List<AddressComponents> getAddressComponents() {
		return this.addressComponents;
	}

	public void setAddressComponents(List<AddressComponents> addressComponents) {
		this.addressComponents = addressComponents;
	}

	public Geometry getGeometry() {
		return this.geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public List<String> getTypes() {
		return this.types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public boolean isPartialMatch() {
		return this.partialMatch;
	}

	public void setPartialMatch(boolean partialMatch) {
		this.partialMatch = partialMatch;
	}

}
