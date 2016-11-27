package ch.rasc.e4desk.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelAssociation;
import ch.rasc.extclassgenerator.ModelAssociationType;
import ch.rasc.extclassgenerator.ModelField;

@Entity
@Table(name = "AppUser")
@Model(value = "E4desk.model.User", readMethod = "userService.read",
		destroyMethod = "userService.destroy", paging = true)
public class User extends AbstractPersistable {

	@Email
	@Size(max = 255)
	@NotNull
	@Column(unique = true)
	private String email;

	@Size(max = 255)
	private String name;

	@Size(max = 255)
	private String firstName;

	@Size(max = 60)
	private String passwordHash;

	@Size(max = 8)
	private String locale;

	private boolean enabled;

	@Size(max = 1000)
	@JsonIgnore
	private String settings;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "AppUserRoles", joinColumns = @JoinColumn(name = "userId") ,
			inverseJoinColumns = @JoinColumn(name = "roleId") )
	@ModelAssociation(value = ModelAssociationType.HAS_MANY, model = Role.class,
			foreignKey = "user_id", autoLoad = false)
	private Set<Role> roles;

	@JsonIgnore
	private Integer failedLogins;

	@JsonIgnore
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime lockedOut;

	@JsonIgnore
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime lastLogin;

	@Transient
	@ModelField(persist = false)
	private String lastLoginDescription;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getSettings() {
		return this.settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public Integer getFailedLogins() {
		return this.failedLogins;
	}

	public void setFailedLogins(Integer failedLogins) {
		this.failedLogins = failedLogins;
	}

	public DateTime getLockedOut() {
		return this.lockedOut;
	}

	public void setLockedOut(DateTime lockedOut) {
		this.lockedOut = lockedOut;
	}

	public DateTime getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(DateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastLoginDescription() {
		return this.lastLoginDescription;
	}

	public void setLastLoginDescription(String lastLoginDescription) {
		this.lastLoginDescription = lastLoginDescription;
	}

}
