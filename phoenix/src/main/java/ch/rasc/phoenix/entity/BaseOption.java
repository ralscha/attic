package ch.rasc.phoenix.entity;

import javax.persistence.MappedSuperclass;

import ch.rasc.edsutil.entity.AbstractPersistable;

@MappedSuperclass
public class BaseOption extends AbstractPersistable {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
