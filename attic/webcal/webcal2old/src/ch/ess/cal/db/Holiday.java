package ch.ess.cal.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Holiday implements Serializable {

    /** identifier field */
    private Long holidayId;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String colour;

    /** nullable persistent field */
    private String country;

    /** nullable persistent field */
    private Integer month;

    /** nullable persistent field */
    private int dayOfMonth;

    /** nullable persistent field */
    private int dayOfWeek;

    /** nullable persistent field */
    private int fromYear;

    /** nullable persistent field */
    private int toYear;

    /** persistent field */
    private boolean builtin;

    /** persistent field */
    private boolean active;

    /** nullable persistent field */
    private int version;

    /** full constructor */
    public Holiday(java.lang.String name, java.lang.String description, java.lang.String colour, java.lang.String country, java.lang.Integer month, int dayOfMonth, int dayOfWeek, int fromYear, int toYear, boolean builtin, boolean active, int version) {
        this.name = name;
        this.description = description;
        this.colour = colour;
        this.country = country;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.fromYear = fromYear;
        this.toYear = toYear;
        this.builtin = builtin;
        this.active = active;
        this.version = version;
    }

    /** default constructor */
    public Holiday() {
    }

    /** minimal constructor */
    public Holiday(java.lang.String name, boolean builtin, boolean active) {
        this.name = name;
        this.builtin = builtin;
        this.active = active;
    }

    public java.lang.Long getHolidayId() {
        return this.holidayId;
    }

    public void setHolidayId(java.lang.Long holidayId) {
        this.holidayId = holidayId;
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

    public java.lang.String getColour() {
        return this.colour;
    }

    public void setColour(java.lang.String colour) {
        this.colour = colour;
    }

    public java.lang.String getCountry() {
        return this.country;
    }

    public void setCountry(java.lang.String country) {
        this.country = country;
    }

    public java.lang.Integer getMonth() {
        return this.month;
    }

    public void setMonth(java.lang.Integer month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return this.dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getFromYear() {
        return this.fromYear;
    }

    public void setFromYear(int fromYear) {
        this.fromYear = fromYear;
    }

    public int getToYear() {
        return this.toYear;
    }

    public void setToYear(int toYear) {
        this.toYear = toYear;
    }

    public boolean isBuiltin() {
        return this.builtin;
    }

    public void setBuiltin(boolean builtin) {
        this.builtin = builtin;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("holidayId", getHolidayId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Holiday) ) return false;
        Holiday castOther = (Holiday) other;
        return new EqualsBuilder()
            .append(this.getHolidayId(), castOther.getHolidayId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getHolidayId())
            .toHashCode();
    }

}
