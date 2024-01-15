package ch.ess.calendar.db;

import java.sql.*;
import java.util.*;


public class RepeatersTable {

	private final static String deleteSQL = "DELETE FROM Repeaters";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT repeaterid,appointmentid,until,every,everyperiod,repeaton,repeatonweekday,repeatonperiod FROM Repeaters";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO Repeaters(appointmentid,until,every,everyperiod,repeaton,repeatonweekday,repeatonperiod) VALUES(?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE Repeaters SET appointmentid=?, until=?, every=?, everyperiod=?, repeaton=?, repeatonweekday=?, repeatonperiod=? WHERE repeaterid=?";

	public int delete() throws SQLException {
		return (delete(null));
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
		if (whereClause == null) {
			return stmt.executeUpdate(deleteSQL);
    }

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



	public int insert(Repeaters repeaters) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {
      return insert(repeaters, conn);
    } finally {
      conn.close();
    }
	}

	public int insert(Repeaters repeaters, Connection conn) throws SQLException {
		PreparedStatement insertPS = conn.prepareStatement(insertSQL);
		prepareInsertStatement(insertPS, repeaters);
		return insertPS.executeUpdate();
	}

	public int update(Repeaters repeaters) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {      
    	PreparedStatement updatePS = conn.prepareStatement(updateSQL);
		  prepareUpdateStatement(updatePS, repeaters);
		  int rowCount = updatePS.executeUpdate();

		  if (rowCount <= 0) {
			  return insert(repeaters);
		  } 
			return rowCount;		  

		} finally {
      conn.close();
 		}
	}

	public static Repeaters makeObject(ResultSet rs) throws SQLException {
		return new Repeaters(rs.getInt(1), rs.getInt(2), rs.getTimestamp(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
	}

	private void prepareInsertStatement(PreparedStatement ps, Repeaters repeaters) throws SQLException {
		ps.setInt(1, repeaters.getAppointmentid());
		
		if (repeaters.getUntil() == null) 
			ps.setNull(2, java.sql.Types.TIMESTAMP);
		else 	
			ps.setTimestamp(2, repeaters.getUntil());
		
		ps.setInt(3, repeaters.getEvery());
		ps.setInt(4, repeaters.getEveryperiod());
		ps.setInt(5, repeaters.getRepeaton());
		ps.setInt(6, repeaters.getRepeatonweekday());
		ps.setInt(7, repeaters.getRepeatonperiod());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Repeaters repeaters) throws SQLException {
		ps.setInt(1, repeaters.getAppointmentid());
		
		if (repeaters.getUntil() == null) {
			ps.setNull(2, java.sql.Types.TIMESTAMP);
		}
		else 	
			ps.setTimestamp(2, repeaters.getUntil());		
		
		ps.setInt(3, repeaters.getEvery());
		ps.setInt(4, repeaters.getEveryperiod());
		ps.setInt(5, repeaters.getRepeaton());
		ps.setInt(6, repeaters.getRepeatonweekday());
		ps.setInt(7, repeaters.getRepeatonperiod());
		ps.setInt(8, repeaters.getRepeaterid());
	}
}
