package ch.ess.cal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import ch.ess.base.model.BaseObject;
import ch.ess.base.model.User;
import ch.ess.cal.enums.ImportanceEnum;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.enums.TransparencyEnum;


@Entity
@Table(name="Event")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="typ", length=1, discriminatorType=DiscriminatorType.CHAR)
@DiscriminatorValue("A")
public class BaseEvent extends BaseObject {

  private Long startDate;
  private Long endDate;
  private Boolean allDay;
  private SensitivityEnum sensitivity;
  private ImportanceEnum importance;
  private String description;
  private String location;
  private String subject;
  private String uid;
  private Long createDate;
  private Long modificationDate;
  private Integer sequence;
  private Integer priority;
  private TransparencyEnum transparency;
  private Resource resource;
  private Group group;
  private String categoryId;
  private String oldUid;
  private Boolean isSynched;
  private Boolean deleted;
  private Set<EventProperty> eventProperties = new HashSet<EventProperty>();
  private Set<User> users = new HashSet<User>();

  private Set<EventCategory> eventCategories = new HashSet<EventCategory>();

  private Set<Recurrence> recurrences = new HashSet<Recurrence>();
  private Set<Reminder> reminders = new HashSet<Reminder>();
  private Set<Attachment> attachments = new HashSet<Attachment>();

  public BaseEvent(){
	  deleted = false;
  }
  public Long getStartDate() {
    return this.startDate;
  }

  public void setStartDate(Long startDate) {
    this.startDate = startDate;
  }

  public Long getEndDate() {
    return this.endDate;
  }

  public void setEndDate(Long endDate) {
    this.endDate = endDate;
  }

  
  public Boolean isAllDay() {
    return this.allDay;
  }

  public void setAllDay(Boolean allDay) {
    this.allDay = allDay;
  }

  @Column(length=1)
  @Type(type="ch.ess.base.hibernate.StringValuedEnumType",
        parameters = { @Parameter( name="enum", value = "ch.ess.cal.enums.SensitivityEnum" ) } )
  public SensitivityEnum getSensitivity() {
    return this.sensitivity;
  }

  public void setSensitivity(SensitivityEnum sensitivity) {
    this.sensitivity = sensitivity;
  }

  @Column(length=1)
  @Type(type="ch.ess.base.hibernate.StringValuedEnumType", 
        parameters = { @Parameter( name="enum", value = "ch.ess.cal.enums.ImportanceEnum" ) } )
  public ImportanceEnum getImportance() {
    return this.importance;
  }

  public void setImportance(ImportanceEnum importance) {
    this.importance = importance;
  }

  @Column(length = 1000)
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Column(length = 255)
  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Column(length = 255)
  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Column(length = 255)
  public String getUid() {
    return this.uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public Long getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(Long createDate) {
    this.createDate = createDate;
  }

  public Long getModificationDate() {
    return this.modificationDate;
  }

  public void setModificationDate(Long modificationDate) {
    this.modificationDate = modificationDate;
  }

  
  public Integer getSequence() {
    return this.sequence;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  
  public Integer getPriority() {
    return this.priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  @Column(length=1)
  @Type(type="ch.ess.base.hibernate.StringValuedEnumType", 
        parameters = { @Parameter( name="enum", value = "ch.ess.cal.enums.TransparencyEnum" ) } )  
  public TransparencyEnum getTransparency() {
    return transparency;
  }

  public void setTransparency(TransparencyEnum transparency) {
    this.transparency = transparency;
  }

  @ManyToOne
  @JoinColumn(name = "resourceId")
  public Resource getResource() {
    return this.resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  @ManyToOne
  @JoinColumn(name = "groupId")
  public Group getGroup() {
    return this.group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<EventProperty> getEventProperties() {
    return eventProperties;
  }

  public void setEventProperties(Set<EventProperty> eventProperties) {
    this.eventProperties = eventProperties;
  }

  @ManyToMany
  @JoinTable(name = "UserEvents", 
      joinColumns = @JoinColumn(name = "eventId", nullable = false), 
      inverseJoinColumns = @JoinColumn(name = "userId", nullable = false))
  public Set<User> getUsers() {
    return this.users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<Recurrence> getRecurrences() {
    return this.recurrences;
  }

  public void setRecurrences(Set<Recurrence> recurrences) {
    this.recurrences = recurrences;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<Reminder> getReminders() {
    return this.reminders;
  }

  public void setReminders(Set<Reminder> reminders) {
    this.reminders = reminders;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<Attachment> getAttachments() {
    return this.attachments;
  }

  public void setAttachments(Set<Attachment> attachments) {
    this.attachments = attachments;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<EventCategory> getEventCategories() {
    return eventCategories;
  }

  public void setEventCategories(Set<EventCategory> eventCategories) {
    this.eventCategories = eventCategories;
  }
  
@Transient
public String getCategoryId() {
	return categoryId;
}

public void setCategoryId(String categoryId) {
	this.categoryId = categoryId;
}

public Boolean getIsSynched() {
	return isSynched;
}

public void setIsSynched(Boolean isSynched) {
	this.isSynched = isSynched;
}

public Boolean getDeleted() {
	return deleted;
}

public void setDeleted(Boolean deleted) {
	this.deleted = deleted;
}
public String getOldUid() {
	return oldUid;
}
public void setOldUid(String oldUid) {
	this.oldUid = oldUid;
}

}
