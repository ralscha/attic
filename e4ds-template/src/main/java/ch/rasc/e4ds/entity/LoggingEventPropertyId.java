package ch.rasc.e4ds.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LoggingEventPropertyId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "event_id", nullable = false)
	private long eventId;

	@Column(name = "mapped_key", nullable = false, length = 255)
	private String mappedKey;

	public LoggingEventPropertyId() {
		// no action
	}

	public LoggingEventPropertyId(long eventId, String mappedKey) {
		this.eventId = eventId;
		this.mappedKey = mappedKey;
	}

	public long getEventId() {
		return this.eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getMappedKey() {
		return this.mappedKey;
	}

	public void setMappedKey(String mappedKey) {
		this.mappedKey = mappedKey;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (!(other instanceof LoggingEventPropertyId)) {
			return false;
		}
		LoggingEventPropertyId castOther = (LoggingEventPropertyId) other;

		return this.getEventId() == castOther.getEventId()
				&& (this.getMappedKey() == castOther.getMappedKey() || this
						.getMappedKey() != null
						&& castOther.getMappedKey() != null
						&& this.getMappedKey().equals(castOther.getMappedKey()));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getEventId();
		result = 37 * result
				+ (getMappedKey() == null ? 0 : this.getMappedKey().hashCode());
		return result;
	}

}
