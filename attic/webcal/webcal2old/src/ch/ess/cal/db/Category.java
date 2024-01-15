package ch.ess.cal.db;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Category implements Serializable {

    /** identifier field */
    private Long categoryId;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** persistent field */
    private String icalKey;

    /** persistent field */
    private String colour;

    /** persistent field */
    private String bwColour;

    /** persistent field */
    private boolean unknown;

    /** nullable persistent field */
    private int version;

    /** persistent field */
    private Set events;

    /** full constructor */
    public Category(java.lang.String name, java.lang.String description, java.lang.String icalKey, java.lang.String colour, java.lang.String bwColour, boolean unknown, int version, Set events) {
        this.name = name;
        this.description = description;
        this.icalKey = icalKey;
        this.colour = colour;
        this.bwColour = bwColour;
        this.unknown = unknown;
        this.version = version;
        this.events = events;
    }

    /** default constructor */
    public Category() {
    }

    /** minimal constructor */
    public Category(java.lang.String name, java.lang.String icalKey, java.lang.String colour, java.lang.String bwColour, boolean unknown, Set events) {
        this.name = name;
        this.icalKey = icalKey;
        this.colour = colour;
        this.bwColour = bwColour;
        this.unknown = unknown;
        this.events = events;
    }

    public java.lang.Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(java.lang.Long categoryId) {
        this.categoryId = categoryId;
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

    public java.lang.String getIcalKey() {
        return this.icalKey;
    }

    public void setIcalKey(java.lang.String icalKey) {
        this.icalKey = icalKey;
    }

    public java.lang.String getColour() {
        return this.colour;
    }

    public void setColour(java.lang.String colour) {
        this.colour = colour;
    }

    public java.lang.String getBwColour() {
        return this.bwColour;
    }

    public void setBwColour(java.lang.String bwColour) {
        this.bwColour = bwColour;
    }

    public boolean isUnknown() {
        return this.unknown;
    }

    public void setUnknown(boolean unknown) {
        this.unknown = unknown;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public java.util.Set getEvents() {
        return this.events;
    }

    public void setEvents(java.util.Set events) {
        this.events = events;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("categoryId", getCategoryId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Category) ) return false;
        Category castOther = (Category) other;
        return new EqualsBuilder()
            .append(this.getCategoryId(), castOther.getCategoryId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCategoryId())
            .toHashCode();
    }

}
