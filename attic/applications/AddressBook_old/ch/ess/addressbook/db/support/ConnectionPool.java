
package ch.ess.addressbook.db.support;

import com.javaexchange.dbConnectionBroker.DbConnectionBroker;
import common.util.AppProperties;
import java.sql.Connection;
import java.io.IOException;

public class ConnectionPool {

	private static DbConnectionBroker instance;
	
	public static Connection getConnection() {	
		return getInstance().getConnection();
	}
	
	public static void freeConnection(Connection connection) {
		getInstance().freeConnection(connection);
	}

	public static DbConnectionBroker getInstance() {
		if (instance == null) {
			synchronized (ConnectionPool.class) {
				if (instance == null) {
					createInstance();
				}
			}
		}
		return instance;
	}

	private static void createInstance() {
		try {	
			String dbDriver = AppProperties.getStringProperty("db.driver");
			String dbURL = AppProperties.getStringProperty("db.url");
			String dbUser = AppProperties.getStringProperty("db.user");
			String dbPassword = AppProperties.getStringProperty("db.password");

			int minConns = AppProperties.getIntegerProperty("db.min.connections", 1);
			int maxConns = AppProperties.getIntegerProperty("db.max.connections", 2);

			String logFileString = AppProperties.getStringProperty("db.log.file");
			double maxConnTime = AppProperties.getDoubleProperty("db.max.connection.time", 0.5);
							
			instance = new DbConnectionBroker(dbDriver, dbURL, dbUser, dbPassword, minConns,
		                               			maxConns, logFileString, maxConnTime);
									
		} catch (IOException ioe) {
			System.err.println(ioe);
			instance = null;
		}
	}
}