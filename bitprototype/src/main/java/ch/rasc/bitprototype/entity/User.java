package ch.rasc.bitprototype.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.edsutil.entity.DateTimeConverter;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Entity
@Table(name = "AppUser")
@Model(value = "BitP.model.User", readMethod = "userService.read",
		createMethod = "userService.create", updateMethod = "userService.update",
		destroyMethod = "userService.destroy", paging = true)
public class User extends AbstractPersistable {

	@NotEmpty(message = "{user_missing_username}")
	@Size(max = 100)
	@Column(unique = true)
	private String userName;

	@Size(max = 255)
	private String name;

	@Size(max = 255)
	private String firstName;

	@Email(message = "{user_missing_email}")
	@Size(max = 255)
	@NotEmpty(message = "{user_missing_email}")
	@Column(unique = true)
	private String email;

	private String role;

	@Size(max = 255)
	@JsonIgnore
	private String passwordHash;

	@Transient
	private String passwordNew;

	@Transient
	private String passwordNewConfirm;

	@Transient
	private String oldPassword;

	@Size(max = 8)
	private String locale;

	private boolean enabled;

	@JsonIgnore
	private Integer failedLogins;

	@JsonIgnore
	@Convert(converter = DateTimeConverter.class)
	private DateTime lockedOut;

	@Transient
	@ModelField(useNull = true)
	private Long lieferantId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "lieferantId", nullable = true)
	private Lieferant lieferant;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
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

	public String getPasswordNew() {
		return this.passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordNewConfirm() {
		return this.passwordNewConfirm;
	}

	public void setPasswordNewConfirm(String passwordNewConfirm) {
		this.passwordNewConfirm = passwordNewConfirm;
	}

	public String getOldPassword() {
		return this.oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Long getLieferantId() {
		return this.lieferantId;
	}

	public void setLieferantId(Long lieferantId) {
		this.lieferantId = lieferantId;
	}

	public Lieferant getLieferant() {
		return this.lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}

	@PostLoad
	@PostPersist
	@PostUpdate
	private void populate() {

		if (this.lieferant != null) {
			this.lieferantId = this.lieferant.getId();
		}
		else {
			this.lieferantId = null;
		}
	}
}
