package ch.rasc.packtsales.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import ch.rasc.edsutil.entity.AbstractPersistable;

@MappedSuperclass
public class BaseEntity extends AbstractPersistable {
	private Integer status;

	private Date modified;

	private Date created;

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
