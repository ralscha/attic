

import java.sql.*;
import java.io.*;

public class ODBCTests {

	static {
		try {
      Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			System.err.println(e);
		} 
	}
	
	

	private Connection openConnection() throws SQLException {
    String dbURL = "jdbc:postgresql://localhost/testdb";
		String dbUser = "sr";
		String dbPassword = "";
		return DriverManager.getConnection(dbURL, dbUser, dbPassword);
	}
	
	public void test() throws Exception {
		try {
			Connection conn = openConnection();

      /*
      PreparedStatement ps = conn.prepareStatement("UPDATE benutzer set picture3 = ?,tt=? where benutzerid = 2");
      FileInputStream bis = new FileInputStream("Scrap.kpx");
      byte[] b = new byte[bis.available()];
      System.out.println(bis.available());
      bis.read(b, 0, b.length);
      

      ByteArrayInputStream bais = new ByteArrayInputStream(b);
      
      ps.setBinaryStream(1, bais, b.length);
      
      java.io.FileReader fr = new java.io.FileReader("PrintBits.java");
      ps.setCharacterStream(2, fr, 200);
      

      ps.executeUpdate();
      conn.commit();
      fr.close();
      bis.close();
*/
			ResultSet rs = null;
			Statement stmt = conn.createStatement();
			try {
        rs = stmt.executeQuery("SELECT * FROM test");
				while(rs.next()) {
          /*
          //Clob c = rs.getClob("picture3");
          //System.out.println(c.length());
          System.out.println(rs.getObject("picture3").getClass());
          System.out.println(rs.getObject("tt").getClass());
					System.out.println(rs.getString("BenutzerId"));
					*/
          System.out.println(rs.getString("TestId"));
          System.out.println(rs.getString("Text"));
				}
			} catch (SQLException sqle) {
			 sqle.printStackTrace();
				System.err.println(sqle);
			}
			
			if (rs != null) {
				rs.close();
			}
			
			if (stmt != null) {
				stmt.close();
			}
			
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}
	}


	public static void main(String[] args) {
    try {
		  new ODBCTests().test();
    } catch (Exception e) {
      System.err.println(e);
    }
	}

}