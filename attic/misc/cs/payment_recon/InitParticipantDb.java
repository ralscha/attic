

import java.sql.*;
import java.io.*;
import java.util.*;
import java.math.*;
 
public class InitParticipantDb {
	private Statement statement;
	private Connection con;
	
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
				InitParticipantDb isd = new InitParticipantDb();
				isd.openDb();
				if ("DROP".equalsIgnoreCase(args[0]))
					isd.dropTables();
				else if ("CREATE".equalsIgnoreCase(args[0]))
					isd.createTables();
					
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
		String url = "jdbc:Polite:GTFNAB";
		String user = "SYSTEM";
		String pw = "SYSTEM";
		con = DriverManager.getConnection(url, user, pw);
		statement = con.createStatement();
		

	} 

	
	private void createTables() {
		
		try {
			System.out.println(statement.executeUpdate("CREATE TABLE PARTICIPANT("+
					"Recordid				CHAR(5)	 NOT NULL  ,"+
					"Oid				INTEGER NOT NULL Primary Key ,"+
					"Dossier_Item_Oid				INTEGER NOT NULL  ,"+
					"Dossier_Oid				INTEGER NOT NULL  ,"+
					"Le_Oid				INTEGER ,"+
					"Address_Oid				INTEGER NOT NULL  ,"+
					"Role_Code				CHAR(2)	 NOT NULL  ,"+
					"Role_Seq_Num				SMALLINT NOT NULL  ,"+
					"Contact_Person				VARCHAR(65)	 NOT NULL  ,"+
					"Reference_Number				VARCHAR(30)	 NOT NULL  ,"+
					"Our_Charges_To				CHAR(2)	 NOT NULL  ,"+
					"Received_From_Role				CHAR(2)	 NOT NULL  ,"+
					"Language				CHAR(3)	 NOT NULL  ,"+
					"Updated_By				CHAR(8)	 NOT NULL  ,"+
					"Update_Ts				TIMESTAMP NOT NULL  		)"));
							
							
			statement.executeUpdate("create  index PART_ITEM_OID_SX on PARTICIPANT (DOSSIER_ITEM_OID)");
			System.out.println("Create Table successful");
			
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public void dropTables() {
		try {
			statement.executeUpdate("DROP INDEX PART_ITEM_OID_SX");

			statement.executeUpdate("DROP TABLE PARTICIPANT");
			
			
			System.out.println("Drop Table successful");
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	
		
}