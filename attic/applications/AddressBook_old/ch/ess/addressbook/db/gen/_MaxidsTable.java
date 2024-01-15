package ch.ess.addressbook.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import ch.ess.addressbook.db.support.*;
import ch.ess.addressbook.db.*;

public class _MaxidsTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM maxids";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT tablename,maxid FROM maxids";
	private final static String countSQL = "SELECT count(*) FROM maxids";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO maxids(tablename,maxid) VALUES(?,?)";

	private final static String updateSQL = 
		"UPDATE maxids SET maxid=? WHERE tablename=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE tablename=?";

	public _MaxidsTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Maxids maxids) throws SQLException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = ConnectionPool.getConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, maxids);
				return deletePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				ConnectionPool.freeConnection(conn);
		}
	}

	public int delete() throws SQLException {
		return (delete((String)null));
	}

	public int delete(String whereClause) throws SQLException {
		Connection conn = null;
		try {
			conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();

			if (whereClause == null)
				return (stmt.executeUpdate(deleteSQL));
			else
				return (stmt.executeUpdate(deleteSQL+whereSQL+whereClause));
		} finally {
			ConnectionPool.freeConnection(conn);
		}
	}

	public int count() throws SQLException {
		return count(null);
	}

	public int count(String whereClause) throws SQLException {
		StringBuffer sb = new StringBuffer(countSQL);

		if (whereClause != null)
			sb.append(whereSQL).append(whereClause);

		Connection conn = ConnectionPool.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			int count = -1;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();

			return count;

		} finally {
			ConnectionPool.freeConnection(conn);
		}
	}

	public Maxids selectOne(String whereClause) throws SQLException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Maxids)resultList.get(0);
		} else {
			return null;
		}
	}

	public List select() throws SQLException {
		return select(null, null);
	}

	public List select(String whereClause) throws SQLException {
		return select(whereClause, null);
	}

	public List select(String whereClause, String orderClause) throws SQLException {
		StringBuffer sb = new StringBuffer(selectSQL);

		if (whereClause != null)
			sb.append(whereSQL).append(whereClause);
		if (orderClause != null)
			sb.append(orderSQL).append(orderClause);

		Connection conn = ConnectionPool.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			List resultList = new ArrayList();

			while(rs.next()) {
				resultList.add(makeObject(rs));
			}
			rs.close();
			stmt.close();

			return resultList;

		} finally {
			ConnectionPool.freeConnection(conn);
		}
	}

	public int insert(Maxids maxids) throws SQLException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = ConnectionPool.getConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, maxids);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				ConnectionPool.freeConnection(conn);
		}
	}

	public int update(Maxids maxids) throws SQLException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = ConnectionPool.getConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, maxids);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				ConnectionPool.freeConnection(conn);
		}
	}

	public static Maxids makeObject(ResultSet rs) throws SQLException {
		return new Maxids(rs.getString("tablename"), rs.getInt("maxid"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Maxids maxids) throws SQLException {
		ps.setString(1, maxids.getTablename());
		ps.setInt(2, maxids.getMaxid());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Maxids maxids) throws SQLException {
		ps.setInt(1, maxids.getMaxid());
		ps.setString(2, maxids.getTablename());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Maxids maxids) throws SQLException {
		ps.setString(1, maxids.getTablename());
	}

}
