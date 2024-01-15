package gtf.mbooker;

import java.sql.*;
import java.io.*;
import java.util.*;
import common.util.*;
import gtf.crapa.*;
 
public class InitDb {
	private Statement statement;

	public static void main(String[] args) {
		if (args.length == 1) {
			InitDb isd = new InitDb();
			isd.initDbConnection();

			if ("DROP".equalsIgnoreCase(args[0]))
				isd.dropTables();
			else if ("CREATE".equalsIgnoreCase(args[0]))
				isd.createTables();

			isd.shutDown();
		}			
	}

	void shutDown() {
		try {
			if (statement != null) {
				statement.close();
			}
			CrapaDbManager.closeDb();
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
	}
	
	public void initDbConnection() {
		try {
			CrapaDbManager.openDb();
			statement = CrapaDbManager.getConnection().createStatement();

		} catch (Exception e) {
			System.err.println(e);
		}
	}


	private void createTables() {
		try {
			statement.executeUpdate("CREATE TABLE GTFLC.M_ContLiab("+
							"sequencenumber integer not null," +
							"gtfnumber char(7) not null," +
							"type char(1) not null," +
							"accountnumber char(16) not null," +
							"expirydate char(8) not null," +
							"currency char(3) not null," +
							"amount decimal(15,3) not null," +
							"exchangerate decimal(15,8) not null," +
							"gtfproccenter char(4) not null," +
							"customerref char(35) not null," +
							"gtftype char(20) not null," +
							"dossieritem char(20) not null," +
							"bucode char(4) not null," +
							"creator char(8) not null," +
							"ts timestamp not null)");
							
			statement.executeUpdate("CREATE TABLE GTFLC.M_FirmLiab("+
							"sequencenumber integer not null," +
							"gtfnumber char(7) not null," +
							"debacct_number char(16) not null," +
							"creacct_number char(16) not null," +
							"valuedate char(8) not null," + 
							"currency char(3) not null," +
							"amount decimal(15,3) not null," +
							"exchangerate decimal(15,8) not null," +
							"gtfproccenter char(4) not null," +
							"customerref char(35) not null," +
							"gtftype char(20) not null," +
							"dossieritem char(20) not null," +
							"bucode char(4) not null," +
							"creator char(8) not null," +
							"ts timestamp not null)");
			                
					
			/*						
			statement.executeUpdate("CREATE INDEX SWIFTHeaderIx2 ON SWIFTHeader(ReceiveTS)");
			statement.executeUpdate("CREATE INDEX SWIFTHeaderIx3 ON SWIFTHeader(ProcCenter)");
			*/
			System.out.println("Create Table successful");
			
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public void dropTables() {
		try {
			statement.executeUpdate("DROP GTFLC.TABLE M_ContLiab");
			statement.executeUpdate("DROP GTFLC.TABLE M_FirmLiab");
			
			System.out.println("Drop Table successful");
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
		
}