#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "`User`")
public class User extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Length(max = 100)
	@Column(unique = true)
	private String userName;

	@Length(max = 200)
	private String name;

	@Length(max = 200)
	private String firstName;

	@Email
	@Length(max = 255)
	@NotNull
	private String email;

	@Length(max = 64)
	private String passwordHash;

	@Length(max = 255)
	private String openId;

	@Length(max = 8)
	private String locale;

	private boolean enabled;

	@Temporal(TemporalType.DATE)
	private Date createDate;

	@ManyToMany
	@JoinTable(name = "UserRoles", joinColumns = @JoinColumn(name = "userId"),
			inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Set<Role> roles;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private Set<Address> addresses = new HashSet<>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
