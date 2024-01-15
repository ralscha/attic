package ch.ess.tools.schemacompare.dbobj;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class DbTable extends DbObject {
  private String schema;

  private Map<String, DbColumn> columns;
  private Map<String, DbIndex> indexes;
  private Map<String, DbPrimaryKey> primaryKeys;
  private Map<String, DbExportedKey> exportedKeys;
    
  public DbTable(String schema, String tableName) {
    super(tableName);
    this.schema = schema;
  }


  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }
  
  public void addColumn(DbColumn column) {
    if (columns == null) {
      columns = new HashMap<String, DbColumn>();
    }
    
    columns.put(column.getName(), column);
  }
  
  public void addIndex(DbIndex index) {
    if (indexes == null) {
      indexes = new HashMap<String, DbIndex>();
    }
    indexes.put(index.getName(), index);
  }
 
  public void addPrimaryKey(DbPrimaryKey key) {
    if (primaryKeys == null) {
      primaryKeys = new HashMap<String, DbPrimaryKey>();
    }
    primaryKeys.put(key.getName(), key);
  }
  
  public void addExporedKey(DbExportedKey key) {
    if (exportedKeys == null) {
      exportedKeys = new HashMap<String, DbExportedKey>();
    }
    exportedKeys.put(key.getName(), key);
  }
  
  public Map<String, DbColumn> getColumns() {
    if (columns == null) {
      columns = new HashMap<String, DbColumn>();
    }
    return columns;
  }
  
  public Map<String, DbIndex> getIndexes() {
    if (indexes == null) {
      indexes = new HashMap<String, DbIndex>();
    }
    return indexes;
  }
  
  public Map<String, DbPrimaryKey> getPrimaryKeys() {
    if (primaryKeys == null) {
      primaryKeys = new HashMap<String, DbPrimaryKey>();
    }
    return primaryKeys;
  }
 
  public Map<String, DbExportedKey> getExportedKeys() {
    if (exportedKeys == null) {
      exportedKeys = new HashMap<String, DbExportedKey>();
    }
    return exportedKeys;
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