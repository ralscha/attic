package ch.rasc.changelog.util;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DMYHMSDateSerializer extends JsonSerializer<Date> {

	private static final FastDateFormat DATE_FORMAT = FastDateFormat
			.getInstance("dd.MM.yyyy hh:mm:ss");

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		jgen.writeString(DATE_FORMAT.format(value));
	}
}