package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _BenutzerrechteTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.BenutzerRechte";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT BenutzerId,RechtId,Lesen,Schreiben FROM dbo.BenutzerRechte";
	private final static String countSQL = "SELECT count(*) FROM dbo.BenutzerRechte";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.BenutzerRechte(BenutzerId,RechtId,Lesen,Schreiben) VALUES(?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.BenutzerRechte SET Lesen=?, Schreiben=? WHERE BenutzerId=? AND RechtId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE BenutzerId=? AND RechtId=?";

	public _BenutzerrechteTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Benutzerrechte benutzerrechte) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, benutzerrechte);
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

	public Benutzerrechte selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Benutzerrechte)resultList.get(0);
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

	public int insert(Benutzerrechte benutzerrechte) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, benutzerrechte);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Benutzerrechte benutzerrechte) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, benutzerrechte);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Benutzerrechte makeObject(ResultSet rs) throws SQLException {
		return new Benutzerrechte(rs.getInt("benutzerid"), rs.getInt("rechtid"), rs.getBoolean("lesen"), rs.getBoolean("schreiben"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Benutzerrechte benutzerrechte) throws SQLException {
		ps.setInt(1, benutzerrechte.getBenutzerid());
		ps.setInt(2, benutzerrechte.getRechtid());
		ps.setBoolean(3, benutzerrechte.getLesen());
		ps.setBoolean(4, benutzerrechte.getSchreiben());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Benutzerrechte benutzerrechte) throws SQLException {
		ps.setBoolean(1, benutzerrechte.getLesen());
		ps.setBoolean(2, benutzerrechte.getSchreiben());
		ps.setInt(3, benutzerrechte.getBenutzerid());
		ps.setInt(4, benutzerrechte.getRechtid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Benutzerrechte benutzerrechte) throws SQLException {
		ps.setInt(1, benutzerrechte.getBenutzerid());
		ps.setInt(2, benutzerrechte.getRechtid());
	}

}
