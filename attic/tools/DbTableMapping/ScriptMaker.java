
import java.sql.*;
import java.util.*;
import java.io.*;

public class ScriptMaker {

	private Connection conn;
	Set indexSet;
	
	public static void main(String args[]) {
		if (args.length == 5) {
			if (args[4].equalsIgnoreCase("RUNSTATS"))
				new ScriptMaker(args[0], args[1], args[2], args[3]).createRunstatsScript();
			else 
				new ScriptMaker(args[0], args[1], args[2], args[3]).createReorgScript();
		} else {
			System.out.println("java RunstatsScript <dbDriver> <dbURL> <user> <password> [REORG|RUNSTATS]");
		}
	}

	private ScriptMaker(String dbDriver, String dbURL, String user, String password) {
	
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbURL, user, password);
						
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	


	private void createReorgScript() {
		try {
			DatabaseMetaData dmb = conn.getMetaData();
	
			PrintWriter out = new PrintWriter(new FileWriter("reorg.bat"));
			out.println("DB2 CONNECT TO %1");
			
			indexSet = new HashSet();
	
			String[] type = new String[1];
			type[0] = "TABLE";
			
			ResultSet rs = dmb.getTables(null, "GTFLC", "%", type);
			while(rs.next()) {
				String tschema = rs.getString(2);
				String ttable = rs.getString(3);
				
				ResultSet indexRS = dmb.getIndexInfo(null, tschema, ttable, false, false);
				
				indexSet.clear();
				while(indexRS.next()) {
					procIndex(indexRS);
				}
				
				if (indexSet.isEmpty()) {
					out.println("@echo "+ttable);
					out.println("DB2 REORG TABLE "+tschema+"."+ttable);
				} else {
					if (indexSet.size() > 1) {
						System.out.println("Table "+tschema+"."+ttable+" more than one index");
					}
					
					Iterator it = indexSet.iterator();
					StringBuffer sb = new StringBuffer();
					while(it.hasNext()) {
						sb.append(it.next());						
						if (it.hasNext())
							sb.append(",");
					}
					
					out.println("@echo "+ttable);
					out.println("DB2 REORG TABLE "+tschema+"."+ttable+" INDEX "+sb.toString());
					
				}
				
			}
			
			out.println("DB2 COMMIT");
			out.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.err.println(sqle);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally { 			
			try {
				conn.close();
      		} catch (SQLException sqle) {
      			System.err.println(sqle);
      		}
		} 
	}
	
	private void procIndex(ResultSet rs) throws SQLException {
			
		String ixSchema = rs.getString(2);
		String ixTable  = rs.getString(3);
		boolean nonUnique = rs.getBoolean(4);
		String indexQualifier = rs.getString(5);
		String indexName = rs.getString(6);
		short ixType = rs.getShort(7);
		
		if (ixSchema.equals(indexQualifier)) {		
			String upperIndexName = indexName.toUpperCase();
			if (upperIndexName.endsWith("_IDX") ||
			    upperIndexName.endsWith("_SX") ||
			    upperIndexName.endsWith("_SX1")) {
			
			    indexSet.add(indexQualifier+"."+indexName);
			    
			}
		}
	}
			
	
	private void createRunstatsScript() {
		try {
			DatabaseMetaData dmb = conn.getMetaData();
	
			PrintWriter out = new PrintWriter(new FileWriter("runstats.bat"));
			out.println("DB2 CONNECT TO %1");

			String[] type = new String[1];
			type[0] = "TABLE";
			
			ResultSet rs = dmb.getTables(null, "GTFLC", "%", type);
			while(rs.next()) {
				String table = rs.getString(2) + "." + rs.getString(3);
				out.println("DB2 RUNSTATS ON TABLE "+table+ " AND INDEXES ALL");
			}
			
			out.println("DB2 COMMIT");
			out.close();
			
		} catch (SQLException sqle) {
			System.err.println(sqle);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally { 			
			try {
				conn.close();
      		} catch (SQLException sqle) {
      			System.err.println(sqle);
      		}
		} 

		
	}

}