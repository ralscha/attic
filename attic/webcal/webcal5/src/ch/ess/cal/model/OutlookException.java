package ch.ess.cal.model;

import javax.persistence.Entity;


@Entity
public class OutlookException extends Recurrence{

  private long originalDate;
  private Boolean deleted;
  private long startDate;
  private long endDate;
  private Boolean allDay;
  private String sensitivity;
  private String importance;
  private String description;
  private String location;
  private String subject;
  private String uid;
  private long createDate;
  private long modificationDate;
  private Recurrence r;
  
public long getOriginalDate() {
	return originalDate;
}
public void setOriginalDate(long originalDate) {
	this.originalDate = originalDate;
}
public Boolean getDeleted() {
	return deleted;
}
public void setDeleted(Boolean deleted) {
	this.deleted = deleted;
}
public long getStartDate() {
	return startDate;
}
public void setStartDate(long startDate) {
	this.startDate = startDate;
}
public long getEndDate() {
	return endDate;
}
public void setEndDate(long endDate) {
	this.endDate = endDate;
}
public Boolean getAllDay() {
	return allDay;
}
public void setAllDay(Boolean allDay) {
	this.allDay = allDay;
}
public String getSensitivity() {
	return sensitivity;
}
public void setSensitivity(String sensitivity) {
	this.sensitivity = sensitivity;
}
public String getImportance() {
	return importance;
}
public void setImportance(String importance) {
	this.importance = importance;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public long getCreateDate() {
	return createDate;
}
public void setCreateDate(long createDate) {
	this.createDate = createDate;
}
public long getModificationDate() {
	return modificationDate;
}
public void setModificationDate(long modificationDate) {
	this.modificationDate = modificationDate;
}
 
 
}
