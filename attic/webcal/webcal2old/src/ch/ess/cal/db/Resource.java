package ch.ess.cal.db;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Resource implements Serializable {

    /** identifier field */
    private Long resourceId;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private int version;

    /** persistent field */
    private ch.ess.cal.db.ResourceGroup resourceGroup;

    /** persistent field */
    private Set events;

    /** full constructor */
    public Resource(java.lang.String name, java.lang.String description, int version, ch.ess.cal.db.ResourceGroup resourceGroup, Set events) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.resourceGroup = resourceGroup;
        this.events = events;
    }

    /** default constructor */
    public Resource() {
    }

    /** minimal constructor */
    public Resource(java.lang.String name, ch.ess.cal.db.ResourceGroup resourceGroup, Set events) {
        this.name = name;
        this.resourceGroup = resourceGroup;
        this.events = events;
    }

    public java.lang.Long getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(java.lang.Long resourceId) {
        this.resourceId = resourceId;
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

    public ch.ess.cal.db.ResourceGroup getResourceGroup() {
        return this.resourceGroup;
    }

    public void setResourceGroup(ch.ess.cal.db.ResourceGroup resourceGroup) {
        this.resourceGroup = resourceGroup;
    }

    public java.util.Set getEvents() {
        return this.events;
    }

    public void setEvents(java.util.Set events) {
        this.events = events;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("resourceId", getResourceId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Resource) ) return false;
        Resource castOther = (Resource) other;
        return new EqualsBuilder()
            .append(this.getResourceId(), castOther.getResourceId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getResourceId())
            .toHashCode();
    }

}
