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


public class SWIFTService {

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	private DbConnectionBroker myBroker;
	private static SWIFTService instance = new SWIFTService();

	public final static int ASC  = 0;
	public final static int DESC = 1;

	private SWIFTService() {

		try {
			myBroker = new DbConnectionBroker(AppProperties.getStringProperty("swift.db.driver"),
                                  			AppProperties.getStringProperty("swift.db.url"),
                                  			AppProperties.getStringProperty("swift.db.user"),
                                  			AppProperties.getStringProperty("swift.db.password"),
                                  			AppProperties.getIntegerProperty("swift.db.min.connections", 1),
                                  			AppProperties.getIntegerProperty("swift.db.max.connections", 3),
                                  			AppProperties.getStringProperty("swift.db.connectionbroker.logfile"),
                                  			AppProperties.getDoubleProperty("swift.db.max.connectiontime", 0.1));

		} catch (IOException e) {
			System.err.println(e);
		}
	}


	public static void destroy() {
		instance.myBroker.destroy();
	}

	public static String getAddressFromSWIFT(String swift) {
		Connection conn = instance.myBroker.getConnection();
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rset =	stmt.executeQuery("SELECT Address FROM SWIFTAddress WHERE Swift = '" + swift + "'");

			String address = null;
			if (rset.next()) {
				address = rset.getString(1);
			}
			rset.close();
			return address;

		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			try {
      			if (stmt != null) {
          			stmt.close();
          		}
      		} catch (SQLException e) {
          		System.err.println(e);
          	}
          	instance.myBroker.freeConnection(conn);
       } 
		return null;
	}

	public static List getDates() {
		Connection conn = instance.myBroker.getConnection();
		Statement stmt = null;
		Set dates = new TreeSet();

		try {
			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("SELECT DISTINCT(ReceiveTS) from SWIFTHeader order by ReceiveTS");

			while (rset.next()) {
				java.util.Date date = new java.util.Date(rset.getTimestamp(1).getTime());
				dates.add(dateFormat.format(date));
			}
			rset.close();
			return new ArrayList(dates);

		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
       	try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				System.err.println(e);
			}
			instance.myBroker.freeConnection(conn);
		} 
		return null;
	}
	
	public static List getMissingSWIFT() {

		Connection conn = instance.myBroker.getConnection();
		Statement stmt = null;

		List v = new ArrayList();
		List result = new ArrayList();

		int counter = 0;
		int next;

		try {
			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("SELECT TOSN FROM SWIFTHeader ORDER BY TOSN ASC");

			while (rset.next()) {
				v.add(rset.getString(1));
			}
			rset.close();

			if (v.size() > 0) {
				counter = Integer.parseInt((String) v.get(0));
			}

			for (int i = 1; i < v.size(); i++) {
				next = Integer.parseInt((String) v.get(i));

				while (++counter < next) {
					result.add(Strings.rightJustify(String.valueOf(counter), 6, '0'));
				}
			}
			return result;

		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			try {
      			if (stmt != null) {
          			stmt.close();
          		}
      		} catch (SQLException e) {
          		System.err.println(e);
			}
			instance.myBroker.freeConnection(conn);
       }
		return null;
	}

	public static List getStateDescription() {

		Connection conn = instance.myBroker.getConnection();
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("SELECT * FROM StateDescription");
			
			List v = new ArrayList();
			while (rset.next()) {
				v.add(StateDescription.makeObject(rset));
			}
			rset.close();
			return v;
			
		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			try {
      			if (stmt != null) {
          			stmt.close();
          		}
      		} catch (SQLException e) {
          		System.err.println(e);
          	}
          	instance.myBroker.freeConnection(conn);
		}
		return null;
	}

	public static SWIFTHeader getSWIFTHeader(String tosn) {

		Connection conn = instance.myBroker.getConnection();
		Statement stmt = null;

		try {

			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("SELECT * FROM SWIFTHeader WHERE TOSN = '" + tosn + "'");

			SWIFTHeader sh = null;
			if (rset.next()) {
				sh = SWIFTHeader.makeObject(rset);
			}
			rset.close();
			return sh;

		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			try {
      			if (stmt != null) {
					stmt.close();
				}
      		} catch (SQLException e) {
				System.err.println(e);
			}
			instance.myBroker.freeConnection(conn);
		}
		return null;
	}
	
	public static List getSWIFTMessage(String tosn) {
		Connection conn = instance.myBroker.getConnection();
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rset =	stmt.executeQuery("SELECT * FROM MessageLine WHERE TOSN = '" + tosn + "' order by Lineno ASC");

			List v = new ArrayList();
			while (rset.next()) {
				v.add(MessageLine.makeObject(rset));
			}
			rset.close();
			return v;

		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			try {
      			if (stmt != null) {
					stmt.close();
				}
      		} catch (SQLException e) {
				System.err.println(e);
			}
			instance.myBroker.freeConnection(conn);
		}
		return null;
	}

	public static List searchStates(int gtfno, Branch branch, String orderBy, int direction) {
		Connection conn = instance.myBroker.getConnection();
		List in = null;
		PreparedStatement ps = null;

		boolean bbranch = false;
		boolean bgtfno = false;

		try {

			StringBuffer sqlString = new StringBuffer("SELECT * FROM State");

			if (gtfno > 0) {
				bgtfno = true;
				sqlString.append(" WHERE GtfNo = ? ");
			}

			if (branch != null) {

				in = branch.getIn();
				if (in != null) {
					bbranch = true;

					if (in.size() == 1) {
						if (bgtfno)
							sqlString.append(" AND ProcCenter = ?");
						else
							sqlString.append(" WHERE ProcCenter = ?");
					} else {
						if (bgtfno)
							sqlString.append(" AND ProcCenter IN (?");
						else
							sqlString.append(" WHERE ProcCenter IN (?");

						for (int i = 1; i < in.size(); i++)
							sqlString.append(",?");
						sqlString.append(")");
					}

				}
			}

			if ((orderBy != null) && (!orderBy.equals(""))) {
				if (direction == ASC)
					sqlString.append(" ORDER BY ").append(orderBy).append(" ASC");
				else if (direction == DESC)
					sqlString.append(" ORDER BY ").append(orderBy).append(" DESC");
				else
					sqlString.append(" ORDER BY ").append(orderBy);
			}

			ps = conn.prepareStatement(sqlString.toString());

			int index = 0;
			if (bgtfno) {
				ps.setInt(++index, gtfno);
			}
			if (bbranch) {
				for (int i = 0; i < in.size(); i++)
					ps.setString(++index, (String) in.get(i));
			}

			ResultSet rset = ps.executeQuery();
			List v = new ArrayList();

			while (rset.next()) {
				v.add(State.makeObject(rset));
			}
			rset.close();
			return v;

		} catch (SQLException sqle) {
			System.err.println(sqle);
		}
		finally { 
			try {
      			if (ps != null) {
          			ps.close();
          		}
      		} catch (SQLException e) {
          		System.err.println(e);
          	}   	
          	instance.myBroker.freeConnection(conn);
		}
		return null;
	}

	public static List searchSWIFTs(Calendar fromDate, Calendar toDate, Branch branch,
                         	String orderBy, int direction) {
		Connection conn = instance.myBroker.getConnection();
		PreparedStatement ps = null;
		boolean bdates = false;
		boolean bbranch = false;
		List in = null;

		try {

			StringBuffer sqlString = new StringBuffer("SELECT * FROM SWIFTHeader");
			java.sql.Timestamp fromTS = null;
			java.sql.Timestamp toTS = null;


			if ((fromDate != null) && (toDate != null) && (!fromDate.equals("")) &&
    				(!toDate.equals(""))) {

				bdates = true;

				fromTS = new java.sql.Timestamp(fromDate.getTime().getTime());
				toTS = new java.sql.Timestamp(toDate.getTime().getTime());

				sqlString.append(" WHERE ReceiveTS >= ? AND ReceiveTS <= ?");
			}

			if (branch != null) {

				in = branch.getIn();


				if (in != null) {
					bbranch = true;

					if (in.size() == 1) {
						if (bdates)
							sqlString.append(" AND ProcCenter = ?");
						else
							sqlString.append(" WHERE ProcCenter = ?");
					} else {
						/*
						if (bdates)
							sqlString.append(" AND ProcCenter IN (?");
						else
							sqlString.append(" WHERE ProcCenter IN (?");

						for (int i = 1; i < in.size(); i++)
							sqlString.append(",?");
						sqlString.append(")");
						*/
						if (bdates)
							sqlString.append(" AND (ProcCenter = ?");
						else
							sqlString.append(" WHERE (ProcCenter = ?");
						for (int i = 1; i < in.size(); i++)
							sqlString.append(" OR ProcCenter = ?");
						sqlString.append(")");
					}

				}
			}

			if ((orderBy != null) && (!orderBy.equals(""))) {
				if (direction == ASC)
					sqlString.append(" ORDER BY ").append(orderBy).append(" ASC");
				else if (direction == DESC)
					sqlString.append(" ORDER BY ").append(orderBy).append(" DESC");
				else
					sqlString.append(" ORDER BY ").append(orderBy);
			}

			ps = conn.prepareStatement(sqlString.toString());

			int index = 0;
			if (bdates) {
				ps.setTimestamp(++index, fromTS);
				ps.setTimestamp(++index, toTS);
			}
			if (bbranch) {
				for (int i = 0; i < in.size(); i++)
					ps.setString(++index, (String) in.get(i));
			}

			ResultSet rset = ps.executeQuery();
			List v = new ArrayList();

			while (rset.next()) {
				v.add(SWIFTHeader.makeObject(rset));
			}
			rset.close();

			return v;

		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally { 
			try {
      			if (ps != null) {
          			ps.close();
          		}
      		} catch (SQLException e) {
          		System.err.println(e);
          	}
			instance.myBroker.freeConnection(conn);
		}
		
		return null;
	}

	
}