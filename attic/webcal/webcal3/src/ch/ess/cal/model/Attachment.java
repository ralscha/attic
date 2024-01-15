package ch.ess.cal.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** A business entity class representing an Attachment
 * 
 * @author sr
 * @version $Revision: 1.5 $ $Date: 2005/04/04 11:31:12 $
 */

@Entity
public class Attachment extends BaseObject {

  private String fileName;
  private Blob content;
  private String contentType;
  private String description;
  private Event event;

  @Column(nullable = false, length = 255)
  public String getFileName() {
    return this.fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Column(nullable = false)
  public Blob getContent() {
    return this.content;
  }

  public void setContent(Blob content) {
    this.content = content;
  }

  @Column(nullable = false, length = 50)
  public String getContentType() {
    return this.contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
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
  public Event getEvent() {
    return this.event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

}
