package ch.rasc.reflection;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Model(value = "User", paging = true, readMethod = "read", createMethod = "create",
		updateMethod = "update", destroyMethod = "destroy")
public class User extends Base {

	private String name;

	public String firstName;

	@SuppressWarnings("unused")
	private String veryPrivate;

	private String myName;

	@ModelField(defaultValue = "true")
	private boolean active;

	@ModelField(defaultValue = "c")
	private LocalDateTime dob;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDateTime getDob() {
		return this.dob;
	}

	public void setDob(LocalDateTime dob) {
		this.dob = dob;
	}

	@JsonIgnore
	public String getMyName() {
		return this.myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	// public User(int id, String name) {
	// this.id = id;
	// this.name = name;
	// }
	//
	// public int getId() {
	// return id;
	// }
	//
	// public String getName() {
	// return name;
	// }

}
