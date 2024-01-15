package gtf.ss.search;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

import common.util.*;

import com.javaexchange.dbConnectionBroker.*;


public class HttpServletJXGB extends HttpServlet {

	protected static DbConnectionBroker myBroker = null;
	protected String propsFile;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);		
		if (myBroker == null) { // Only created by first servlet to call
			
			try {	
				propsFile = getInitParameter("props.file");
				AppProperties.setFileName(propsFile);
	
	
				System.out.println(propsFile);
	
				String dbDriver = AppProperties.getStringProperty("SS.db.driver");
				String dbURL = AppProperties.getStringProperty("SS.db.url");
				String dbUser = AppProperties.getStringProperty("SS.db.user");
				String dbPassword = AppProperties.getStringProperty("SS.db.password");
	
				int minConns = AppProperties.getIntegerProperty("min.connections", 1);
				int maxConns = AppProperties.getIntegerProperty("max.connections", 2);
	
				String logFileString = AppProperties.getStringProperty("log.file.string");
				double maxConnTime = AppProperties.getDoubleProperty("max.connection.time", 0.5);
								
				myBroker = new DbConnectionBroker(dbDriver, dbURL, dbUser, dbPassword, minConns,
		                                  			maxConns, logFileString, maxConnTime);
										
													
			} catch (IOException ioe) {
				System.out.println(ioe);
				log("init HttpServletJXGB");
				log(ioe.toString());
				myBroker = null;
			}

		}
	}
}