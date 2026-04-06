package ch.rasc.springplayground.json.config;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.javalite.activejdbc.Model;

public final class ModelSerializer extends JsonSerializer<Model> {
	@Override
	public void serialize(Model value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeRawValue(value.toJson(false));
	}
}