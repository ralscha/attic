package ch.rasc.ab.entity;

import java.io.IOException;

import javax.persistence.Convert;
import javax.persistence.Entity;

import org.joda.time.DateTime;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.edsutil.entity.DateTimeConverter;
import ch.rasc.edsutil.jackson.ISO8601DateTimeSerializer;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Model(value = "Ab.model.LogEvent", readMethod = "logEventService.read", paging = true)
public class LogEvent extends AbstractPersistable {

	@ModelField(dateFormat = "c")
	@Convert(converter = DateTimeConverter.class)
	private DateTime eventDate;

	private String level;

	private String logger;

	private String source;

	private String message;

	private String marker;

	private String thread;

	private String exception;

	private String userName;

	private String ip;

	private String userAgent;

	@JsonSerialize(using = ISO8601DateTimeSerializer.class)
	public DateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(DateTime eventDate) {
		this.eventDate = eventDate;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	@JsonSerialize(using = NewLineBrSerializer.class)
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	private final static class NewLineBrSerializer extends JsonSerializer<String> {

		@Override
		public void serialize(String value, JsonGenerator jgen,
				SerializerProvider provider) throws IOException, JsonProcessingException {
			jgen.writeString(value.replace("\n", "<br>"));
		}

	}
}
