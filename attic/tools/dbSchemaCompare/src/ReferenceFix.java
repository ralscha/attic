

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class ReferenceFix {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }

    Connection conn = null;

    try {

      conn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.20.197:1433/casetest", "sa", "papa8gei");

      DatabaseMetaData dmb = conn.getMetaData();
      String[] types = {"TABLE"};
      ResultSet rs = dmb.getTables(null, null, "%", types);


      List alterList = new ArrayList();
      
      while (rs.next()) {

        //String schema = rs.getString("TABLE_SCHEM");
        String name = rs.getString("TABLE_NAME");
        
        
        Set compareSet = new HashSet();


        ResultSet rs2 = dmb.getExportedKeys(null, null, name);
        while (rs2.next()) {
                
          String pkColumnName = rs2.getString("PKCOLUMN_NAME"); 
          String fkTableName = rs2.getString("FKTABLE_NAME"); 
          String fkColumnName = rs2.getString("FKCOLUMN_NAME");
          String fkName = rs2.getString("FK_NAME");
          
          CompareObject co = new CompareObject();
          co.setPkColumnName(pkColumnName);
          co.setFkTableName(fkTableName);
          co.setFkColumnName(fkColumnName);
          
          if (compareSet.contains(co)) {
            String sqlStmt = "ALTER TABLE " + fkTableName + " DROP CONSTRAINT " + fkName;
            alterList.add(sqlStmt);
          } else {
            compareSet.add(co);
          }
        } 
        rs2.close();
        
      }

      rs.close();
      
      
      Statement stmt = conn.createStatement();
      for (Iterator it = alterList.iterator(); it.hasNext();) {
        String sqlStmt = (String)it.next();
        
        System.out.println("EXECUTE: " + sqlStmt);
        stmt.executeUpdate(sqlStmt);
        
      }
      
      stmt.close();
      
      
      conn.close();

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

class CompareObject {

  private String pkColumnName;
  private String fkTableName;
  private String fkColumnName;

  public String getFkColumnName() {
    return fkColumnName;
  }

  public void setFkColumnName(String fkColumnName) {
    this.fkColumnName = fkColumnName;
  }

  public String getFkTableName() {
    return fkTableName;
  }

  public void setFkTableName(String fkTableName) {
    this.fkTableName = fkTableName;
  }

  public String getPkColumnName() {
    return pkColumnName;
  }

  public void setPkColumnName(String pkColumnName) {
    this.pkColumnName = pkColumnName;
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
