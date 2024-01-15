package ch.ess.cal.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EventProperty implements Serializable {

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String propValue;

    /** full constructor */
    public EventProperty(java.lang.String name, java.lang.String propValue) {
        this.name = name;
        this.propValue = propValue;
    }

    /** default constructor */
    public EventProperty() {
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
            .toString();
    }

}
