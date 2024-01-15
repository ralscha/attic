package ch.rasc.changelog.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DMYHMSDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		DateFormat dateForm = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		try {
			return dateForm.parse(jp.getText());
		}
		catch (ParseException e) {
			LoggerFactory.getLogger(DMYHMSDateDeserializer.class).error("deserialize", e);
			return null;
		}
	}

}