import java.net.URL;
import java.sql.*;

class SimpleSelect {


	static {
		try {
			// Load the db driver
			Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (Exception e) {
			System.err.println(e);
		}
	}


	// CHANGE!!
	// Dies muss natürlich entsprechend angepasst werden
	// my-dsn entspricht dem Eintrag im Settings->Control Panel->ODBC
	private static String url   = "jdbc:odbc:my-dsn";
	private static String query = "SELECT * FROM emp";
	private static String userid = "my-user";
	private static String password = "my-password";

	public static void main (String args[]) {

		try {
         // Attempt to connect to a driver.  Each one
         // of the registered drivers will be loaded until
         // one is found that can process this URL

         Connection con = DriverManager.getConnection(url, userid, password);

	     // If we were unable to connect, an exception
	     // would have been thrown.  So, if we get here,
	     // we are successfully connected to the URL


	     // Create a Statement object so we can submit
	     // SQL statements to the driver

         Statement stmt = con.createStatement();

         // Submit a query, creating a ResultSet object
         ResultSet rs = stmt.executeQuery (query);

         // Display all columns and rows from the result set
		 	// CHANGE!!
			while(rs.next()) {
				String col1 = rs.getString(1);
				Date col2 = rs.getDate(2);
			}

         // Close the result set
         rs.close();

         // Close the statement
         stmt.close();

         // Close the connection
         con.close();
		
		} catch (SQLException ex) {

             // A SQLException was generated.  Catch it and
             // display the error information.  Note that there
             // could be multiple error objects chained
             // together

             System.out.println ("\n*** SQLException caught ***\n");

             while (ex != null) {
	             System.out.println ("SQLState: " + ex.getSQLState ());
	             System.out.println ("Message:  " + ex.getMessage ());
	             System.out.println ("Vendor:   " + ex.getErrorCode ());
	             ex = ex.getNextException ();
	             System.out.println ("");
             }
         } catch (java.lang.Exception ex) {
	         // Got some other type of exception.  Dump it.
	         ex.printStackTrace ();
		 }
     }

}