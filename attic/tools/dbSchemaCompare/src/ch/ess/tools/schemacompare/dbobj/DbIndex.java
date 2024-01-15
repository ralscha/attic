package ch.ess.tools.schemacompare.dbobj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author sr
 */
public class DbIndex extends DbObject {

  private String indexName;
  private String columnName;
  private String qualifier;
  private boolean nonUnique;
  private short type;
  private short ordinalPosition;
  private String ascDesc;

  public DbIndex(String indexName, String columnName, String qualifier, boolean nonUnique, short type,
      short ordinalPosition, String ascDesc) {
    super(columnName + "/" + indexName);
    this.indexName = indexName;
    this.columnName = columnName;
    this.qualifier = qualifier;
    this.nonUnique = nonUnique;
    this.type = type;
    this.ordinalPosition = ordinalPosition;
    this.ascDesc = ascDesc;
  }

  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public String getAscDesc() {
    return ascDesc;
  }

  public void setAscDesc(String ascDesc) {
    this.ascDesc = ascDesc;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public boolean isNonUnique() {
    return nonUnique;
  }

  public void setNonUnique(boolean nonUnique) {
    this.nonUnique = nonUnique;
  }

  public short getOrdinalPosition() {
    return ordinalPosition;
  }

  public void setOrdinalPosition(short ordinalPosition) {
    this.ordinalPosition = ordinalPosition;
  }

  public String getQualifier() {
    return qualifier;
  }

  public void setQualifier(String qualifier) {
    this.qualifier = qualifier;
  }

  public short getType() {
    return type;
  }

  public void setType(short type) {
    this.type = type;
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