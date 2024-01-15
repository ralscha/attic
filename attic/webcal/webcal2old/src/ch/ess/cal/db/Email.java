package ch.ess.cal.db;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Email implements Serializable {

    /** identifier field */
    private Long emailId;

    /** persistent field */
    private int sequence;

    /** persistent field */
    private String email;

    /** persistent field */
    private boolean defaultEmail;

    /** nullable persistent field */
    private ch.ess.cal.db.User user;

    /** nullable persistent field */
    private ch.ess.cal.db.Department department;

    /** persistent field */
    private Set reminders;

    /** full constructor */
    public Email(int sequence, java.lang.String email, boolean defaultEmail, ch.ess.cal.db.User user, ch.ess.cal.db.Department department, Set reminders) {
        this.sequence = sequence;
        this.email = email;
        this.defaultEmail = defaultEmail;
        this.user = user;
        this.department = department;
        this.reminders = reminders;
    }

    /** default constructor */
    public Email() {
    }

    /** minimal constructor */
    public Email(int sequence, java.lang.String email, boolean defaultEmail, Set reminders) {
        this.sequence = sequence;
        this.email = email;
        this.defaultEmail = defaultEmail;
        this.reminders = reminders;
    }

    public java.lang.Long getEmailId() {
        return this.emailId;
    }

    public void setEmailId(java.lang.Long emailId) {
        this.emailId = emailId;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public java.lang.String getEmail() {
        return this.email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public boolean isDefaultEmail() {
        return this.defaultEmail;
    }

    public void setDefaultEmail(boolean defaultEmail) {
        this.defaultEmail = defaultEmail;
    }

    public ch.ess.cal.db.User getUser() {
        return this.user;
    }

    public void setUser(ch.ess.cal.db.User user) {
        this.user = user;
    }

    public ch.ess.cal.db.Department getDepartment() {
        return this.department;
    }

    public void setDepartment(ch.ess.cal.db.Department department) {
        this.department = department;
    }

    public java.util.Set getReminders() {
        return this.reminders;
    }

    public void setReminders(java.util.Set reminders) {
        this.reminders = reminders;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("emailId", getEmailId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Email) ) return false;
        Email castOther = (Email) other;
        return new EqualsBuilder()
            .append(this.getEmailId(), castOther.getEmailId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getEmailId())
            .toHashCode();
    }

}
