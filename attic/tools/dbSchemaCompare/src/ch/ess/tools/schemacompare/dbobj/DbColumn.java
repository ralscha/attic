package ch.ess.tools.schemacompare.dbobj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author sr
 */
public class DbColumn extends DbObject {

  private String datatype;
  private int datasize;
  private int digits;
  boolean nullable;

  public DbColumn(String columnName, String datatype, int datasize, int digits, boolean nullable) {
    super(columnName);
    this.datatype = datatype;
    this.datasize = datasize;
    this.digits = digits;
    this.nullable = nullable;
  }
  
  public int getDatasize() {
    return datasize;
  }

  public void setDatasize(int datasize) {
    this.datasize = datasize;
  }

  public String getDatatype() {
    return datatype;
  }

  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  public int getDigits() {
    return digits;
  }

  public void setDigits(int digits) {
    this.digits = digits;
  }

  public boolean isNullable() {
    return nullable;
  }

  public void setNullable(boolean nullable) {
    this.nullable = nullable;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }  
  
  
  @Override
  public boolean equals(Object obj) {    
    return EqualsBuilder.reflectionEquals(this, obj);
  }
  
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
  
}