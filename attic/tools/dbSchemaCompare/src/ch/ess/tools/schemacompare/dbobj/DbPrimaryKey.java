package ch.ess.tools.schemacompare.dbobj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author sr
 */
public class DbPrimaryKey extends DbObject {

  private String columnName;
  private short sequence;

  public DbPrimaryKey(String columnName, String primaryKeyName, short sequence) {
    super(primaryKeyName);

    this.columnName = columnName;
    this.sequence = sequence;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public short getSequence() {
    return sequence;
  }

  public void setSequence(short sequence) {
    this.sequence = sequence;
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