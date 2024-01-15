package ch.ess.issue.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import ch.ess.issue.enums.IssueStatus;

@Entity
@Table(name = "issue")
public class Issue extends AbstractEntity {

  @Column(length=500)
  private String summary;
  
  @Lob
  private String description;
  
  @ManyToOne
  @JoinColumn(name = "userReporterId", nullable = false)    
  private User reporter;

  @ManyToOne
  @JoinColumn(name = "userAssigneeId", nullable = false)      
  private User assignee;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date createDate;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date changeDate;
  
  @ManyToOne
  @JoinColumn(name = "componentId", nullable = false)  
  private Component component;
  
  @ManyToOne
  @JoinColumn(name = "typeId", nullable = false)    
  private IssueType type;
  
  @Enumerated(EnumType.STRING)
  @Column(length=30)
  private IssueStatus status;
  
  @ManyToOne
  @JoinColumn(name = "priorityLevelId", nullable = false)
  private PriorityLevel priorityLevel;
  
  @ManyToOne
  @JoinColumn(name = "resolutionId", nullable = false)
  private Resolution resolution;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public User getReporter() {
    return reporter;
  }

  public void setReporter(User reporter) {
    this.reporter = reporter;
  }

  public User getAssignee() {
    return assignee;
  }

  public void setAssignee(User assignee) {
    this.assignee = assignee;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getChangeDate() {
    return changeDate;
  }

  public void setChangeDate(Date changeDate) {
    this.changeDate = changeDate;
  }

  public Component getComponent() {
    return component;
  }

  public void setComponent(Component component) {
    this.component = component;
  }

  public IssueType getType() {
    return type;
  }

  public void setType(IssueType type) {
    this.type = type;
  }

  public IssueStatus getStatus() {
    return status;
  }

  public void setStatus(IssueStatus status) {
    this.status = status;
  }

  public PriorityLevel getPriorityLevel() {
    return priorityLevel;
  }

  public void setPriorityLevel(PriorityLevel priorityLevel) {
    this.priorityLevel = priorityLevel;
  }

  public Resolution getResolution() {
    return resolution;
  }

  public void setResolution(Resolution resolution) {
    this.resolution = resolution;
  }

}
