package ch.rasc.mongodb.morphia;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

@Entity(noClassnameStored = true, value = "employees")
@Indexes(@Index(fields = @Field("salary")))
class Employee {

	@Id
	private ObjectId id;

	private String name;

	private String firstname;

	@Reference
	private Employee manager;

	@Reference
	private List<Employee> directReports = new ArrayList<>();

	private Decimal128 salary;

	public Employee() {
		// default constructor
	}

	public Employee(String name, Decimal128 salary) {
		this.name = name;
		this.salary = salary;
	}

	public ObjectId getId() {
		return this.id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Employee getManager() {
		return this.manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public List<Employee> getDirectReports() {
		return this.directReports;
	}

	public void setDirectReports(List<Employee> directReports) {
		this.directReports = directReports;
	}

	public Decimal128 getSalary() {
		return this.salary;
	}

	public void setSalary(Decimal128 salary) {
		this.salary = salary;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String toString() {
		return "Employee [id=" + this.id + ", name=" + this.name + ", manager="
				+ this.manager + ", directReports=" + this.directReports + ", salary="
				+ this.salary + "]";
	}

}