package ch.rasc.eds.starter.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.rasc.extclassgenerator.Model;

@Model(value = "Starter.model.Department", readMethod = "departmentService.read")
@Document(collection = "departments")
public class Department {

	@Id
	private String id;

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department() {
		// default constructor
	}

	public Department(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

}
