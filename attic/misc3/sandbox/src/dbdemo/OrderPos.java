package dbdemo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrderPos implements Serializable {

    /** identifier field */
    private String orderPosId;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private java.math.BigDecimal no;

    /** nullable persistent field */
    private java.math.BigDecimal price;

    /** persistent field */
    private dbdemo.OrderTotal order;

    /** full constructor */
    public OrderPos(java.lang.String description, java.math.BigDecimal no, java.math.BigDecimal price, dbdemo.OrderTotal order) {
        this.description = description;
        this.no = no;
        this.price = price;
        this.order = order;
    }

    /** default constructor */
    public OrderPos() {
    }

    /** minimal constructor */
    public OrderPos(dbdemo.OrderTotal order) {
        this.order = order;
    }

    public java.lang.String getOrderPosId() {
        return this.orderPosId;
    }

    public void setOrderPosId(java.lang.String orderPosId) {
        this.orderPosId = orderPosId;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.math.BigDecimal getNo() {
        return this.no;
    }

    public void setNo(java.math.BigDecimal no) {
        this.no = no;
    }

    public java.math.BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }

    public dbdemo.OrderTotal getOrder() {
        return this.order;
    }

    public void setOrder(dbdemo.OrderTotal order) {
        this.order = order;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("orderPosId", getOrderPosId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrderPos) ) return false;
        OrderPos castOther = (OrderPos) other;
        return new EqualsBuilder()
            .append(this.getOrderPosId(), castOther.getOrderPosId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getOrderPosId())
            .toHashCode();
    }

}
