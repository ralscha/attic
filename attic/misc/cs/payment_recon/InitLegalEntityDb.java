

import java.sql.*;
import java.io.*;
import java.util.*;
import java.math.*;
 
public class InitLegalEntityDb {
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
				InitLegalEntityDb isd = new InitLegalEntityDb();
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
			System.out.println(statement.executeUpdate("CREATE TABLE LEGAL_ENTITY("+
					"Recordid				CHAR(5)	 NOT NULL  ,"+
					"Oid				INTEGER NOT NULL Primary Key ,"+
					"Not_In_Cif				CHAR(1)	 NOT NULL  ,"+
					"Cif_Number				CHAR(12)	 NOT NULL  ,"+
					"Handle				CHAR(12)	 NOT NULL  ,"+
					"Name				CHAR(70)	 NOT NULL  ,"+
					"City				CHAR(35)	 NOT NULL  ,"+
					"Postal_Code				CHAR(15)	 NOT NULL  ,"+
					"Name_Srch_Key				CHAR(35)	 NOT NULL  ,"+
					"City_Srch_Key				CHAR(35)	 NOT NULL  ,"+
					"Postal_Cd_Srch_Key				CHAR(15)	 NOT NULL  ,"+
					"Language				CHAR(3)	 NOT NULL  ,"+
					"Domicile				CHAR(3)	 NOT NULL  ,"+
					"Nationality				CHAR(3)	 NOT NULL  ,"+
					"Customer_Segment				CHAR(3)	 NOT NULL  ,"+
					"Industry_Type				CHAR(4)	 NOT NULL  ,"+
					"Category				CHAR(2)	 NOT NULL  ,"+
					"Customer_Type				CHAR(3)	 NOT NULL  ,"+
					"Credit_Resp				CHAR(7)	 NOT NULL  ,"+
					"Customer_Resp				CHAR(7)	 NOT NULL  ,"+
					"Branch				CHAR(4)	 NOT NULL  ,"+
					"Clearing_Number				CHAR(11)	 NOT NULL  ,"+
					"Group_Id				CHAR(12)	 NOT NULL  ,"+
					"Business_Unit				CHAR(4)	 NOT NULL  ,"+
					"Updated_By				CHAR(8)	 NOT NULL  ,"+
					"Update_Ts				TIMESTAMP NOT NULL  ,"+
					"Inactiv				CHAR(1)	 NOT NULL  ,"+
					"Sic_Participant				CHAR(1)	 NOT NULL  ,"+
					"Sec_Participant				CHAR(1)	 NOT NULL  		)"));
							
							
			statement.executeUpdate("create  index LEGAL_ENTITY_SX5 on LEGAL_ENTITY (POSTAL_CD_SRCH_KEY)");
			statement.executeUpdate("create  index LEGAL_ENTITY_SX1 on LEGAL_ENTITY (CIF_NUMBER)");
			statement.executeUpdate("create  index LEGAL_ENTITY_SX2 on LEGAL_ENTITY (HANDLE)");
			statement.executeUpdate("create  index LEGAL_ENTITY_SX3 on LEGAL_ENTITY (NAME_SRCH_KEY)");
			statement.executeUpdate("create  index LEGAL_ENTITY_SX4 on LEGAL_ENTITY (CITY_SRCH_KEY)");
					
			System.out.println("Create Table successful");
			
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public void dropTables() {
		try {
			statement.executeUpdate("DROP INDEX LEGAL_ENTITY_SX1");
			statement.executeUpdate("DROP INDEX LEGAL_ENTITY_SX2");
			statement.executeUpdate("DROP INDEX LEGAL_ENTITY_SX3");
			statement.executeUpdate("DROP INDEX LEGAL_ENTITY_SX4");
			statement.executeUpdate("DROP INDEX LEGAL_ENTITY_SX5");

			statement.executeUpdate("DROP TABLE LEGAL_ENTITY");
			
			
			System.out.println("Drop Table successful");
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	
		
}