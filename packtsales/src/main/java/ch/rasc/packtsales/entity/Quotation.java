package ch.rasc.packtsales.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Quotation extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	private String note;

	@OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<QuotationItem> quotationItems;

	@OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Bill> bills;

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set<QuotationItem> getQuotationItems() {
		return this.quotationItems;
	}

	public void setQuotationItems(Set<QuotationItem> quotationItems) {
		this.quotationItems = quotationItems;
	}

	public Set<Bill> getBills() {
		return this.bills;
	}

	public void setBills(Set<Bill> bills) {
		this.bills = bills;
	}

}
