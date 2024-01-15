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
@Table(name = "comments")
public class Comments extends AbstractEntity {

  @Temporal(TemporalType.TIMESTAMP)
  private Date commentDate;
  
  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private User user;
  
  @ManyToOne
  @JoinColumn(name = "issueId", nullable = false)
  private Issue issue;

  @Lob
  private String comment;

  
  
  public Date getCommentDate() {
    return commentDate;
  }

  public void setCommentDate(Date commentDate) {
    this.commentDate = commentDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Issue getIssue() {
    return issue;
  }

  public void setIssue(Issue issue) {
    this.issue = issue;
  }

}
