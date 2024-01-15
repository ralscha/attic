package ch.ess.cal.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class DepartmentConfiguration implements Serializable {

    /** identifier field */
    private Long departmentConfigurationId;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String propValue;

    /** persistent field */
    private ch.ess.cal.db.Department department;

    /** full constructor */
    public DepartmentConfiguration(java.lang.String name, java.lang.String propValue, ch.ess.cal.db.Department department) {
        this.name = name;
        this.propValue = propValue;
        this.department = department;
    }

    /** default constructor */
    public DepartmentConfiguration() {
    }

    /** minimal constructor */
    public DepartmentConfiguration(java.lang.String name, ch.ess.cal.db.Department department) {
        this.name = name;
        this.department = department;
    }

    public java.lang.Long getDepartmentConfigurationId() {
        return this.departmentConfigurationId;
    }

    public void setDepartmentConfigurationId(java.lang.Long departmentConfigurationId) {
        this.departmentConfigurationId = departmentConfigurationId;
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

    public ch.ess.cal.db.Department getDepartment() {
        return this.department;
    }

    public void setDepartment(ch.ess.cal.db.Department department) {
        this.department = department;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("departmentConfigurationId", getDepartmentConfigurationId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof DepartmentConfiguration) ) return false;
        DepartmentConfiguration castOther = (DepartmentConfiguration) other;
        return new EqualsBuilder()
            .append(this.getDepartmentConfigurationId(), castOther.getDepartmentConfigurationId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getDepartmentConfigurationId())
            .toHashCode();
    }

}
