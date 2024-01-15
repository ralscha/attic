package ch.ess.cal.db;

import ch.ess.common.db.Persistent;

/** A business entity class representing an Attachment
 * 
 * @author Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calAttachment" lazy="true"
 */
public class Attachment extends Persistent {

  private String fileName;
  private long fileSize;
  private String contentType;
  private String description;
  private Event event;

  /**
   * @hibernate.property length="255" not-null="true"
   */
  public String getFileName() {
    return this.fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * @hibernate.property not-null="true"
   */
  public long getFileSize() {
    return this.fileSize;
  }

  public void setFileSize(long fileSize) {
    this.fileSize = fileSize;
  }

  /**
   * @hibernate.property length="50" not-null="true"
   */
  public String getContentType() {
    return this.contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   * @hibernate.property length="1000" not-null="false"
   */
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
  * @hibernate.many-to-one class="ch.ess.cal.db.Event"
  * @hibernate.column name="eventId" not-null="true" index="FK_Attachment_Event"  
   */
  
  
  public Event getEvent() {
    return this.event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

}
