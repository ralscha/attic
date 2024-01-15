package ch.ess.tools.schemacompare.dbobj;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author sr
 */
public class DbExportedKey extends DbObject {

  private String pkColumnName;
  private String fkColumnName;
  private short sequence;
  private short updateRule;
  private short deleteRule;
  private String fkTableName;
  private String fkName;
  private String pkName;
  private short deferrability;

  public DbExportedKey(String pkColumnName, String fkTableName, String fkColumnName, short sequence, short updateRule, short deleteRule,
      String fkName, String pkName, short deferrability) {

    super(pkColumnName + fkTableName + fkColumnName);
    this.pkColumnName = pkColumnName;
    this.fkColumnName = fkColumnName;
    this.sequence = sequence;
    this.updateRule = updateRule;
    this.deleteRule = deleteRule;
    this.fkTableName = fkTableName;
    this.fkName = fkName;
    this.pkName = pkName;
    this.deferrability = deferrability;
  }

  public short getDeferrability() {
    return deferrability;
  }

  public void setDeferrability(short deferrability) {
    this.deferrability = deferrability;
  }

  public short getDeleteRule() {
    return deleteRule;
  }

  public void setDeleteRule(short deleteRule) {
    this.deleteRule = deleteRule;
  }

  public String getFkColumnName() {
    return fkColumnName;
  }

  public void setFkColumnName(String fkColumnName) {
    this.fkColumnName = fkColumnName;
  }

  public String getFkName() {
    return fkName;
  }

  public void setFkName(String fkName) {
    this.fkName = fkName;
  }

  public String getPkColumnName() {
    return pkColumnName;
  }

  public void setPkColumnName(String pkColumnName) {
    this.pkColumnName = pkColumnName;
  }

  public String getPkName() {
    return pkName;
  }

  public void setPkName(String pkName) {
    this.pkName = pkName;
  }

  public short getSequence() {
    return sequence;
  }

  public void setSequence(short sequence) {
    this.sequence = sequence;
  }

  public short getUpdateRule() {
    return updateRule;
  }

  public void setUpdateRule(short updateRule) {
    this.updateRule = updateRule;
  }
  
  
  
  
  public String getFkTableName() {
    return fkTableName;
  }
  public void setFkTableName(String fkTableName) {
    this.fkTableName = fkTableName;
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