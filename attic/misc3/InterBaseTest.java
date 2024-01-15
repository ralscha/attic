
import java.sql.*;

public class InterBaseTest {

  public static void main(String[] args) {
    try {
      Class.forName ("interbase.interclient.Driver");
      
      java.sql.Connection c =
      DriverManager.getConnection ("jdbc:interbase://127.0.0.1/e:/test.gdb","sysdba", "masterkey");
      
      Statement stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery("select * from personalien");
      
      while(rs.next()) {
        System.out.println(rs.getInt(1));
        System.out.println(rs.getString(2));
      }
      
      rs.close();
      stmt.close();
      c.close();
      
    } catch (Exception e) {
      System.err.println(e);
    }  
    
  }
    
}