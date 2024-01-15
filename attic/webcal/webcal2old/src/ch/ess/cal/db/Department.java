package ch.ess.cal.db;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Department implements Serializable {

    /** identifier field */
    private Long departmentId;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private int version;

    /** persistent field */
    private List emails;

    /** persistent field */
    private Set configuration;

    /** persistent field */
    private Set users;

    /** persistent field */
    private Set accessUsers;

    /** persistent field */
    private Set resourceGroups;

    /** persistent field */
    private Set events;

    /** full constructor */
    public Department(java.lang.String name, java.lang.String description, int version, List emails, Set configuration, Set users, Set accessUsers, Set resourceGroups, Set events) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.emails = emails;
        this.configuration = configuration;
        this.users = users;
        this.accessUsers = accessUsers;
        this.resourceGroups = resourceGroups;
        this.events = events;
    }

    /** default constructor */
    public Department() {
    }

    /** minimal constructor */
    public Department(java.lang.String name, List emails, Set configuration, Set users, Set accessUsers, Set resourceGroups, Set events) {
        this.name = name;
        this.emails = emails;
        this.configuration = configuration;
        this.users = users;
        this.accessUsers = accessUsers;
        this.resourceGroups = resourceGroups;
        this.events = events;
    }

    public java.lang.Long getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(java.lang.Long departmentId) {
        this.departmentId = departmentId;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public java.util.Set getUsers() {
        return this.users;
    }

    public void setUsers(java.util.Set users) {
        this.users = users;
    }

    public java.util.Set getAccessUsers() {
        return this.accessUsers;
    }

    public void setAccessUsers(java.util.Set accessUsers) {
        this.accessUsers = accessUsers;
    }

    public java.util.Set getResourceGroups() {
        return this.resourceGroups;
    }

    public void setResourceGroups(java.util.Set resourceGroups) {
        this.resourceGroups = resourceGroups;
    }

    public java.util.Set getEvents() {
        return this.events;
    }

    public void setEvents(java.util.Set events) {
        this.events = events;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("departmentId", getDepartmentId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Department) ) return false;
        Department castOther = (Department) other;
        return new EqualsBuilder()
            .append(this.getDepartmentId(), castOther.getDepartmentId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getDepartmentId())
            .toHashCode();
    }

}
