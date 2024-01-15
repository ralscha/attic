package gtf.util;

/**
 * This type was created in VisualAge.
 */

import java.sql.*;
import java.io.*;
import java.util.*;
import common.util.*;
 
public class InitSWIFTDb {
	private Connection connection;
	private Statement statement;

	private final static String USAGE_STR = "Usage: java InitSWIFTDb <DROP | CREATE | DELETE | READ <swiftAdrFile> <stateDescrFile> >";

	public static void main(String[] args) {
		if (args.length >= 1) {
			InitSWIFTDb isd = new InitSWIFTDb();
			isd.initDbConnection();
			if ("DROP".equalsIgnoreCase(args[0]))
				isd.dropTables();
			else if ("CREATE".equalsIgnoreCase(args[0]))
				isd.createTables();
			else if ("DELETE".equalsIgnoreCase(args[0]))
				isd.deleteTables();
			else if ("READ".equalsIgnoreCase(args[0]))
				if (args.length == 3)
					isd.readFiles(args[1], args[2]);
				else
					System.out.println(USAGE_STR);
			else 
				System.out.println(USAGE_STR);
			isd.shutDown();
		} else {
			System.out.println(USAGE_STR);  
		}			
	}

	void shutDown() {
		try {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
	}
	
	public void initDbConnection() {
		connection = null;
		statement = null;
		try {
			Class.forName(AppProperties.getStringProperty("swift.db.driver"));
			connection = DriverManager.getConnection(AppProperties.getStringProperty("swift.db.url"),
															AppProperties.getStringProperty("swift.db.user"),
															AppProperties.getStringProperty("swift.db.password"));
			statement = connection.createStatement();
			System.out.println("Db Connection OK");
		} catch (Exception e) {
			System.err.println(e);
		}
	}


	private void createTables() {
		try {
			statement.executeUpdate("CREATE TABLE SWIFTHeader("+
							  "TOSN  CHAR(6)  NOT NULL PRIMARY KEY,"+
//							  "SendDate  TIMESTAMP  NOT NULL,"+
							  "SendDate  DATE  NOT NULL,"+
							  "SenderAddress  CHAR(12)  NOT NULL,"+
							  "SessionNumber  CHAR(4)  NOT NULL,"+
							  "SequenceNumber  CHAR(6)  NOT NULL,"+
							  "ReceiverAddress  CHAR(11)  NOT NULL,"+
							  "ProcCenter  CHAR(4)  NOT NULL,"+
							  "MessageType  CHAR(3)  NOT NULL,"+
							  "Duplicate  CHAR(1)  NOT NULL,"+
							  "Priority  CHAR(1)  NOT NULL,"+
//							  "ReceiveTS  TIMESTAMP  NOT NULL , " +
							  "ReceiveTS  DATE  NOT NULL)");
					
			statement.executeUpdate("CREATE INDEX SWIFTHeaderIx2 ON SWIFTHeader(ReceiveTS)");
			statement.executeUpdate("CREATE INDEX SWIFTHeaderIx3 ON SWIFTHeader(ProcCenter)");
			
			statement.executeUpdate("CREATE TABLE MessageLine("+
							  "TOSN  CHAR(6)  NOT NULL PRIMARY KEY,"+
							  "Lineno  SMALLINT  NOT NULL PRIMARY KEY,"+
							  "FieldTag  CHAR(3),"+
							  "MsgLine  CHAR(69))");
	
			
			statement.executeUpdate("CREATE TABLE SWIFTAddress("+
									"Swift CHAR(11) NOT NULL PRIMARY KEY,"+
									"Address CHAR(70) NOT NULL)");
	
			statement.executeUpdate("CREATE TABLE StateDescription("+
									"Code CHAR(2) NOT NULL,"+
									"Colour SMALLINT NOT NULL,"+
									"Description CHAR(120))");
	
			statement.executeUpdate("CREATE TABLE State("+
									"ProcCenter CHAR(4) NOT NULL,"+
									"OrderNo CHAR(35) NOT NULL PRIMARY KEY," +
									"GtfNo INTEGER NOT NULL,"+
									"StateNum CHAR(2) NOT NULL,"+
									"StateTran CHAR(3) NOT NULL,"+
									"MessageType CHAR(1) NOT NULL,"+
//									"StateTS TIMESTAMP NOT NULL, " +
									"StateTS DATE NOT NULL)");		
			
			System.out.println("Create Table successful");
			
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public void deleteTables() {
		try {		
					
			statement.executeUpdate("DELETE FROM SWIFTHeader");
			connection.commit();
			
			statement.executeUpdate("DELETE FROM MessageLine");
			connection.commit();
			
			statement.executeUpdate("DELETE FROM SWIFTAddress");
			connection.commit();
			
			statement.executeUpdate("DELETE FROM State");
			connection.commit();
	
			statement.executeUpdate("DELETE FROM StateDescription");
			connection.commit();
					
			System.out.println("Delete Table successful");
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public void dropTables() {
		try {
			statement.executeUpdate("DROP TABLE SWIFTHeader");
			statement.executeUpdate("DROP TABLE MessageLine");
			statement.executeUpdate("DROP TABLE SWIFTAddress");
			statement.executeUpdate("DROP TABLE State");
			statement.executeUpdate("DROP TABLE StateDescription");
			
			System.out.println("Drop Table successful");
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	public void readFiles(String swiftAdrFile, String stateDescrFile) {
		try {
			readStateDescription(stateDescrFile);
			System.out.println("readStateDescription successful");
	
			readSWIFTAddress(swiftAdrFile);
			System.out.println("readSWIFTAddress File successful");
				
		} catch (Exception e) {
			System.err.println(e);
		}
			
	}
	
	public void readStateDescription(String fileName) throws SQLException, IOException {
	
		String insertSDSQL = "INSERT INTO StateDescription(Code, Colour, Description) VALUES(?,?,?)";
		
		BufferedReader dis;
		String line;
	
		PreparedStatement ps = connection.prepareStatement(insertSDSQL);
		dis = new BufferedReader(new FileReader(fileName));     
		while ((line = dis.readLine()) != null) {
					
			ps.setString(1,line.substring(0,2));
			ps.setShort(2, Short.parseShort(line.substring(2,3)));
			ps.setString(3,line.substring(3).trim());
		
			ps.executeUpdate();
				
		}
		ps.close();
		dis.close();
			
	}
	
	public void readSWIFTAddress(String fileName) throws SQLException, IOException {
		String insertSQL = "INSERT INTO SWIFTAddress(Swift, Address) VALUES(?,?)";
		BufferedReader dis = null;
		String line = null;
		int loop2commit = 500;
		int l = 0;
		
		PreparedStatement ps = connection.prepareStatement(insertSQL);		
	
		connection.setAutoCommit(false);
		
		StringBuffer sb = new StringBuffer();
		try {
	
		dis = new BufferedReader(new FileReader(fileName));
		while ((line = dis.readLine()) != null) {
			if (line.length() > 48) {
	
				ps.setString(1, line.substring(0, 11));
	
				sb.setLength(0);
				sb.append(line.substring(12, 48).trim()).append(", ");
				sb.append(line.substring(48).trim());
	
				ps.setString(2, sb.toString());
				ps.executeUpdate();
	
				if (++l > loop2commit) {
					connection.commit();
					l = 0;
				} 
			}
		}
		
		connection.commit();
		connection.setAutoCommit(true);
		
		} catch (SQLException sqle) {
			System.err.println(sqle);
			System.err.println(line);
		}
		ps.close();
		dis.close();
	}
		
}