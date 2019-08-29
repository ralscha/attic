package json.application.pojo;

import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Message {
	private String id;

	private String action;

	private String payload;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPayload() {
		return this.payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

}
