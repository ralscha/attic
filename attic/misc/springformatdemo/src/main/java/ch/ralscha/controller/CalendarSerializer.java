package ch.ralscha.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class CalendarSerializer extends JsonSerializer<Calendar> {

	@Override
	public void serialize(Calendar value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		jgen.writeString(formatter.format(value.getTime()));
	}
}
