package ch.rasc.packt.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.ModelField;

@MappedSuperclass
public class SakilaBaseEntity extends AbstractPersistable {
	@ModelField(dateFormat = "c")
	private Date lastUpdate;

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
