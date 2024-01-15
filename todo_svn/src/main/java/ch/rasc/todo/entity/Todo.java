package ch.rasc.todo.entity;

import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.javers.core.metamodel.annotation.TypeName;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@JsonInclude(Include.NON_NULL)
@TypeName("todo")
@Indexed
public class Todo {

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 7)
	private Type type;

	@Future
	private Date due;

	@NotEmpty
	@Field(store = Store.NO)
	private String title;

	
	@Length(max = 4000)
	@Field(store = Store.NO)
	private String description;

	private String imageName;

	@Transient
	private String thumbnail64;

	@Lob
	@JsonProperty(access = Access.WRITE_ONLY)
	@DiffIgnore
	private byte[] thumbnail;

	public void updateThumbnail64() {
		if (this.thumbnail != null && this.thumbnail.length > 0) {
			this.thumbnail64 = Base64.getEncoder().encodeToString(this.thumbnail);
		}
	}

	public Long getId() {
		return this.id;
	}

	protected void setId(final Long id) {
		this.id = id;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getDue() {
		return this.due;
	}

	public void setDue(Date due) {
		this.due = due;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getThumbnail64() {
		return this.thumbnail64;
	}

	public void setThumbnail64(String thumbnail64) {
		this.thumbnail64 = thumbnail64;
	}

	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(ClassUtils.getUserClass(obj))) {
			return false;
		}

		Todo that = (Todo) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}

	@Override
	public String toString() {
		return "Todo [id=" + this.id + ", type=" + this.type + ", due=" + this.due
				+ ", title=" + this.title + ", description=" + this.description + "]";
	}

}
