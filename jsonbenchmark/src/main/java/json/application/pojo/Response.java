package json.application.pojo;

import javax.json.bind.annotation.JsonbProperty;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonObject
public class Response {

	@JsonField
	public User[] users;

	@JsonField
	public String status;

	@SerializedName("is_real_json") // for GSON
	@org.boon.json.annotations.SerializedName("is_real_json") // for Boon
	@JsonProperty("is_real_json") // for Jackson Databind
	@JsonField(name = "is_real_json") // Logansquare
	@JsonbProperty(value = "is_real_json") // Yasson
	public boolean isRealJson;
}
