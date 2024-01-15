package dbtests;

import java.sql.*;
import java.io.*;

public class DBTests {

	static {
		try {
			Class.forName("jdbc.idbDriver");
		} catch (Exception e) {
			System.err.println(e);
		} 
	}
	
	
	private final static String dropTableSQL = "DROP TABLE ADDRESSES";
	private final static String createTableSQL = "CREATE TABLE ADDRESSES (id INTEGER, firstname CHAR(30)"+
														", name CHAR(30), street CHAR(40), city CHAR(30))";
	private final static String createIxSQL = "CREATE INDEX ADDRESSES_IX ON ADDRESSES (id)";


	private Connection openConnection() throws SQLException {
		String dbURL = "jdbc:idb:e:/test.gdb";
		String dbUser = "sysdba";
		String dbPassword = "masterkey";
		return DriverManager.getConnection(dbURL, dbUser, dbPassword);
	}
	
	public void createDb() {
		try {
			Connection conn = openConnection();
	
			Statement stmt = conn.createStatement ();
			try {
				stmt.executeUpdate(dropTableSQL);
			} catch (SQLException sqle) {
				System.err.println(sqle);
			}
		
			stmt.executeUpdate(createTableSQL);
			stmt.executeUpdate(createIxSQL);
			
			
			if (conn != null) {
				conn.commit();
				conn.close();
			}
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}
	}
	
	
	public void populateData() {
		try {
			Connection conn = openConnection();
			
			BufferedReader reader = new BufferedReader(new FileReader("dbtests/db.data"));
			
			TableBuilder builder = new TableBuilder(conn, reader, ";");
			builder.buildTableInfo();
			builder.buildActiveColumns();
			builder.buildTable();
			
			reader.close();
			conn.commit();
			conn.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void main(String[] args) {
		//new DBTests().createDb();	
		new DBTests().populateData();
	}
}