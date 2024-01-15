package ch.ess.cal.db;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Permission implements Serializable {

    /** identifier field */
    private Long permissionId;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private int version;

    /** persistent field */
    private Set userGroups;

    /** full constructor */
    public Permission(java.lang.String name, int version, Set userGroups) {
        this.name = name;
        this.version = version;
        this.userGroups = userGroups;
    }

    /** default constructor */
    public Permission() {
    }

    /** minimal constructor */
    public Permission(java.lang.String name, Set userGroups) {
        this.name = name;
        this.userGroups = userGroups;
    }

    public java.lang.Long getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(java.lang.Long permissionId) {
        this.permissionId = permissionId;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public java.util.Set getUserGroups() {
        return this.userGroups;
    }

    public void setUserGroups(java.util.Set userGroups) {
        this.userGroups = userGroups;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("permissionId", getPermissionId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Permission) ) return false;
        Permission castOther = (Permission) other;
        return new EqualsBuilder()
            .append(this.getPermissionId(), castOther.getPermissionId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getPermissionId())
            .toHashCode();
    }

}
