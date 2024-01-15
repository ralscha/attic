package dbdemo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Child implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String descr;

    /** persistent field */
    private dbdemo.Parent parent;

    /** full constructor */
    public Child(java.lang.String name, java.lang.String descr, dbdemo.Parent parent) {
        this.name = name;
        this.descr = descr;
        this.parent = parent;
    }

    /** default constructor */
    public Child() {
    }

    /** minimal constructor */
    public Child(dbdemo.Parent parent) {
        this.parent = parent;
    }

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getDescr() {
        return this.descr;
    }

    public void setDescr(java.lang.String descr) {
        this.descr = descr;
    }

    public dbdemo.Parent getParent() {
        return this.parent;
    }

    public void setParent(dbdemo.Parent parent) {
        this.parent = parent;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Child) ) return false;
        Child castOther = (Child) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
