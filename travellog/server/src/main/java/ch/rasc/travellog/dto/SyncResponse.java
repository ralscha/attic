package ch.rasc.travellog.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SyncResponse<T> {
	private final List<T> gets;

	private final Map<Long, Long> updated;

	private final Map<Long, NewId> inserted;

	private final Set<Long> removed;

	public SyncResponse(List<T> gets, Map<Long, NewId> inserted, Map<Long, Long> updated,
			Set<Long> removed) {
		this.gets = gets != null ? List.copyOf(gets) : null;
		this.inserted = inserted != null ? Map.copyOf(inserted) : null;
		this.updated = updated != null ? Map.copyOf(updated) : null;
		this.removed = removed != null ? Set.copyOf(removed) : null;
	}

	public List<T> getGets() {
		return this.gets;
	}

	public Map<Long, NewId> getInserted() {
		return this.inserted;
	}

	public Map<Long, Long> getUpdated() {
		return this.updated;
	}

	public Set<Long> getRemoved() {
		return this.removed;
	}

	public static class NewId {
		private final long id;
		private final long ts;

		public NewId(long id, long ts) {
			this.id = id;
			this.ts = ts;
		}

		public long getId() {
			return this.id;
		}

		public long getTs() {
			return this.ts;
		}

	}

}
