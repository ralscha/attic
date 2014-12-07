package ch.rasc.cartracker.entity.option;

import javax.persistence.MappedSuperclass;

import ch.rasc.cartracker.entity.BaseEntity;

@MappedSuperclass
public class BaseOption extends BaseEntity {
	private String longName;

	private String shortName;

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
