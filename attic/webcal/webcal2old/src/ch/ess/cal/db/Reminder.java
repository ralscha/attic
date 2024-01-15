package ch.ess.cal.db;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Reminder implements Serializable {

    /** identifier field */
    private Long reminderId;

    /** persistent field */
    private int minutesBefore;

    /** persistent field */
    private ch.ess.cal.db.Event event;

    /** persistent field */
    private Set emails;

    /** full constructor */
    public Reminder(int minutesBefore, ch.ess.cal.db.Event event, Set emails) {
        this.minutesBefore = minutesBefore;
        this.event = event;
        this.emails = emails;
    }

    /** default constructor */
    public Reminder() {
    }

    public java.lang.Long getReminderId() {
        return this.reminderId;
    }

    public void setReminderId(java.lang.Long reminderId) {
        this.reminderId = reminderId;
    }

    public int getMinutesBefore() {
        return this.minutesBefore;
    }

    public void setMinutesBefore(int minutesBefore) {
        this.minutesBefore = minutesBefore;
    }

    public ch.ess.cal.db.Event getEvent() {
        return this.event;
    }

    public void setEvent(ch.ess.cal.db.Event event) {
        this.event = event;
    }

    public java.util.Set getEmails() {
        return this.emails;
    }

    public void setEmails(java.util.Set emails) {
        this.emails = emails;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("reminderId", getReminderId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Reminder) ) return false;
        Reminder castOther = (Reminder) other;
        return new EqualsBuilder()
            .append(this.getReminderId(), castOther.getReminderId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getReminderId())
            .toHashCode();
    }

}
