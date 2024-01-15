package ch.rasc.messenger.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.rasc.bsoncodec.annotation.BsonDocument;
import ch.rasc.bsoncodec.annotation.Id;

@BsonDocument
@JsonInclude(Include.NON_NULL)
public class Message {

	@Id(generator = UUIDStringGenerator.class)
	private String id;

	private String content;

	private String author;

	private Location location;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.author == null ? 0 : this.author.hashCode());
		result = prime * result + (this.content == null ? 0 : this.content.hashCode());
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		result = prime * result + (this.location == null ? 0 : this.location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Message other = (Message) obj;
		if (this.author == null) {
			if (other.author != null) {
				return false;
			}
		}
		else if (!this.author.equals(other.author)) {
			return false;
		}
		if (this.content == null) {
			if (other.content != null) {
				return false;
			}
		}
		else if (!this.content.equals(other.content)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.location == null) {
			if (other.location != null) {
				return false;
			}
		}
		else if (!this.location.equals(other.location)) {
			return false;
		}
		return true;
	}

}
