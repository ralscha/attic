package ch.ess.cal.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Configuration implements Serializable {

    /** identifier field */
    private Long configurationId;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String propValue;

    /** full constructor */
    public Configuration(java.lang.String name, java.lang.String propValue) {
        this.name = name;
        this.propValue = propValue;
    }

    /** default constructor */
    public Configuration() {
    }

    /** minimal constructor */
    public Configuration(java.lang.String name) {
        this.name = name;
    }

    public java.lang.Long getConfigurationId() {
        return this.configurationId;
    }

    public void setConfigurationId(java.lang.Long configurationId) {
        this.configurationId = configurationId;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("configurationId", getConfigurationId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Configuration) ) return false;
        Configuration castOther = (Configuration) other;
        return new EqualsBuilder()
            .append(this.getConfigurationId(), castOther.getConfigurationId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getConfigurationId())
            .toHashCode();
    }

}
