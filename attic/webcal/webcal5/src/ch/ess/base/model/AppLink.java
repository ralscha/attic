package ch.ess.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="app_link")
public class AppLink extends BaseObject {

  private String linkName;
  private String appLink;

  @Column(nullable = false, length = 255)
  public String getLinkName() {
    return linkName;
  }

  public void setLinkName(final String linkName) {
    this.linkName = linkName;
  }

  @Column(nullable = false, length = 255)
  public String getAppLink() {
	return appLink;
  }

  public void setAppLink(String appLink) {
	  this.appLink = appLink;
  }
  
}
