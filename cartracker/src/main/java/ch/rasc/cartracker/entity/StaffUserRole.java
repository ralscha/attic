package ch.rasc.cartracker.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.rasc.cartracker.entity.option.UserRole;
import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class StaffUserRole extends AbstractPersistable {
	@ManyToOne
	@JoinColumn(name = "staffId")
	private Staff staff;

	@ManyToOne
	@JoinColumn(name = "userRoleId")
	private UserRole userRole;

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

}
