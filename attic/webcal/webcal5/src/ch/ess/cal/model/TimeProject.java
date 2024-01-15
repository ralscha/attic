package ch.ess.cal.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import ch.ess.base.model.BaseObject;

@Entity
public class TimeProject extends BaseObject {

  private String name;
  private String description;
  private boolean active;
  private BigDecimal hourRate;
  private String projectNumber;
  private Set<TimeTask> timeTasks = new HashSet<TimeTask>();
  private Set<TimeTaskBudget> budgets = new HashSet<TimeTaskBudget>();
  private Set<Time> times = new HashSet<Time>();
  private TimeCustomer timeCustomer;
  
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

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeProject")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<TimeTask> getTimeTasks() {
    return timeTasks;
  }

  public void setTimeTasks(Set<TimeTask> set) {
    timeTasks = set;
  }
  
  @ManyToOne
  @JoinColumn(name = "timeCustomerId", nullable = false) 
  public TimeCustomer getTimeCustomer() {
    return timeCustomer;
  }

  public void setTimeCustomer(final TimeCustomer c) {
    timeCustomer = c;
  }
    
  @Column(nullable=false)
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean aktiv) {
    this.active = aktiv;
  }
  
  @Column(precision = 18, scale = 3)
  public BigDecimal getHourRate() {
    return hourRate;
  }
  
  public void setHourRate(final BigDecimal hourRate) {
    this.hourRate = hourRate;
  }
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeProject")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)  
  public Set<TimeTaskBudget> getBudgets() {
    return budgets;
  }

  
  public void setBudgets(Set<TimeTaskBudget> budgets) {
    this.budgets = budgets;
  }

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	
	@Column(length = 15)
	public String getProjectNumber() {
		return projectNumber;
	}
	
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeProject")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<Time> getTimes() {
    return times;
  }

  public void setTimes(Set<Time> times) {
    this.times = times;
  }
}
