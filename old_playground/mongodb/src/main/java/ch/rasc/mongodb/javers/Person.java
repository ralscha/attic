package ch.rasc.mongodb.javers;

import org.javers.core.metamodel.annotation.Id;

public class Person {
	@Id
	private String login;
	private String name;

	public Person(String login, String name) {
		super();
		this.login = login;
		this.name = name;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}