package ch.rasc.playground.orientdb.domain;

import com.orientechnologies.orient.core.annotation.OId;
import com.orientechnologies.orient.core.annotation.OVersion;

public class Country {
	@OId
	private Object id;

	@OVersion
	private Object version;

	private String name;

	public Country() {
	}

	public Country(String iName) {
		this.name = iName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getId() {
		return this.id;
	}

	public Object getVersion() {
		return this.version;
	}
}