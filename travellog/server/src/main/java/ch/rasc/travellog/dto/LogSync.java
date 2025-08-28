package ch.rasc.travellog.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class LogSync {

	private final long id;

	private final long ts;

	private final long travelId;

	private final long created;

	private final BigDecimal lat;

	private final BigDecimal lng;

	private final String location;

	private final String report;

	@JsonCreator
	public LogSync(@JsonProperty("id") long id, @JsonProperty("ts") long ts,
			@JsonProperty("travelId") long travelId,
			@JsonProperty("created") long created, @JsonProperty("lat") BigDecimal lat,
			@JsonProperty("lng") BigDecimal lng,
			@JsonProperty("location") String location,
			@JsonProperty("report") String report) {
		this.id = id;
		this.ts = ts;
		this.travelId = travelId;
		this.created = created;
		this.lat = lat;
		this.lng = lng;
		this.location = location;
		this.report = report;
	}

	public long getId() {
		return this.id;
	}

	public long getTs() {
		return this.ts;
	}

	public long getTravelId() {
		return this.travelId;
	}

	public long getCreated() {
		return this.created;
	}

	public BigDecimal getLat() {
		return this.lat;
	}

	public BigDecimal getLng() {
		return this.lng;
	}

	public String getLocation() {
		return this.location;
	}

	public String getReport() {
		return this.report;
	}

}
