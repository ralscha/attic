package ch.ess.issue.entity;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "changelog")
public class Changelog extends AbstractEntity {

  @Temporal(TemporalType.TIMESTAMP)
  private Date changeDate;
 
  private String field;
  
  @Lob
  private String oldValue;
 
  @Lob
  private String newValue;
  
  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)  
  private User user;
  
  @ManyToOne
  @JoinColumn(name = "issueId", nullable = false)
  private Issue issue;

  public Date getChangeDate() {
    return changeDate;
  }

  public void setChangeDate(Date changeDate) {
    this.changeDate = changeDate;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getOldValue() {
    return oldValue;
  }

  public void setOldValue(String oldValue) {
    this.oldValue = oldValue;
  }

  public String getNewValue() {
    return newValue;
  }

  public void setNewValue(String newValue) {
    this.newValue = newValue;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Issue getIssue() {
    return issue;
  }

  public void setIssue(Issue issue) {
    this.issue = issue;
  }

}
