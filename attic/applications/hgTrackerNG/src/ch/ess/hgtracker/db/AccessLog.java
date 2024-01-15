package ch.ess.hgtracker.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "accessLog")
public class AccessLog implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "clubId")
  private Club club;

  @Column(length = 50)
  private String sessionId;

  @Temporal(TemporalType.TIMESTAMP)
  private Date logIn;

  @Temporal(TemporalType.TIMESTAMP)
  private Date logOut;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Club getClub() {
    return club;
  }

  public void setClub(Club club) {
    this.club = club;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Date getLogIn() {
    return logIn;
  }

  public void setLogIn(Date logIn) {
    this.logIn = logIn;
  }

  public Date getLogOut() {
    return logOut;
  }

  public void setLogOut(Date logOut) {
    this.logOut = logOut;
  }

  @Override
  public int hashCode() {
    if (id != null) {
      return id.hashCode();
    }
    return super.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof AccessLog)) {
      return false;
    }

    AccessLog other = (AccessLog)obj;
    if ((other.getId() == null) || (getId() == null)) {
      return super.equals(obj);
    }

    return other.getId().equals(getId());

  }
}
