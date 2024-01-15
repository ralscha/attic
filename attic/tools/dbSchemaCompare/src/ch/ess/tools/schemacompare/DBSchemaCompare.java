package ch.ess.tools.schemacompare;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ch.ess.tools.schemacompare.compare.TableComparer;
import ch.ess.tools.schemacompare.dbobj.DbColumn;
import ch.ess.tools.schemacompare.dbobj.DbExportedKey;
import ch.ess.tools.schemacompare.dbobj.DbIndex;
import ch.ess.tools.schemacompare.dbobj.DbPrimaryKey;
import ch.ess.tools.schemacompare.dbobj.DbTable;

/**
 * @author sr
 */
public class DBSchemaCompare {
  public static void main(String[] args) {

    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }

    Connection conn = null;

    try {

      conn = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/ct9b", "sa", "");
      Map<String, DbTable> newTable = readTables(conn);
      conn.close();
      
      conn = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/ct9", "sa", "");
      Map<String, DbTable> oldTable = readTables(conn);
      conn.close();
      conn = null;      
      
            
      TableComparer comparer = new TableComparer(oldTable, newTable);
      comparer.compare(new PrintWriter(new OutputStreamWriter(System.out)));

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

  }

  public static Map<String, DbTable> readTables(Connection conn) throws SQLException {
    DatabaseMetaData dmb = conn.getMetaData();
    String[] types = {"TABLE"};
    ResultSet rs = dmb.getTables(null, null, "%", types);

    Map<String, DbTable> tableMap = new HashMap<String, DbTable>();

    while (rs.next()) {

      String schema = rs.getString("TABLE_SCHEM");
      String name = rs.getString("TABLE_NAME");
      DbTable table = new DbTable(schema, name);
      readTableColumns(dmb, table);
      readTableIndexes(dmb, table);
      readTablePK(dmb, table);
      readExportedKeys(dmb, table);
      tableMap.put(name, table);
    }

    rs.close();
    
    return tableMap;
  }
  
  private static void readTableColumns(DatabaseMetaData dmb, DbTable table) throws SQLException {
    ResultSet rs = dmb.getColumns(null, null, table.getName(), "%");
    while (rs.next()) {
            
      String columnName = rs.getString("COLUMN_NAME"); 
      String datatype = rs.getString("TYPE_NAME"); 
      int datasize = rs.getInt("COLUMN_SIZE"); 
      int digits = rs.getInt("DECIMAL_DIGITS"); 
      int nullable = rs.getInt("NULLABLE"); 
      boolean isNull = (nullable == 1); 
      DbColumn newColumn = new DbColumn(columnName, datatype, datasize, digits, isNull);  
      table.addColumn(newColumn); 
    } 
      
  }
  
  private static void readTableIndexes(DatabaseMetaData dmb, DbTable table) throws SQLException {
    ResultSet rs = dmb.getIndexInfo(null, null, table.getName(), false, false);
    while (rs.next()) {
                   
      String indexName = rs.getString("INDEX_NAME"); 
      if (indexName != null) {
        boolean nonUnique = rs.getBoolean("NON_UNIQUE");
        String qualifier = rs.getString("INDEX_QUALIFIER");
        short type = rs.getShort("TYPE");
        
        String columnName = rs.getString("COLUMN_NAME");
        short ordinal = rs.getShort("ORDINAL_POSITION");
        String ascDesc = rs.getString("ASC_OR_DESC");
                
        DbIndex newIndex = new DbIndex(indexName, columnName, qualifier, nonUnique, type, ordinal, ascDesc);  
        table.addIndex(newIndex);
      }
    }       
  }
  
  private static void readTablePK(DatabaseMetaData dmb, DbTable table) throws SQLException {
    ResultSet rs = dmb.getPrimaryKeys(null, null, table.getName());
    while (rs.next()) {
            
      String columnName = rs.getString("COLUMN_NAME"); 
      String pkName = rs.getString("PK_NAME"); 
      short keySeq = rs.getShort("KEY_SEQ");
      
      
      DbPrimaryKey newPK = new DbPrimaryKey(columnName, pkName, keySeq);  
      table.addPrimaryKey(newPK); 
    } 
      
  }

  private static void readExportedKeys(DatabaseMetaData dmb, DbTable table) throws SQLException {
    ResultSet rs = dmb.getExportedKeys(null, null, table.getName());
    while (rs.next()) {
            
      String pkColumnName = rs.getString("PKCOLUMN_NAME"); 
      String fkTableName = rs.getString("FKTABLE_NAME"); 
      String fkColumnName = rs.getString("FKCOLUMN_NAME");
      short keySeq = rs.getShort("KEY_SEQ");
      short updateRule = rs.getShort("UPDATE_RULE");
      short deleteRule = rs.getShort("DELETE_RULE");
      String fkName = rs.getString("FK_NAME");
      String pkName = rs.getString("PK_NAME");
      short deferrability = rs.getShort("DEFERRABILITY");
      
      DbExportedKey newExportedKey = new DbExportedKey(pkColumnName, fkTableName, fkColumnName, keySeq, updateRule, deleteRule, fkName, pkName, deferrability);  
      table.addExporedKey(newExportedKey); 
    } 
      
  }

  
}