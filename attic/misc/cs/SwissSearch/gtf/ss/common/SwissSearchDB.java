package gtf.ss.common;

import common.util.*;
import java.sql.*;

public class SwissSearchDB {

	private String dbURL;
	private String dbUser;
	private String dbPassword;
	private Connection conn = null;
	
	static {
		try {
			Class.forName(AppProperties.getStringProperty("SS.db.driver"));
		} catch (Exception e) {
			System.err.println(e);
		} 
	}
	
	public SwissSearchDB()  {
		dbURL = AppProperties.getStringProperty("SS.db.url");
		dbUser = AppProperties.getStringProperty("SS.db.user");
		dbPassword = AppProperties.getStringProperty("SS.db.password");
	}

	public Connection getConnection() throws SQLException {
		if ((conn == null) || (conn.isClosed()))
			conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
		return conn;
	}
	
	public void closeConnection() throws SQLException {
		if (conn != null) {
			conn.commit();
			conn.close();
			conn = null;
		}
	}

}