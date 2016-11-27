package ch.rasc.e4desk.util;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateTimeDeserializer extends JsonDeserializer<DateTime> {

	private final DateTimeFormatter formatter = DateTimeFormat
			.forPattern("yyyy-MM-dd HH:mm").withZoneUTC();

	@Override
	public DateTime deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return DateTime.parse(jp.getText(), this.formatter);
	}

}