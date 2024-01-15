package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _AnfragelieferantenTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.AnfrageLieferanten";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT LieferantId,AnfrageId,allgemein FROM dbo.AnfrageLieferanten";
	private final static String countSQL = "SELECT count(*) FROM dbo.AnfrageLieferanten";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.AnfrageLieferanten(LieferantId,AnfrageId,allgemein) VALUES(?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.AnfrageLieferanten SET allgemein=? WHERE LieferantId=? AND AnfrageId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE LieferantId=? AND AnfrageId=?";

	public _AnfragelieferantenTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Anfragelieferanten anfragelieferanten) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, anfragelieferanten);
				return deletePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int delete() throws SQLException, PoolPropsException {
		return (delete((String)null));
	}

	public int delete(String whereClause) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			conn = SQLManager.getInstance().requestConnection();
			Statement stmt = conn.createStatement();

			if (whereClause == null)
				return (stmt.executeUpdate(deleteSQL));
			else
				return (stmt.executeUpdate(deleteSQL+whereSQL+whereClause));
		} finally {
			SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int count() throws SQLException, PoolPropsException {
		return count(null);
	}

	public int count(String whereClause) throws SQLException, PoolPropsException {
		StringBuffer sb = new StringBuffer(countSQL);

		if (whereClause != null)
			sb.append(whereSQL).append(whereClause);

		Connection conn = SQLManager.getInstance().requestConnection();
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
			SQLManager.getInstance().returnConnection(conn);
		}
	}

	public Anfragelieferanten selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Anfragelieferanten)resultList.get(0);
		} else {
			return null;
		}
	}

	public List select() throws SQLException, PoolPropsException {
		return select(null, null);
	}

	public List select(String whereClause) throws SQLException, PoolPropsException{
		return select(whereClause, null);
	}

	public List select(String whereClause, String orderClause) throws SQLException, PoolPropsException {
		StringBuffer sb = new StringBuffer(selectSQL);

		if (whereClause != null)
			sb.append(whereSQL).append(whereClause);
		if (orderClause != null)
			sb.append(orderSQL).append(orderClause);

		Connection conn = SQLManager.getInstance().requestConnection();
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
			SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int insert(Anfragelieferanten anfragelieferanten) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, anfragelieferanten);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Anfragelieferanten anfragelieferanten) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, anfragelieferanten);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Anfragelieferanten makeObject(ResultSet rs) throws SQLException {
		return new Anfragelieferanten(rs.getInt("lieferantid"), rs.getInt("anfrageid"), rs.getBoolean("allgemein"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Anfragelieferanten anfragelieferanten) throws SQLException {
		ps.setInt(1, anfragelieferanten.getLieferantid());
		ps.setInt(2, anfragelieferanten.getAnfrageid());
		ps.setBoolean(3, anfragelieferanten.getAllgemein());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Anfragelieferanten anfragelieferanten) throws SQLException {
		ps.setBoolean(1, anfragelieferanten.getAllgemein());
		ps.setInt(2, anfragelieferanten.getLieferantid());
		ps.setInt(3, anfragelieferanten.getAnfrageid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Anfragelieferanten anfragelieferanten) throws SQLException {
		ps.setInt(1, anfragelieferanten.getLieferantid());
		ps.setInt(2, anfragelieferanten.getAnfrageid());
	}

}
