package ch.rasc.javaplayground.hazelcast;

import java.io.Serializable;

public class ObjectToCache implements Serializable {
	private static final long serialVersionUID = -4702483501497968870L;

	private String objectName;

	private String objectValue;

	private Integer objectID;

	public ObjectToCache() {

	}

	public ObjectToCache(String objectName, String objectValue, Integer objectID) {
		super();
		this.objectName = objectName;
		this.objectValue = objectValue;
		this.objectID = objectID;
	}

	public final String getObjectName() {
		return this.objectName;
	}

	public final void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public final String getObjectValue() {
		return this.objectValue;
	}

	public final void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public final Integer getObjectID() {
		return this.objectID;
	}

	public final void setObjectID(Integer objectID) {
		this.objectID = objectID;
	}

	@Override
	public String toString() {
		return "ObjectToCache [objectID=" + this.objectID + ", objectName="
				+ this.objectName + ", objectValue=" + this.objectValue + "]";
	}

}
