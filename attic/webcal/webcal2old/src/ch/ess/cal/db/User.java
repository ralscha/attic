package ch.ess.cal.db;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class User implements Serializable {

    /** identifier field */
    private Long userId;

    /** persistent field */
    private String userName;

    /** nullable persistent field */
    private String passwordHash;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String firstName;

    /** persistent field */
    private java.util.Locale locale;

    /** nullable persistent field */
    private String logonToken;

    /** nullable persistent field */
    private java.util.Date logonTokenTime;

    /** nullable persistent field */
    private int version;

    /** nullable persistent field */
    private ch.ess.cal.db.UserGroup userGroup;

    /** persistent field */
    private List emails;

    /** persistent field */
    private Set configuration;

    /** persistent field */
    private Set events;

    /** persistent field */
    private Set accessDepartments;

    /** persistent field */
    private Set departments;

    /** persistent field */
    private Set resourceGroups;

    /** full constructor */
    public User(java.lang.String userName, java.lang.String passwordHash, java.lang.String name, java.lang.String firstName, java.util.Locale locale, java.lang.String logonToken, java.util.Date logonTokenTime, int version, ch.ess.cal.db.UserGroup userGroup, List emails, Set configuration, Set events, Set accessDepartments, Set departments, Set resourceGroups) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.name = name;
        this.firstName = firstName;
        this.locale = locale;
        this.logonToken = logonToken;
        this.logonTokenTime = logonTokenTime;
        this.version = version;
        this.userGroup = userGroup;
        this.emails = emails;
        this.configuration = configuration;
        this.events = events;
        this.accessDepartments = accessDepartments;
        this.departments = departments;
        this.resourceGroups = resourceGroups;
    }

    /** default constructor */
    public User() {
    }

    /** minimal constructor */
    public User(java.lang.String userName, java.lang.String name, java.util.Locale locale, List emails, Set configuration, Set events, Set accessDepartments, Set departments, Set resourceGroups) {
        this.userName = userName;
        this.name = name;
        this.locale = locale;
        this.emails = emails;
        this.configuration = configuration;
        this.events = events;
        this.accessDepartments = accessDepartments;
        this.departments = departments;
        this.resourceGroups = resourceGroups;
    }

    public java.lang.Long getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.Long userId) {
        this.userId = userId;
    }

    public java.lang.String getUserName() {
        return this.userName;
    }

    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    public java.lang.String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(java.lang.String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }

    public java.util.Locale getLocale() {
        return this.locale;
    }

    public void setLocale(java.util.Locale locale) {
        this.locale = locale;
    }

    public java.lang.String getLogonToken() {
        return this.logonToken;
    }

    public void setLogonToken(java.lang.String logonToken) {
        this.logonToken = logonToken;
    }

    public java.util.Date getLogonTokenTime() {
        return this.logonTokenTime;
    }

    public void setLogonTokenTime(java.util.Date logonTokenTime) {
        this.logonTokenTime = logonTokenTime;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ch.ess.cal.db.UserGroup getUserGroup() {
        return this.userGroup;
    }

    public void setUserGroup(ch.ess.cal.db.UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public java.util.List getEmails() {
        return this.emails;
    }

    public void setEmails(java.util.List emails) {
        this.emails = emails;
    }

    public java.util.Set getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(java.util.Set configuration) {
        this.configuration = configuration;
    }

    public java.util.Set getEvents() {
        return this.events;
    }

    public void setEvents(java.util.Set events) {
        this.events = events;
    }

    public java.util.Set getAccessDepartments() {
        return this.accessDepartments;
    }

    public void setAccessDepartments(java.util.Set accessDepartments) {
        this.accessDepartments = accessDepartments;
    }

    public java.util.Set getDepartments() {
        return this.departments;
    }

    public void setDepartments(java.util.Set departments) {
        this.departments = departments;
    }

    public java.util.Set getResourceGroups() {
        return this.resourceGroups;
    }

    public void setResourceGroups(java.util.Set resourceGroups) {
        this.resourceGroups = resourceGroups;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("userId", getUserId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof User) ) return false;
        User castOther = (User) other;
        return new EqualsBuilder()
            .append(this.getUserId(), castOther.getUserId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUserId())
            .toHashCode();
    }

}
