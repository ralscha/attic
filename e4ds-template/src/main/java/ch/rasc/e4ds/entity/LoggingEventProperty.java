package ch.rasc.e4ds.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "logging_event_property")
public class LoggingEventProperty {

	@EmbeddedId
	private LoggingEventPropertyId id;

	@Column(name = "mapped_value")
	private String mappedValue;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "event_id", nullable = false, insertable = false,
			updatable = false)
	private LoggingEvent eventId;

	public LoggingEventPropertyId getId() {
		return this.id;
	}

	public void setId(LoggingEventPropertyId id) {
		this.id = id;
	}

	public String getMappedValue() {
		return this.mappedValue;
	}

	public void setMappedValue(String mappedValue) {
		this.mappedValue = mappedValue;
	}

	public LoggingEvent getEventId() {
		return this.eventId;
	}

	public void setEventId(LoggingEvent eventId) {
		this.eventId = eventId;
	}

}
