package ch.ess.cal.db;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ResourceGroup implements Serializable {

    /** identifier field */
    private Long resourceGroupId;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private int version;

    /** persistent field */
    private Set resources;

    /** persistent field */
    private Set departments;

    /** persistent field */
    private Set users;

    /** full constructor */
    public ResourceGroup(java.lang.String name, java.lang.String description, int version, Set resources, Set departments, Set users) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.resources = resources;
        this.departments = departments;
        this.users = users;
    }

    /** default constructor */
    public ResourceGroup() {
    }

    /** minimal constructor */
    public ResourceGroup(java.lang.String name, Set resources, Set departments, Set users) {
        this.name = name;
        this.resources = resources;
        this.departments = departments;
        this.users = users;
    }

    public java.lang.Long getResourceGroupId() {
        return this.resourceGroupId;
    }

    public void setResourceGroupId(java.lang.Long resourceGroupId) {
        this.resourceGroupId = resourceGroupId;
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

    public java.util.Set getResources() {
        return this.resources;
    }

    public void setResources(java.util.Set resources) {
        this.resources = resources;
    }

    public java.util.Set getDepartments() {
        return this.departments;
    }

    public void setDepartments(java.util.Set departments) {
        this.departments = departments;
    }

    public java.util.Set getUsers() {
        return this.users;
    }

    public void setUsers(java.util.Set users) {
        this.users = users;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("resourceGroupId", getResourceGroupId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ResourceGroup) ) return false;
        ResourceGroup castOther = (ResourceGroup) other;
        return new EqualsBuilder()
            .append(this.getResourceGroupId(), castOther.getResourceGroupId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getResourceGroupId())
            .toHashCode();
    }

}
