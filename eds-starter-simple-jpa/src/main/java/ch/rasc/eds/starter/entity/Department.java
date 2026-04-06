package ch.rasc.eds.starter.entity;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import ch.rasc.extclassgenerator.Model;

@Model(value = "Starter.model.Department", readMethod = "departmentService.read")
@Entity
public class Department extends AbstractPersistable<Long> {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
