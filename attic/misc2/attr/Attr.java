
package ch.ess.util.attr;

import java.sql.Timestamp;
import java.math.BigDecimal;


public class Attr {

  protected String name;
  protected Object value = null;

  public Attr(String name) {
    this.name = name;
  }

  public Attr(String name, Object value) {
    this(name);
    this.value = value;
  }

  public Attr(String name, boolean value) {
    this(name);
    this.value = new Boolean(value);
  }

  public Attr(String name, int value) {
    this(name);
    this.value = new Integer(value);
  }

  public Attr(String name, long value) {
    this(name);
    this.value = new Long(value);
  }

  public Attr(String name, float value) {
    this(name);
    this.value = new Double((double) value);
  }

  public Attr(String name, double value) {
    this(name);
    this.value = new Double(value);
  }

  public String getName() {
    return name;
  }

  public Object getValue() {
    return value;
  }

  public boolean isNull() {
    return value == null;
  }


  public boolean getBooleanValue() {
    return ((Boolean) value).booleanValue();
  }

  public int getIntValue() {
    return ((Integer) value).intValue();
  }

  public long getLongValue() {
    return ((Long) value).longValue();
  }

  public float getFloatValue() {
    return ((Float) value).floatValue();
  }

  public double getDoubleValue() {
    return ((Double) value).doubleValue();
  }

  public Boolean getBooleanWrapperValue() {
    return (Boolean)value;
  }

  public Integer getIntWrapperValue() {
    return (Integer)value;
  }

  public Long getLongWrapperValue() {
    return (Long)value;
  }

  public Float getFloatWrapperValue() {
    return (Float)value;
  }

  public Double getDoubleWrapperValue() {
    return (Double)value;
  }

  public String getStringValue() {
    return (String)value;
  }

  public Timestamp getTimestampValue() {
    return (Timestamp) value;
  }

  public BigDecimal getBigDecimalValue() {
    return (BigDecimal) value;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer(name).append("='");
    if (value != null)
      sb.append(value.toString());
    else
      sb.append(value);
    sb.append("'");
    return sb.toString();
  }
}
