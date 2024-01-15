package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.ess.base.model.BaseObject;
import ch.ess.base.model.Document;



@Entity
public class Attachment extends BaseObject {

  private Document document;
  private String description;
  private BaseEvent event;

  public Document getDocument() {
    return document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  @Column(length = 1000)
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @ManyToOne
  @JoinColumn(name = "eventId", nullable = false)
  public BaseEvent getEvent() {
    return this.event;
  }

  public void setEvent(BaseEvent event) {
    this.event = event;
  }

}
