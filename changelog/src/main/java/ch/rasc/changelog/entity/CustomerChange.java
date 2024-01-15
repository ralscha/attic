package ch.rasc.changelog.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class CustomerChange extends AbstractPersistable {

	@ManyToOne
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "changeId", nullable = false)
	private Change change;

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Change getChange() {
		return this.change;
	}

	public void setChange(Change change) {
		this.change = change;
	}

}
