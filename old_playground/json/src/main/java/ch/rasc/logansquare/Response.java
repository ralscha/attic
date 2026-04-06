package ch.rasc.logansquare;

import java.util.List;

import org.boon.json.annotations.SerializedName;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonObject
public class Response {

	@JsonField
	public List<User> users;

	@JsonField
	public String status;

	@SerializedName("is_real_json") // Annotation needed for GSON
	@JsonProperty("is_real_json") // Annotation needed for Jackson Databind
	@JsonField(name = "is_real_json")
	public boolean isRealJson;
}
