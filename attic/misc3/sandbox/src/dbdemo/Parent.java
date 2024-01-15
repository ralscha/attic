package dbdemo;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Parent implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private Map childMap;

    /** persistent field */
    private Set child;

    /** full constructor */
    public Parent(Map childMap, Set child) {
        this.childMap = childMap;
        this.child = child;
    }

    /** default constructor */
    public Parent() {
    }

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.util.Map getChildMap() {
        return this.childMap;
    }

    public void setChildMap(java.util.Map childMap) {
        this.childMap = childMap;
    }

    public java.util.Set getChild() {
        return this.child;
    }

    public void setChild(java.util.Set child) {
        this.child = child;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Parent) ) return false;
        Parent castOther = (Parent) other;
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
