package ch.rasc.jpa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long employeeId;

	@NotBlank
	@Length(max = 30)
	private String employeeName;

	@NotBlank
	@Length(max = 30)
	private String employeeSurname;

	@Length(max = 50)
	private String job;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", orphanRemoval = true)
	private Set<Address> address = new HashSet<>();

	public long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeSurname() {
		return this.employeeSurname;
	}

	public void setEmployeeSurname(String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Set<Address> getAddress() {
		return this.address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}

}
