package ch.rasc.e4ds.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class Configuration extends AbstractPersistable {

	@NotNull
	@Column(unique = true)
	@Enumerated(EnumType.STRING)
	private ConfigurationKey confKey;

	@NotNull
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
