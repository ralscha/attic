package ch.ess.cal.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import ch.ess.base.model.BaseObject;

@Entity
public class TimeCustomer extends BaseObject {

  private String name;
  private String description;
  private boolean active;
  private BigDecimal hourRate;
  private String customerNumber;
  private Set<TimeProject> timeProjects = new HashSet<TimeProject>();
  private Set<UserTimeCustomer> userTimeCustomers = new HashSet<UserTimeCustomer>();

  @Column(length = 255, nullable=false)
  public String getName() {
    return name;
  }

  public void setName(final String string) {
    name = string;
  }

  @Column(length = 255)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String string) {
    description = string;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeCustomer")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<TimeProject> getTimeProjects() {
    return timeProjects;
  }

  public void setTimeProjects(Set<TimeProject> timeProjects) {
    this.timeProjects = timeProjects;
  }

  @Column(nullable=false)
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean aktiv) {
    this.active = aktiv;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeCustomer")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)  
  public Set<UserTimeCustomer> getUserTimeCustomers() {
    return userTimeCustomers;
  }

  public void setUserTimeCustomers(Set<UserTimeCustomer> userTimeCustomers) {
    this.userTimeCustomers = userTimeCustomers;
  }
    
  @Column(precision = 18, scale = 3)
  public BigDecimal getHourRate() {
    return hourRate;
  }
  
  public void setHourRate(final BigDecimal hourRate) {
    this.hourRate = hourRate;
  }

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	@Column(length = 15)
	public String getCustomerNumber() {
		return customerNumber;
	}
	  
}
