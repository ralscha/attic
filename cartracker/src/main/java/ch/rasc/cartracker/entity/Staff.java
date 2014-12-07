package ch.rasc.cartracker.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import ch.rasc.cartracker.entity.option.Position;
import ch.rasc.edsutil.SortProperty;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Collections2;

@Entity
@Model(value = "CarTracker.model.Staff", readMethod = "staffService.read",
		createMethod = "staffService.create", updateMethod = "staffService.update",
		destroyMethod = "staffService.destroy", paging = true)
public class Staff extends BaseEntity {

	@Size(max = 255)
	@NotEmpty(message = "Please enter a First Name")
	private String firstName;

	@Size(max = 255)
	@NotEmpty(message = "Please enter a Last Name")
	private String lastName;

	@NotNull(message = "Please enter a valid Date of Birth")
	@ModelField(dateFormat = "c")
	private Date dob;

	@Size(max = 255)
	private String username;

	@Size(max = 255)
	@JsonIgnore
	private String password;

	@Transient
	@Size(max = 255)
	private String changePassword;

	@Size(max = 255)
	@NotEmpty(message = "Please enter a Address1")
	private String address1;

	@Size(max = 255)
	private String address2;

	@Size(max = 255)
	@NotEmpty(message = "Please enter a City")
	private String city;

	@Size(max = 10)
	@NotEmpty(message = "Please enter a State")
	private String state;

	@Size(max = 10)
	@NotEmpty(message = "Please enter a Postal Code")
	private String postalCode;

	@Size(max = 50)
	@NotEmpty(message = "Please enter a valid Phone Number")
	private String phone;

	@NotNull(message = "Please enter a valid Hire Date")
	@ModelField(dateFormat = "c")
	private Date hireDate;

	@ManyToOne
	@JoinColumn(name = "positionId")
	@JsonIgnore
	private Position position;

	@Transient
	@ModelField(useNull = true)
	private Long positionId;

	@Transient
	@ModelField(persist = false)
	@SortProperty("position.longName")
	private String positionName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "staff", orphanRemoval = true)
	@JsonIgnore
	private Set<StaffUserRole> staffUserRoles = new HashSet<>();

	@ModelField
	@Transient
	private Collection<Long> userRoleIds;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<StaffUserRole> getStaffUserRoles() {
		return staffUserRoles;
	}

	public void setStaffUserRoles(Set<StaffUserRole> staffUserRoles) {
		this.staffUserRoles = staffUserRoles;
	}

	public Collection<Long> getUserRoleIds() {
		return userRoleIds;
	}

	public void setUserRoleIds(Collection<Long> userRoleIds) {
		this.userRoleIds = userRoleIds;
	}

	public String getChangePassword() {
		return changePassword;
	}

	public void setChangePassword(String changePassword) {
		this.changePassword = changePassword;
	}

	@PostLoad
	@PostPersist
	@PostUpdate
	private void populate() {
		if (position != null) {
			positionName = position.getLongName();
			positionId = position.getId();
		}
		else {
			positionName = null;
			positionId = null;
		}

		userRoleIds = Collections2.transform(staffUserRoles, input -> input.getUserRole()
				.getId());
	}

}
