import common.util.*;

import java.sql.*;
import java.util.*;
import com.javaexchange.dbConnectionBroker.*;


public class Scrap {
	
	public static void main(String[] args) {
		
			
		try {
			String dbDriver = AppProperties.getStringProperty("db.driver");
			String dbURL = AppProperties.getStringProperty("db.url");
			String dbUser = AppProperties.getStringProperty("db.user");
			String dbPassword = AppProperties.getStringProperty("db.password");
			
			int minConns = AppProperties.getIntegerProperty("min.connections", 1);
			int maxConns = AppProperties.getIntegerProperty("max.connections", 2);
			
			String logFileString = AppProperties.getStringProperty("log.file.string");
			double maxConnTime = AppProperties.getDoubleProperty("max.connection.time", 0.5);
			
			DbConnectionBroker myBroker = new DbConnectionBroker(dbDriver, dbURL, dbUser, dbPassword, minConns,
			                         			maxConns, logFileString, maxConnTime);
	
			Connection conn = myBroker.getConnection();
			
			DossierSearch ds = new DossierSearch(conn);
			List result = ds.searchWithRef("1234");
			Iterator it = result.iterator();
			while(it.hasNext()) {
				System.out.println((SS_BANK_REF)it.next());
			}
			
			myBroker.freeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
				
	}
}