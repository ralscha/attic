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
public class TimeTask extends BaseObject {

  private String name;
  private String description;
  private boolean active;
  private BigDecimal hourRate;
  private TimeProject timeProject;
  private Set<Time> times = new HashSet<Time>();
  private Set<TimeTaskBudget> budgets = new HashSet<TimeTaskBudget>();
  
  @Column(length = 255)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Column(length = 255, nullable=false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ManyToOne
  @JoinColumn(name = "timeProjektId", nullable = false)
  public TimeProject getTimeProject() {
    return timeProject;
  }

  public void setTimeProject(TimeProject project) {
    this.timeProject = project;
  }
  
  @Column(nullable=false)
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean aktiv) {
    this.active = aktiv;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeTask")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<Time> getTimes() {
    return times;
  }

  public void setTimes(Set<Time> times) {
    this.times = times;
  }

  @Column(precision = 18, scale = 3)
  public BigDecimal getHourRate() {
    return hourRate;
  }
  
  public void setHourRate(final BigDecimal hourRate) {
    this.hourRate = hourRate;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeTask")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)  
  public Set<TimeTaskBudget> getBudgets() {
    return budgets;
  }

  
  public void setBudgets(Set<TimeTaskBudget> budgets) {
    this.budgets = budgets;
  }

}
