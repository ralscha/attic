package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import ch.ess.base.model.BaseObject;

@Entity
public class RecurrenceException extends BaseObject{
    private Boolean exAllDay;
    private long exStartDate;
    private long exEndDate;
    private String exSubject;
    private String uid;
    private long originalDate;
    private Boolean deleted;
    
    @Column(nullable = true)
	public Boolean getExAllDay() {
		return exAllDay;
	}
    @Column(nullable = true)
	public void setExAllDay(Boolean exAllDay) {
		this.exAllDay = exAllDay;
	}
	
    @Column(nullable = true)
	public long getExStartDate() {
		return exStartDate;
	}
    @Column(nullable = true)
	public void setExStartDate(long exStartDate) {
		this.exStartDate = exStartDate;
	}
	
    @Column(nullable = true)
	public long getExEndDate() {
		return exEndDate;
	}
    @Column(nullable = true)
	public void setExEndDate(long exEndDate) {
		this.exEndDate = exEndDate;
	}
	
    @Column(nullable = true)
	public String getExSubject() {
		return exSubject;
	}
    @Column(nullable = true)
	public void setExSubject(String exSubject) {
		this.exSubject = exSubject;
	}
	
    @Column(nullable = true)
	public long getOriginalDate() {
		return originalDate;
	}
    @Column(nullable = true)
	public void setOriginalDate(long originalDate) {
		this.originalDate = originalDate;
	}
	
    @Column(nullable = true)
	public Boolean getDeleted() {
		return deleted;
	}
    @Column(nullable = true)
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
    @Column(nullable = true)
	public String getUid() {
		return uid;
	}
    @Column(nullable = true)
	public void setUid(String uid) {
		this.uid = uid;
	}
}
