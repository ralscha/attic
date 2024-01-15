package dbdemo;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrderTotal implements Serializable {

    /** identifier field */
    private String orderId;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private java.math.BigDecimal total;

    /** persistent field */
    private Set orderPos;

    /** full constructor */
    public OrderTotal(java.lang.String description, java.math.BigDecimal total, Set orderPos) {
        this.description = description;
        this.total = total;
        this.orderPos = orderPos;
    }

    /** default constructor */
    public OrderTotal() {
    }

    /** minimal constructor */
    public OrderTotal(Set orderPos) {
        this.orderPos = orderPos;
    }

    public java.lang.String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(java.lang.String orderId) {
        this.orderId = orderId;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.math.BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(java.math.BigDecimal total) {
        this.total = total;
    }

    public java.util.Set getOrderPos() {
        return this.orderPos;
    }

    public void setOrderPos(java.util.Set orderPos) {
        this.orderPos = orderPos;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("orderId", getOrderId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrderTotal) ) return false;
        OrderTotal castOther = (OrderTotal) other;
        return new EqualsBuilder()
            .append(this.getOrderId(), castOther.getOrderId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getOrderId())
            .toHashCode();
    }

}
