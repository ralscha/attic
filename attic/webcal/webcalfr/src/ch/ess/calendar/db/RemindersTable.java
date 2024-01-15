package ch.ess.calendar.db;

import java.sql.*;
import java.util.*;


public class RemindersTable {

	private final static String deleteSQL = "DELETE FROM Reminders";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT reminderid,appointmentid,minutesbefore,email FROM Reminders";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO Reminders(appointmentid,minutesbefore,email) VALUES(?,?,?)";

	private final static String updateSQL = 
		"UPDATE Reminders SET appointmentid=?, minutesbefore=?, email=? WHERE reminderid=?";

	public int delete() throws SQLException {
		return (delete((String)null));
	}

	public int delete(Reminders r) throws SQLException {
		return delete("reminderid = " + r.getReminderid());
	}

	public int delete(String whereClause) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {      
      return delete(whereClause, conn);
    } finally {
      conn.close();
    }    
  }

	public int delete(String whereClause, Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
    //int count;
		if (whereClause == null)
			return stmt.executeUpdate(deleteSQL);
		else
			return stmt.executeUpdate(deleteSQL+whereSQL+whereClause);
	}

	public Iterator select() throws SQLException {
		return select(null, null);
	}

	public Iterator select(String whereClause) throws SQLException {
		return select(whereClause, null);
	}

  public Iterator select(String whereClause, String orderClause) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {      
      return select(whereClause, orderClause, conn);
    } finally {
      conn.close();
    }    
  }

	public Iterator select(String whereClause, String orderClause, Connection conn) throws SQLException {

		StringBuffer sb = new StringBuffer(selectSQL);

		if (whereClause != null)
			sb.append(whereSQL).append(whereClause);
		if (orderClause != null)
			sb.append(orderSQL).append(orderClause);

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sb.toString());

		List tmpList = new ArrayList();
		while(rs.next()) {
		  tmpList.add(makeObject(rs));
		}
		return tmpList.iterator();

	}

	public int insert(Reminders reminders) throws SQLException {
	  Connection conn = DriverManager.getConnection("jdbc:poolman"); 
	  try {
      return insert(reminders, conn);
	  } finally {
	    conn.close();
	  }
	}


	public int insert(Reminders reminders, Connection conn) throws SQLException {
		PreparedStatement insertPS = conn.prepareStatement(insertSQL);
		prepareInsertStatement(insertPS, reminders);
		return insertPS.executeUpdate();
	}

	public int update(Reminders reminders) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman");  
    try {
			PreparedStatement updatePS = conn.prepareStatement(updateSQL);
		  prepareUpdateStatement(updatePS, reminders);
		  int rowCount = updatePS.executeUpdate();
		  if (rowCount <= 0) {
			  return insert(reminders);
		  } else {
			  return rowCount;
		  }
		} finally {
      conn.close();
		}
	}

	public static Reminders makeObject(ResultSet rs) throws SQLException {
		return new Reminders(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4));
	}

	private void prepareInsertStatement(PreparedStatement ps, Reminders reminders) throws SQLException {
		ps.setInt(1, reminders.getAppointmentid());
		ps.setInt(2, reminders.getMinutesbefore());
		ps.setString(3, reminders.getEmail());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Reminders reminders) throws SQLException {
		ps.setInt(1, reminders.getAppointmentid());
		ps.setInt(2, reminders.getMinutesbefore());
		ps.setString(3, reminders.getEmail());
		ps.setInt(4, reminders.getReminderid());
	}
}
