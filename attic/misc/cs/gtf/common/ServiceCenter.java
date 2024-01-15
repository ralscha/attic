package gtf.common;

import common.util.*;
import java.sql.*;

public class ServiceCenter implements gtf.common.Constants {

	private String shortName;
	private String name;
	private String dbURL;
	private String dbUser;
	private String dbPassword;
	private Connection conn = null;
	
	static {
		try {
			Class.forName(AppProperties.getStringProperty(P_DB_DRIVER));
			if (DEBUG)
				java.sql.DriverManager.setLogStream(System.out);
		} catch (Exception e) {
			System.err.println(e);
		} 
	}
	
	public ServiceCenter(String shortName) throws ServiceCenterNotFoundException {
		this.shortName = shortName;
		name = AppProperties.getStringProperty(shortName+P_SC_NAME);
		dbURL = AppProperties.getStringProperty(shortName+P_DB_URL);
		dbUser = AppProperties.getStringProperty(shortName+P_DB_USER);
		dbPassword = AppProperties.getStringProperty(shortName+P_DB_PASSWORD);
		if ((name == null) || (dbURL == null) || (dbUser == null) || (dbPassword == null)) {
      		throw new ServiceCenterNotFoundException(shortName);
      }
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

	public String getName() {
		return name;
	}
	
	public String getShortName() {
		return shortName;
	}

	public String toString() {
		return shortName;
	}
}