

import java.sql.*;
import java.io.*;
import java.util.*;
import java.math.*;
 
public class InitDb {
	private Statement statement;
	private Connection con;
	private PreparedStatement insertPS;
	
	private final static String insertSQL = "INSERT INTO Bookings VALUES(?,?,?,?,?,?)";
	
	static {
		try {
			Class.forName("oracle.lite.poljdbc.POLJDBCDriver");
		} catch (Exception e) {
			System.err.println(e);
		} 
	}


	public static void main(String[] args) {
		try {
			if (args.length == 1) {
				InitDb isd = new InitDb();
				isd.openDb();
				isd.selectCount();
				/*
				
				if ("DROP".equalsIgnoreCase(args[0]))
					isd.dropTables();
				else if ("CREATE".equalsIgnoreCase(args[0]))
					isd.createTables();
				else if ("INSERT".equalsIgnoreCase(args[0])) {
					isd.insertPS = isd.con.prepareStatement(insertSQL);
					isd.importData("book_rz2h.dat");
					isd.importData("book_rz2r.dat");
					isd.importData("book_rz3h.dat");
					isd.importData("book_rz3r.dat");
				}
				
				*/
				isd.shutDown();
			}
		} catch (Exception sqle) {
			System.err.println(sqle);
		}
	}

	void shutDown() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
   				con.commit();
				con.close();
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
	}
	
	
	public void openDb() throws SQLException {		
		String url = "jdbc:Polite:STAT";
		String user = "SYSTEM";
		String pw = "SYSTEM";
		con = DriverManager.getConnection(url, user, pw);
		statement = con.createStatement();
		

	} 

	
	private void createTables() {
		try {
			System.out.println(statement.executeUpdate("CREATE TABLE Bookings("+
							"accountno char(16),"+
							"bookdate  char(8),"+
							"text      char(35),"+
							"valdate   char(8),"+
							"type      char(1),"+
							"amount    decimal(15,3))"));
							
							
			statement.executeUpdate("CREATE INDEX bookings_ix ON Bookings(accountno)");
			System.out.println("Create Table successful");
			
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public void dropTables() {
		try {
			statement.executeUpdate("DROP INDEX bookings_ix");
			statement.executeUpdate("DROP TABLE Bookings");
			
			
			System.out.println("Drop Table successful");
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	public void importData(String fileName) throws IOException, SQLException {
		String line;

		System.out.println(fileName);
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		while((line = br.readLine()) != null) {
			if (line.length() > 100) {
				String acctno = line.substring(0,19).trim();
				acctno = gtf.common.AccountFormat.format(acctno, gtf.common.AccountFormat.INTERN);

				String bookdate = line.substring(20,28);
				String text     = line.substring(30,65);
				String valdate  = line.substring(100, 108);
				String sbetrags = line.substring(66,82).trim();
				String hbetrags = line.substring(83,99).trim();

				insertPS.setString(1, acctno);
				insertPS.setString(2, bookdate);
				insertPS.setString(3, text);
				insertPS.setString(4, valdate);

				
				BigDecimal amount = null;
				if (sbetrags.length() == 0) {
					amount = new BigDecimal(hbetrags);
					insertPS.setString(5, "C");
				} else {
					amount = new BigDecimal(sbetrags);
					insertPS.setString(5, "D");
				}
				insertPS.setBigDecimal(6, amount);
				insertPS.executeUpdate();
			}
		}
		br.close();
	}
	
	public void selectCount() throws SQLException {
		ResultSet rs = statement.executeQuery("select count(*) from bookings");
		rs.next();
		System.out.println(rs.getString(1));
	}
	
		
}