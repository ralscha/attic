package gtf.swift;

import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import gtf.swift.util.*;
import gtf.swift.state.*;
import gtf.swift.input.*;
import com.javaexchange.dbConnectionBroker.*;
import ViolinStrings.*;
import common.util.*;

public class SWIFTInsertService {

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	private DbConnectionBroker myBroker;
	private static SWIFTInsertService instance = new SWIFTInsertService();

	private SWIFTInsertService() {

		try {
			myBroker = new DbConnectionBroker(AppProperties.getStringProperty("swift.db.driver"),
                                  			AppProperties.getStringProperty("swift.db.url"),
                                  			AppProperties.getStringProperty("swift.db.user"),
                                  			AppProperties.getStringProperty("swift.db.password"),
                                  			AppProperties.getIntProperty("swift.db.min.connections"),
                                  			AppProperties.getIntProperty("swift.db.max.connections"),
                                  			AppProperties.getStringProperty("swift.db.connectionbroker.logfile"),
                                  			AppProperties.getDoubleProperty("swift.db.max.connectiontime"));

		} catch (IOException e) {
			System.err.println(e);
		}
	}


	public static void destroy() {
		instance.myBroker.destroy();
	}


	public static void insertMessageLine(List ml) {
		Connection conn = instance.myBroker.getConnection();

		try {
			Iterator it = ml.iterator();
			while(it.hasNext()) {
				((MessageLine)it.next()).insert(conn);
			}		
		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			instance.myBroker.freeConnection(conn);
       }   
	}

	public static void insertMessageLine(MessageLine ml) {
		Connection conn = instance.myBroker.getConnection();
		try {
			ml.insert(conn);
		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			instance.myBroker.freeConnection(conn);
		}
	}

	public static void insertState(List states) {
		Connection conn = instance.myBroker.getConnection();
		try {
			Iterator it = states.iterator();
			while(it.hasNext()) {
				((State)it.next()).insert(conn);
			}			
		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			instance.myBroker.freeConnection(conn);
		}
	}
	
	public static void insertState(State state) {
		Connection conn = instance.myBroker.getConnection();
		try {
			state.insert(conn);
		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			instance.myBroker.freeConnection(conn);
		}
	}
	
	public static void insertSWIFTHeader(List sh) {
		Connection conn = instance.myBroker.getConnection();
		try {
			Iterator it = sh.iterator();
			while(it.hasNext()){
				((SWIFTHeader)it.next()).insert(conn);
			}
		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			instance.myBroker.freeConnection(conn);
       }
	}

	public static void insertSWIFTHeader(SWIFTHeader sh) {
		Connection conn = instance.myBroker.getConnection();
		try {
			sh.insert(conn);
		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			instance.myBroker.freeConnection(conn);
		}
	}

	
}