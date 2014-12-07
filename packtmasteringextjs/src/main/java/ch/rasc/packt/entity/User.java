package ch.rasc.packt.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "AppUser")
@Model(value = "Packt.model.security.User", disablePagingParameters = true,
		paging = true, createMethod = "userService.create",
		readMethod = "userService.readUser", updateMethod = "userService.update",
		destroyMethod = "userService.destroy")
public class User extends AbstractPersistable {

	private String name;

	private String userName;

	@JsonIgnore
	private String password;

	private String email;

	@Lob
	private String picture;

	@Transient
	@ModelField(convert = "null", useNull = true)
	private Long appGroupId;

	@ManyToOne
	@JoinColumn(name = "appGroupId")
	@JsonIgnore
	private AppGroup appGroup;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Long getAppGroupId() {
		return appGroupId;
	}

	public void setAppGroupId(Long appGroupId) {
		this.appGroupId = appGroupId;
	}

	public AppGroup getAppGroup() {
		return appGroup;
	}

	public void setAppGroup(AppGroup appGroup) {
		this.appGroup = appGroup;
	}

	@PostLoad
	@PostUpdate
	@PostPersist
	private void populate() {
		if (appGroup != null) {
			appGroupId = appGroup.getId();
		}
		else {
			appGroupId = null;
		}
	}

}
