package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _KandidatenTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Kandidaten";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT LieferantId,KandidatId,Anrede,Vorname,Name,Geburtsdatum,Strasse,Land,PLZ,Ort,Telefon,TelDirekt,Mobil,EMail,Fax,Stdsatz,Notiz,SwisscomErfahrung,Skills,Titel,Kalender_alt FROM dbo.Kandidaten";
	private final static String countSQL = "SELECT count(*) FROM dbo.Kandidaten";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Kandidaten(LieferantId,KandidatId,Anrede,Vorname,Name,Geburtsdatum,Strasse,Land,PLZ,Ort,Telefon,TelDirekt,Mobil,EMail,Fax,Stdsatz,Notiz,SwisscomErfahrung,Skills,Titel,Kalender_alt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Kandidaten SET LieferantId=?, Anrede=?, Vorname=?, Name=?, Geburtsdatum=?, Strasse=?, Land=?, PLZ=?, Ort=?, Telefon=?, TelDirekt=?, Mobil=?, EMail=?, Fax=?, Stdsatz=?, Notiz=?, SwisscomErfahrung=?, Skills=?, Titel=?, Kalender_alt=? WHERE KandidatId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE KandidatId=?";

	public _KandidatenTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Kandidaten kandidaten) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			kandidaten.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, kandidaten);
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
			Kandidaten kandidaten = (Kandidaten)resultList.get(i);
			kandidaten.invalidateBackRelations();
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

	public Kandidaten selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Kandidaten)resultList.get(0);
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

	public int insert(Kandidaten kandidaten) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, kandidaten);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Kandidaten kandidaten) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			kandidaten.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, kandidaten);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Kandidaten makeObject(ResultSet rs) throws SQLException {
		return new Kandidaten(rs.getInt("lieferantid"), rs.getInt("kandidatid"), rs.getString("anrede"), rs.getString("vorname"), rs.getString("name"), rs.getTimestamp("geburtsdatum"), rs.getString("strasse"), rs.getString("land"), rs.getString("plz"), rs.getString("ort"), rs.getString("telefon"), rs.getString("teldirekt"), rs.getString("mobil"), rs.getString("email"), rs.getString("fax"), rs.getBigDecimal("stdsatz",4), rs.getString("notiz"), rs.getBoolean("swisscomerfahrung"), rs.getString("skills"), rs.getString("titel"), rs.getnull("kalender_alt"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Kandidaten kandidaten) throws SQLException {
		ps.setInt(1, kandidaten.getLieferantid());
		ps.setInt(2, kandidaten.getKandidatid());
		ps.setString(3, kandidaten.getAnrede());
		ps.setString(4, kandidaten.getVorname());
		ps.setString(5, kandidaten.getName());
		ps.setTimestamp(6, kandidaten.getGeburtsdatum());
		ps.setString(7, kandidaten.getStrasse());
		ps.setString(8, kandidaten.getLand());
		ps.setString(9, kandidaten.getPlz());
		ps.setString(10, kandidaten.getOrt());
		ps.setString(11, kandidaten.getTelefon());
		ps.setString(12, kandidaten.getTeldirekt());
		ps.setString(13, kandidaten.getMobil());
		ps.setString(14, kandidaten.getEmail());
		ps.setString(15, kandidaten.getFax());
		ps.setBigDecimal(16, kandidaten.getStdsatz());
		ps.setString(17, kandidaten.getNotiz());
		ps.setBoolean(18, kandidaten.getSwisscomerfahrung());
		ps.setString(19, kandidaten.getSkills());
		ps.setString(20, kandidaten.getTitel());
		ps.setnull(21, kandidaten.getKalender_alt());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Kandidaten kandidaten) throws SQLException {
		ps.setInt(1, kandidaten.getLieferantid());
		ps.setString(2, kandidaten.getAnrede());
		ps.setString(3, kandidaten.getVorname());
		ps.setString(4, kandidaten.getName());
		ps.setTimestamp(5, kandidaten.getGeburtsdatum());
		ps.setString(6, kandidaten.getStrasse());
		ps.setString(7, kandidaten.getLand());
		ps.setString(8, kandidaten.getPlz());
		ps.setString(9, kandidaten.getOrt());
		ps.setString(10, kandidaten.getTelefon());
		ps.setString(11, kandidaten.getTeldirekt());
		ps.setString(12, kandidaten.getMobil());
		ps.setString(13, kandidaten.getEmail());
		ps.setString(14, kandidaten.getFax());
		ps.setBigDecimal(15, kandidaten.getStdsatz());
		ps.setString(16, kandidaten.getNotiz());
		ps.setBoolean(17, kandidaten.getSwisscomerfahrung());
		ps.setString(18, kandidaten.getSkills());
		ps.setString(19, kandidaten.getTitel());
		ps.setnull(20, kandidaten.getKalender_alt());
		ps.setInt(21, kandidaten.getKandidatid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Kandidaten kandidaten) throws SQLException {
		ps.setInt(1, kandidaten.getKandidatid());
	}

}
