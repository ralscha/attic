package ch.rasc.cartracker.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class CarStaff extends AbstractPersistable {

	@ManyToOne
	@JoinColumn(name = "carId")
	private Car car;

	@ManyToOne
	@JoinColumn(name = "staffId")
	private Staff staff;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

}
