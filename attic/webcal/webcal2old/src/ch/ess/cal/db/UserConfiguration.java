package ch.ess.cal.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UserConfiguration implements Serializable {

    /** identifier field */
    private Long userConfigurationId;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String propValue;

    /** persistent field */
    private ch.ess.cal.db.User user;

    /** full constructor */
    public UserConfiguration(java.lang.String name, java.lang.String propValue, ch.ess.cal.db.User user) {
        this.name = name;
        this.propValue = propValue;
        this.user = user;
    }

    /** default constructor */
    public UserConfiguration() {
    }

    /** minimal constructor */
    public UserConfiguration(java.lang.String name, ch.ess.cal.db.User user) {
        this.name = name;
        this.user = user;
    }

    public java.lang.Long getUserConfigurationId() {
        return this.userConfigurationId;
    }

    public void setUserConfigurationId(java.lang.Long userConfigurationId) {
        this.userConfigurationId = userConfigurationId;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getPropValue() {
        return this.propValue;
    }

    public void setPropValue(java.lang.String propValue) {
        this.propValue = propValue;
    }

    public ch.ess.cal.db.User getUser() {
        return this.user;
    }

    public void setUser(ch.ess.cal.db.User user) {
        this.user = user;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("userConfigurationId", getUserConfigurationId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserConfiguration) ) return false;
        UserConfiguration castOther = (UserConfiguration) other;
        return new EqualsBuilder()
            .append(this.getUserConfigurationId(), castOther.getUserConfigurationId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUserConfigurationId())
            .toHashCode();
    }

}
