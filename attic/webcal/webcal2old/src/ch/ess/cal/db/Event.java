package ch.ess.cal.db;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Event implements Serializable {

    /** identifier field */
    private Long eventId;

    /** persistent field */
    private long startDate;

    /** nullable persistent field */
    private long endDate;

    /** persistent field */
    private boolean allDay;

    /** persistent field */
    private ch.ess.cal.db.SensitivityEnum sensitivity;

    /** persistent field */
    private ch.ess.cal.db.ImportanceEnum importance;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String location;

    /** nullable persistent field */
    private String subject;

    /** nullable persistent field */
    private String uid;

    /** nullable persistent field */
    private long createDate;

    /** nullable persistent field */
    private long modificationDate;

    /** nullable persistent field */
    private String calVersion;

    /** persistent field */
    private int sequence;

    /** persistent field */
    private int priority;

    /** nullable persistent field */
    private int version;

    /** nullable persistent field */
    private ch.ess.cal.db.Resource resource;

    /** nullable persistent field */
    private ch.ess.cal.db.Department department;

    /** persistent field */
    private Set properties;

    /** persistent field */
    private Set users;

    /** persistent field */
    private Set recurrences;

    /** persistent field */
    private Set reminders;

    /** persistent field */
    private Set attachments;

    /** persistent field */
    private Set categories;

    /** full constructor */
    public Event(long startDate, long endDate, boolean allDay, ch.ess.cal.db.SensitivityEnum sensitivity, ch.ess.cal.db.ImportanceEnum importance, java.lang.String description, java.lang.String location, java.lang.String subject, java.lang.String uid, long createDate, long modificationDate, java.lang.String calVersion, int sequence, int priority, int version, ch.ess.cal.db.Resource resource, ch.ess.cal.db.Department department, Set properties, Set users, Set recurrences, Set reminders, Set attachments, Set categories) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.allDay = allDay;
        this.sensitivity = sensitivity;
        this.importance = importance;
        this.description = description;
        this.location = location;
        this.subject = subject;
        this.uid = uid;
        this.createDate = createDate;
        this.modificationDate = modificationDate;
        this.calVersion = calVersion;
        this.sequence = sequence;
        this.priority = priority;
        this.version = version;
        this.resource = resource;
        this.department = department;
        this.properties = properties;
        this.users = users;
        this.recurrences = recurrences;
        this.reminders = reminders;
        this.attachments = attachments;
        this.categories = categories;
    }

    /** default constructor */
    public Event() {
    }

    /** minimal constructor */
    public Event(long startDate, boolean allDay, ch.ess.cal.db.SensitivityEnum sensitivity, ch.ess.cal.db.ImportanceEnum importance, int sequence, int priority, Set properties, Set users, Set recurrences, Set reminders, Set attachments, Set categories) {
        this.startDate = startDate;
        this.allDay = allDay;
        this.sensitivity = sensitivity;
        this.importance = importance;
        this.sequence = sequence;
        this.priority = priority;
        this.properties = properties;
        this.users = users;
        this.recurrences = recurrences;
        this.reminders = reminders;
        this.attachments = attachments;
        this.categories = categories;
    }

    public java.lang.Long getEventId() {
        return this.eventId;
    }

    public void setEventId(java.lang.Long eventId) {
        this.eventId = eventId;
    }

    public long getStartDate() {
        return this.startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return this.endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public boolean isAllDay() {
        return this.allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public ch.ess.cal.db.SensitivityEnum getSensitivity() {
        return this.sensitivity;
    }

    public void setSensitivity(ch.ess.cal.db.SensitivityEnum sensitivity) {
        this.sensitivity = sensitivity;
    }

    public ch.ess.cal.db.ImportanceEnum getImportance() {
        return this.importance;
    }

    public void setImportance(ch.ess.cal.db.ImportanceEnum importance) {
        this.importance = importance;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getLocation() {
        return this.location;
    }

    public void setLocation(java.lang.String location) {
        this.location = location;
    }

    public java.lang.String getSubject() {
        return this.subject;
    }

    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }

    public java.lang.String getUid() {
        return this.uid;
    }

    public void setUid(java.lang.String uid) {
        this.uid = uid;
    }

    public long getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getModificationDate() {
        return this.modificationDate;
    }

    public void setModificationDate(long modificationDate) {
        this.modificationDate = modificationDate;
    }

    public java.lang.String getCalVersion() {
        return this.calVersion;
    }

    public void setCalVersion(java.lang.String calVersion) {
        this.calVersion = calVersion;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ch.ess.cal.db.Resource getResource() {
        return this.resource;
    }

    public void setResource(ch.ess.cal.db.Resource resource) {
        this.resource = resource;
    }

    public ch.ess.cal.db.Department getDepartment() {
        return this.department;
    }

    public void setDepartment(ch.ess.cal.db.Department department) {
        this.department = department;
    }

    public java.util.Set getProperties() {
        return this.properties;
    }

    public void setProperties(java.util.Set properties) {
        this.properties = properties;
    }

    public java.util.Set getUsers() {
        return this.users;
    }

    public void setUsers(java.util.Set users) {
        this.users = users;
    }

    public java.util.Set getRecurrences() {
        return this.recurrences;
    }

    public void setRecurrences(java.util.Set recurrences) {
        this.recurrences = recurrences;
    }

    public java.util.Set getReminders() {
        return this.reminders;
    }

    public void setReminders(java.util.Set reminders) {
        this.reminders = reminders;
    }

    public java.util.Set getAttachments() {
        return this.attachments;
    }

    public void setAttachments(java.util.Set attachments) {
        this.attachments = attachments;
    }

    public java.util.Set getCategories() {
        return this.categories;
    }

    public void setCategories(java.util.Set categories) {
        this.categories = categories;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("eventId", getEventId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Event) ) return false;
        Event castOther = (Event) other;
        return new EqualsBuilder()
            .append(this.getEventId(), castOther.getEventId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getEventId())
            .toHashCode();
    }

}
