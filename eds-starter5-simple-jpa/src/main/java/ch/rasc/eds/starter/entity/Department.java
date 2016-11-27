package ch.rasc.eds.starter.entity;

import javax.persistence.Entity;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;

@Model(value = "SimpleApp.model.Department", readMethod = "departmentService.read")
@Entity
public class Department extends AbstractPersistable {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
