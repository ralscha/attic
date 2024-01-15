package gtf.ss.update;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.math.*;
import gtf.ss.common.*;

import common.util.AppProperties;

public class InitSwissSearchDb {
	private Statement statement;
	private SwissSearchDB ssDb;
	
	public static void main(String[] args) {
		try {
			if (args.length == 1) {
				InitSwissSearchDb isd = new InitSwissSearchDb();
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
			ssDb.closeConnection();
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
	}
	
	
	public void openDb() throws SQLException {		
		
		ssDb = new SwissSearchDB();
		Connection con = ssDb.getConnection();
		statement = con.createStatement();
	} 
	
	private void createTables() {
		try {
			System.out.println(statement.executeUpdate("CREATE TABLE SS_BANK_REF("+
							"SERVICECENTER  CHAR(4),"+
							"DOSSIER_NUMBER INTEGER,"+
							"BRANCH         CHAR(4),"+
							"BRANCH_GROUP   CHAR(4),"+
							"BANK_REF_NO    CHAR(30),"+
							"BANK_REF_NO_S  CHAR(30),"+
							"BANK_NAME      CHAR(70),"+
							"BANK_NAME_S    CHAR(70),"+
							"BANK_LOCATION  CHAR(35),"+
							"ISO_CODE       CHAR(3),"+
							"AMOUNT         DECIMAL(15,3))"));
			
			statement.executeUpdate("CREATE UNIQUE INDEX SS_UNIQUE_IX ON SS_BANK_REF(SERVICECENTER, DOSSIER_NUMBER, BRANCH, BRANCH_GROUP, BANK_REF_NO, BANK_NAME, AMOUNT)");							
			statement.executeUpdate("CREATE INDEX SS_IX_REF ON SS_BANK_REF(BANK_REF_NO_S)");
			statement.executeUpdate("CREATE INDEX SS_IX_NAME ON SS_BANK_REF(BANK_NAME_S)");
			statement.executeUpdate("CREATE INDEX SS_IX_AMOUNT ON SS_BANK_REF(AMOUNT)");
			
			System.out.println("Create Table successful");
			
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public void dropTables() {
		try {
			statement.executeUpdate("DROP INDEX SS_IX_REF");
		} catch (SQLException e) {
			System.err.println(e);
		}

		try {
			statement.executeUpdate("DROP INDEX SS_IX_NAME");
		} catch (SQLException e) {
			System.err.println(e);
		}

		try {
			statement.executeUpdate("DROP INDEX SS_IX_AMOUNT");
		} catch (SQLException e) {
			System.err.println(e);
		}

		try {
			statement.executeUpdate("DROP TABLE SS_BANK_REF");
		} catch (SQLException e) {
			System.err.println(e);
		}
									
		System.out.println("Drop Table END");
	}
	
	
		
}