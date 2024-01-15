import java.sql.*;

public class JDataConnectTest {

  public static void main(String[] args) {
    try  {
      Class.forName ("JData2_0.sql.$Driver");
      // Change MyDSN, myUsername and myPassword to your specific DSN
      Connection c =java.sql.DriverManager.getConnection(
        "jdbc:JDataConnect://127.0.0.1/PlannerDb2");
      // Change MyTable to your specific table
      PreparedStatement s = c.prepareStatement("SELECT * FROM Users");
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        System.out.println(rs.getString(1));
      }
    }
    catch (Exception e) {
      System.out.println("Error:"+e.getMessage());
    }
  }
}
