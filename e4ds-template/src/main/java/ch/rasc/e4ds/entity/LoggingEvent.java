package ch.rasc.e4ds.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "logging_event")
public class LoggingEvent {

	@Column(name = "timestmp", nullable = false)
	private BigDecimal timestmp;

	@Column(name = "formatted_message", nullable = false)
	private String formattedMessage;

	@Column(name = "logger_name", nullable = false)
	private String loggerName;

	@Column(name = "level_string", nullable = false)
	private String levelString;

	@Column(name = "thread_name")
	private String threadName;

	@Column(name = "reference_flag")
	private Short referenceFlag;

	private String arg0;

	private String arg1;

	private String arg2;

	private String arg3;

	@Column(name = "caller_filename", nullable = false)
	private String callerFilename;

	@Column(name = "caller_class", nullable = false)
	private String callerClass;

	@Column(name = "caller_method", nullable = false)
	private String callerMethod;

	@Column(name = "caller_line", nullable = false)
	private String callerLine;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_id", unique = true, nullable = false)
	private Long eventId;

	@OneToMany(mappedBy = "eventId", cascade = CascadeType.ALL, orphanRemoval = true,
			fetch = FetchType.EAGER)
	private Set<LoggingEventException> loggingEventException = new HashSet<>();

	@OneToMany(mappedBy = "eventId", cascade = CascadeType.ALL, orphanRemoval = true,
			fetch = FetchType.EAGER)
	private Set<LoggingEventProperty> loggingEventProperty = new HashSet<>();

	public BigDecimal getTimestmp() {
		return this.timestmp;
	}

	public void setTimestmp(BigDecimal timestmp) {
		this.timestmp = timestmp;
	}

	public String getFormattedMessage() {
		return this.formattedMessage;
	}

	public void setFormattedMessage(String formattedMessage) {
		this.formattedMessage = formattedMessage;
	}

	public String getLoggerName() {
		return this.loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public String getLevelString() {
		return this.levelString;
	}

	public void setLevelString(String levelString) {
		this.levelString = levelString;
	}

	public String getThreadName() {
		return this.threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Short getReferenceFlag() {
		return this.referenceFlag;
	}

	public void setReferenceFlag(Short referenceFlag) {
		this.referenceFlag = referenceFlag;
	}

	public String getArg0() {
		return this.arg0;
	}

	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}

	public String getArg1() {
		return this.arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public String getArg2() {
		return this.arg2;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	public String getArg3() {
		return this.arg3;
	}

	public void setArg3(String arg3) {
		this.arg3 = arg3;
	}

	public String getCallerFilename() {
		return this.callerFilename;
	}

	public void setCallerFilename(String callerFilename) {
		this.callerFilename = callerFilename;
	}

	public String getCallerClass() {
		return this.callerClass;
	}

	public void setCallerClass(String callerClass) {
		this.callerClass = callerClass;
	}

	public String getCallerMethod() {
		return this.callerMethod;
	}

	public void setCallerMethod(String callerMethod) {
		this.callerMethod = callerMethod;
	}

	public String getCallerLine() {
		return this.callerLine;
	}

	public void setCallerLine(String callerLine) {
		this.callerLine = callerLine;
	}

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Set<LoggingEventException> getLoggingEventException() {
		return this.loggingEventException;
	}

	public void setLoggingEventException(Set<LoggingEventException> loggingEventException) {
		this.loggingEventException = loggingEventException;
	}

	public Set<LoggingEventProperty> getLoggingEventProperty() {
		return this.loggingEventProperty;
	}

	public void setLoggingEventProperty(Set<LoggingEventProperty> loggingEventProperty) {
		this.loggingEventProperty = loggingEventProperty;
	}

}
