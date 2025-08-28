package ch.rasc.travellog.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class TravelSync {

	private final long id;

	private final long ts;

	private final String name;

	@JsonCreator
	public TravelSync(@JsonProperty("id") long id, @JsonProperty("ts") long ts,
			@JsonProperty("name") String name) {
		this.id = id;
		this.ts = ts;
		this.name = name;
	}

	public long getId() {
		return this.id;
	}

	public long getTs() {
		return this.ts;
	}

	public String getName() {
		return this.name;
	}

}
