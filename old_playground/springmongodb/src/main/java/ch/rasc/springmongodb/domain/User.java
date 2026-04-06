package ch.rasc.springmongodb.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id
	private ObjectId id;

	private final String userName;

	public User(String userName) {
		this.userName = userName;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getUserName() {
		return this.userName;
	}

	@Override
	public String toString() {
		return "User [id=" + this.id + ", userName=" + this.userName + "]";
	}

}
