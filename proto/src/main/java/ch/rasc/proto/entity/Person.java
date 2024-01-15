package ch.rasc.proto.entity;

import ch.rasc.extclassgenerator.Model;

@Model(value = "Proto.model.Person", readMethod = "personService.read",
		createMethod = "personService.update", updateMethod = "personService.update",
		destroyMethod = "personService.destroy", rootProperty = "records",
		identifier = "negative")
public class Person extends AbstractPersistable {
	private String firstName;
	private String lastName;

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
