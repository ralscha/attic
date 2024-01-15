

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import ch.ess.tools.schemacompare.DBSchemaCompare;
import ch.ess.tools.schemacompare.dbobj.DbIndex;
import ch.ess.tools.schemacompare.dbobj.DbTable;

/**
 * @author sr
 */
public class WriteIndex {
  public static void main(String[] args) {

    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }

    Connection conn = null;

    try {

      conn = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/ct57", "sa", "papa8gei");
      Map<String, DbTable> newTable = DBSchemaCompare.readTables(conn);
      conn.close();
     
      
      for (String tableName : newTable.keySet()) {        
        DbTable table = newTable.get(tableName);
        Map<String,DbIndex> indexes = table.getIndexes();
        
        for (DbIndex index : indexes.values()) {
          if (!index.getIndexName().startsWith("PK_")) {
            System.out.printf("<createIndex indexName=\"%s\" tableName=\"%s\" unique=\"%b\">\n", index.getIndexName(), tableName, !index.isNonUnique());
            
            System.out.printf("<column name=\"%s\"/>\n", index.getColumnName());
            System.out.println("</createIndex>");
            System.out.println();
          }
        }
        
      }
      
      
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

  

  
}