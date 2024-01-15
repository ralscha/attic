package ch.rasc.spring.wamp;

import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler {

	private ObjectMapper mapper;

	public JsonHandler() {
		mapper = new ObjectMapper();
	}

	public void setObjectMapper(ObjectMapper mapper) {
		if (mapper == null) {
			throw new IllegalArgumentException("ObjectMapper must not be null");
		}

		this.mapper = mapper;
	}

	public ObjectMapper getObjectMapper() {
		return mapper;
	}

	public String writeValueAsString(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		}
		catch (Exception e) {
			LogFactory.getLog(JsonHandler.class).info("serialize object to json", e);
			return null;
		}
	}

	public <T> T readValue(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		}
		catch (Exception e) {
			LogFactory.getLog(JsonHandler.class).info("deserialize json to object", e);
			return null;
		}
	}

	public <T> T convertValue(Object object, Class<T> clazz) {
		return mapper.convertValue(object, clazz);
	}

}
