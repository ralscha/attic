package ch.ess.cal.db;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UserGroup implements Serializable {

    /** identifier field */
    private Long userGroupId;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private int version;

    /** persistent field */
    private Set users;

    /** persistent field */
    private Set permissions;

    /** full constructor */
    public UserGroup(java.lang.String name, java.lang.String description, int version, Set users, Set permissions) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.users = users;
        this.permissions = permissions;
    }

    /** default constructor */
    public UserGroup() {
    }

    /** minimal constructor */
    public UserGroup(java.lang.String name, Set users, Set permissions) {
        this.name = name;
        this.users = users;
        this.permissions = permissions;
    }

    public java.lang.Long getUserGroupId() {
        return this.userGroupId;
    }

    public void setUserGroupId(java.lang.Long userGroupId) {
        this.userGroupId = userGroupId;
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

    public java.util.Set getUsers() {
        return this.users;
    }

    public void setUsers(java.util.Set users) {
        this.users = users;
    }

    public java.util.Set getPermissions() {
        return this.permissions;
    }

    public void setPermissions(java.util.Set permissions) {
        this.permissions = permissions;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("userGroupId", getUserGroupId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserGroup) ) return false;
        UserGroup castOther = (UserGroup) other;
        return new EqualsBuilder()
            .append(this.getUserGroupId(), castOther.getUserGroupId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUserGroupId())
            .toHashCode();
    }

}
