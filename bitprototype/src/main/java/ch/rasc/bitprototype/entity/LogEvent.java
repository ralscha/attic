package ch.rasc.bitprototype.entity;

import java.io.IOException;

import javax.persistence.Convert;
import javax.persistence.Entity;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.edsutil.entity.DateTimeConverter;
import ch.rasc.edsutil.jackson.ISO8601DateTimeSerializer;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Entity
@Model(value = "BitP.model.LogEvent", readMethod = "logEventService.read", paging = true)
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
		return this.eventDate;
	}

	public void setEventDate(DateTime eventDate) {
		this.eventDate = eventDate;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLogger() {
		return this.logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMarker() {
		return this.marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getThread() {
		return this.thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	@JsonSerialize(using = NewLineBrSerializer.class)
	public String getException() {
		return this.exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return this.userAgent;
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
