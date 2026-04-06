package ch.rasc.reflection;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(Include.NON_NULL)
public class ModelFieldBean {
	private final String name;

	private ModelType type;

	private Object defaultValue;

	private String dateFormat;

	private Boolean useNull;

	public ModelFieldBean(String name, ModelType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	@JsonSerialize(using = ModelTypeSerializer.class)
	public ModelType getType() {
		return this.type;
	}

	public void setType(ModelType type) {
		this.type = type;
	}

	public Object getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDateFormat() {
		return this.dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Boolean getUseNull() {
		return this.useNull;
	}

	public void setUseNull(Boolean useNull) {
		this.useNull = useNull;
	}

	private final static class ModelTypeSerializer extends JsonSerializer<ModelType> {
		@Override
		public void serialize(ModelType value, JsonGenerator jgen,
				SerializerProvider provider) throws IOException, JsonProcessingException {
			jgen.writeString(value.getJsName());

		}
	}
}
