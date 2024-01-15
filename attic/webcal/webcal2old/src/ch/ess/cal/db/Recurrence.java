package ch.ess.cal.db;

import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Recurrence implements Serializable {

    /** identifier field */
    private Long recurrenceId;

    /** persistent field */
    private boolean exclude;

    /** persistent field */
    private ch.ess.cal.db.RecurrenceTypeEnum type;

    /** nullable persistent field */
    private int interval;

    /** nullable persistent field */
    private int dayOfWeekMask;

    /** nullable persistent field */
    private int dayOfMonth;

    /** nullable persistent field */
    private int monthOfYear;

    /** nullable persistent field */
    private int instance;

    /** nullable persistent field */
    private int occurrences;

    /** nullable persistent field */
    private int duration;

    /** persistent field */
    private boolean always;

    /** nullable persistent field */
    private long until;

    /** nullable persistent field */
    private String rfcRule;

    /** persistent field */
    private ch.ess.cal.db.Event event;

    /** persistent field */
    private Map days;

    /** full constructor */
    public Recurrence(boolean exclude, ch.ess.cal.db.RecurrenceTypeEnum type, int interval, int dayOfWeekMask, int dayOfMonth, int monthOfYear, int instance, int occurrences, int duration, boolean always, long until, java.lang.String rfcRule, ch.ess.cal.db.Event event, Map days) {
        this.exclude = exclude;
        this.type = type;
        this.interval = interval;
        this.dayOfWeekMask = dayOfWeekMask;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.instance = instance;
        this.occurrences = occurrences;
        this.duration = duration;
        this.always = always;
        this.until = until;
        this.rfcRule = rfcRule;
        this.event = event;
        this.days = days;
    }

    /** default constructor */
    public Recurrence() {
    }

    /** minimal constructor */
    public Recurrence(boolean exclude, ch.ess.cal.db.RecurrenceTypeEnum type, boolean always, ch.ess.cal.db.Event event, Map days) {
        this.exclude = exclude;
        this.type = type;
        this.always = always;
        this.event = event;
        this.days = days;
    }

    public java.lang.Long getRecurrenceId() {
        return this.recurrenceId;
    }

    public void setRecurrenceId(java.lang.Long recurrenceId) {
        this.recurrenceId = recurrenceId;
    }

    public boolean isExclude() {
        return this.exclude;
    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    public ch.ess.cal.db.RecurrenceTypeEnum getType() {
        return this.type;
    }

    public void setType(ch.ess.cal.db.RecurrenceTypeEnum type) {
        this.type = type;
    }

    public int getInterval() {
        return this.interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getDayOfWeekMask() {
        return this.dayOfWeekMask;
    }

    public void setDayOfWeekMask(int dayOfWeekMask) {
        this.dayOfWeekMask = dayOfWeekMask;
    }

    public int getDayOfMonth() {
        return this.dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonthOfYear() {
        return this.monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public int getInstance() {
        return this.instance;
    }

    public void setInstance(int instance) {
        this.instance = instance;
    }

    public int getOccurrences() {
        return this.occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isAlways() {
        return this.always;
    }

    public void setAlways(boolean always) {
        this.always = always;
    }

    public long getUntil() {
        return this.until;
    }

    public void setUntil(long until) {
        this.until = until;
    }

    public java.lang.String getRfcRule() {
        return this.rfcRule;
    }

    public void setRfcRule(java.lang.String rfcRule) {
        this.rfcRule = rfcRule;
    }

    public ch.ess.cal.db.Event getEvent() {
        return this.event;
    }

    public void setEvent(ch.ess.cal.db.Event event) {
        this.event = event;
    }

    public java.util.Map getDays() {
        return this.days;
    }

    public void setDays(java.util.Map days) {
        this.days = days;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("recurrenceId", getRecurrenceId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Recurrence) ) return false;
        Recurrence castOther = (Recurrence) other;
        return new EqualsBuilder()
            .append(this.getRecurrenceId(), castOther.getRecurrenceId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRecurrenceId())
            .toHashCode();
    }

}
