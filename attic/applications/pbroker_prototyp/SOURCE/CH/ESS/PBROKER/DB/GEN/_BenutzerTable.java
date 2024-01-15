package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _BenutzerTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Benutzer";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT BenutzerId,Nachname,Vorname,LoginId,Passwort,Aktiv,KundeId,LieferantId FROM dbo.Benutzer";
	private final static String countSQL = "SELECT count(*) FROM dbo.Benutzer";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Benutzer(BenutzerId,Nachname,Vorname,LoginId,Passwort,Aktiv,KundeId,LieferantId) VALUES(?,?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Benutzer SET Nachname=?, Vorname=?, LoginId=?, Passwort=?, Aktiv=?, KundeId=?, LieferantId=? WHERE BenutzerId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE BenutzerId=?";

	public _BenutzerTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Benutzer benutzer) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			benutzer.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, benutzer);
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

			invalidateBackRelations(whereClause);

			if (whereClause == null)
				return (stmt.executeUpdate(deleteSQL));
			else
				return (stmt.executeUpdate(deleteSQL+whereSQL+whereClause));
		} finally {
			SQLManager.getInstance().returnConnection(conn);
		}
	}

	private void invalidateBackRelations(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause);
		for (int i = 0; i < resultList.size(); i++) {
			Benutzer benutzer = (Benutzer)resultList.get(i);
			benutzer.invalidateBackRelations();
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

	public Benutzer selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Benutzer)resultList.get(0);
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

	public int insert(Benutzer benutzer) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, benutzer);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Benutzer benutzer) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			benutzer.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, benutzer);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Benutzer makeObject(ResultSet rs) throws SQLException {
		return new Benutzer(rs.getInt("benutzerid"), rs.getString("nachname"), rs.getString("vorname"), rs.getString("loginid"), rs.getString("passwort"), rs.getBoolean("aktiv"), rs.getInt("kundeid"), rs.getInt("lieferantid"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Benutzer benutzer) throws SQLException {
		ps.setInt(1, benutzer.getBenutzerid());
		ps.setString(2, benutzer.getNachname());
		ps.setString(3, benutzer.getVorname());
		ps.setString(4, benutzer.getLoginid());
		ps.setString(5, benutzer.getPasswort());
		ps.setBoolean(6, benutzer.getAktiv());
		ps.setInt(7, benutzer.getKundeid());
		ps.setInt(8, benutzer.getLieferantid());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Benutzer benutzer) throws SQLException {
		ps.setString(1, benutzer.getNachname());
		ps.setString(2, benutzer.getVorname());
		ps.setString(3, benutzer.getLoginid());
		ps.setString(4, benutzer.getPasswort());
		ps.setBoolean(5, benutzer.getAktiv());
		ps.setInt(6, benutzer.getKundeid());
		ps.setInt(7, benutzer.getLieferantid());
		ps.setInt(8, benutzer.getBenutzerid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Benutzer benutzer) throws SQLException {
		ps.setInt(1, benutzer.getBenutzerid());
	}

}
