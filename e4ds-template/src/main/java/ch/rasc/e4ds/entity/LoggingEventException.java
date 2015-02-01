package ch.rasc.e4ds.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "logging_event_exception")
public class LoggingEventException {

	@EmbeddedId
	private LoggingEventExceptionId id;

	@Column(name = "trace_line", nullable = false)
	private String traceLine;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "event_id", nullable = false, insertable = false,
			updatable = false)
	private LoggingEvent eventId;

	public LoggingEventExceptionId getId() {
		return this.id;
	}

	public void setId(LoggingEventExceptionId id) {
		this.id = id;
	}

	public String getTraceLine() {
		return this.traceLine;
	}

	public void setTraceLine(String traceLine) {
		this.traceLine = traceLine;
	}

	public LoggingEvent getEventId() {
		return this.eventId;
	}

	public void setEventId(LoggingEvent eventId) {
		this.eventId = eventId;
	}

}
