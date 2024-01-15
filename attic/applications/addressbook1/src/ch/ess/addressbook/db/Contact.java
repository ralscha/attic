package ch.ess.addressbook.db;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;

/** @author Hibernate CodeGenerator */
public class Contact implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String category;

    /** nullable persistent field */
    private int version;

    /** persistent field */
    private Map attributes;

    /** full constructor */
    public Contact(java.lang.String category, int version, Map attributes) {
        this.category = category;
        this.version = version;
        this.attributes = attributes;
    }

    /** default constructor */
    public Contact() {
    }

    /** minimal constructor */
    public Contact(java.lang.String category, Map attributes) {
        this.category = category;
        this.attributes = attributes;
    }

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.String getCategory() {
        return this.category;
    }

    public void setCategory(java.lang.String category) {
        this.category = category;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public java.util.Map getAttributes() {
        return this.attributes;
    }

    public void setAttributes(java.util.Map attributes) {
        this.attributes = attributes;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Contact) ) return false;
        Contact castOther = (Contact) other;
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
