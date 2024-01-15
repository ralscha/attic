package ch.rasc.proto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClientState extends AbstractPersistable {

	private String name;

	private String value;

	@JsonIgnore
	private long userId;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
