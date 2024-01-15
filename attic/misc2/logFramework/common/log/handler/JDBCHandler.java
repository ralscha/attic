package common.log.handler;


import java.sql.*;
import common.util.AppProperties;
import common.log.*;

public class JDBCHandler extends Handler {

	private Connection connection;
	private LogEventFormat format;
	private String table;
	private String columns;	
	
	public JDBCHandler(String prefix, String name) {
		String propertyPrefix = prefix + name;
		try {
			connection = DriverManager.getConnection(
               			AppProperties.getStringProperty(propertyPrefix + ".url"),
               			AppProperties.getStringProperty(propertyPrefix + ".username", ""),
               			AppProperties.getStringProperty(propertyPrefix + ".password", ""));
			//DriverManager.setLogStream(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		table = AppProperties.getStringProperty(propertyPrefix + ".table", "Log");
		columns = AppProperties.getStringProperty(propertyPrefix + ".columns", "");
		if (!columns.equals("") && !columns.startsWith("("))
			columns = '(' + columns + ')';
		format = new LogEventFormat(AppProperties.getStringProperty(propertyPrefix + ".format", "'%t', %n, '%e', '%h', '%c', '%f', '%l', '%m', '%(%j)o'"));
	}

	public void handle(LogEvent event) {
		try {
			Statement statement = connection.createStatement();
			int numRows = statement.executeUpdate("insert into " + table + " values (" +
                                      			format.format(event) + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}