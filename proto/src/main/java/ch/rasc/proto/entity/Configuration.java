package ch.rasc.proto.entity;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class Configuration implements Serializable {

	private static final long serialVersionUID = 1L;

	private ConfigurationKey confKey;

	@Size(max = 1024)
	private String confValue;

	public ConfigurationKey getConfKey() {
		return this.confKey;
	}

	public void setConfKey(ConfigurationKey confKey) {
		this.confKey = confKey;
	}

	public String getConfValue() {
		return this.confValue;
	}

	public void setConfValue(String confValue) {
		this.confValue = confValue;
	}

}
