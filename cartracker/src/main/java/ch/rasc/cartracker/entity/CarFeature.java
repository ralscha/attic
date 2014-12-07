package ch.rasc.cartracker.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.rasc.cartracker.entity.option.Feature;
import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class CarFeature extends AbstractPersistable {
	@ManyToOne
	@JoinColumn(name = "carId")
	private Car car;

	@ManyToOne
	@JoinColumn(name = "featureId")
	private Feature feature;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

}
