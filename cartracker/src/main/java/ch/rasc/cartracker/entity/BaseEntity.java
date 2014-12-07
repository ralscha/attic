package ch.rasc.cartracker.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.ModelField;

@MappedSuperclass
public abstract class BaseEntity extends AbstractPersistable {

	@ModelField(dateFormat = "c")
	private Date createDate;

	@ModelField(defaultValue = "true")
	private boolean active;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@PrePersist
	private void setCreateDate() {
		if (createDate == null) {
			createDate = new Date();
		}
	}
}