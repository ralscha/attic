package ch.rasc.travellog.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SyncRequest<T> {
	private final List<T> inserted;

	private final List<T> updated;

	private final Set<Long> removed;

	private final Set<Long> gets;

	@JsonCreator
	public SyncRequest(@JsonProperty("inserted") List<T> inserted,
			@JsonProperty("updated") List<T> updated,
			@JsonProperty("removed") Set<Long> removed,
			@JsonProperty("gets") Set<Long> gets) {
		this.inserted = inserted != null ? List.copyOf(inserted) : null;
		this.updated = updated != null ? List.copyOf(updated) : null;
		this.removed = removed != null ? Set.copyOf(removed) : null;
		this.gets = gets != null ? Set.copyOf(gets) : null;
	}

	public List<T> getInserted() {
		return this.inserted;
	}

	public List<T> getUpdated() {
		return this.updated;
	}

	public Set<Long> getRemoved() {
		return this.removed;
	}

	public Set<Long> getGets() {
		return this.gets;
	}

}
