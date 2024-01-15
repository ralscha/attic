package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _KundenTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Kunden";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT KundeId,Kunde,Strasse,PLZ,Ort,Land,Telefon,EMail,Fax,Notiz FROM dbo.Kunden";
	private final static String countSQL = "SELECT count(*) FROM dbo.Kunden";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Kunden(KundeId,Kunde,Strasse,PLZ,Ort,Land,Telefon,EMail,Fax,Notiz) VALUES(?,?,?,?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Kunden SET Kunde=?, Strasse=?, PLZ=?, Ort=?, Land=?, Telefon=?, EMail=?, Fax=?, Notiz=? WHERE KundeId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE KundeId=?";

	public _KundenTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Kunden kunden) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			kunden.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, kunden);
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
			Kunden kunden = (Kunden)resultList.get(i);
			kunden.invalidateBackRelations();
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

	public Kunden selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Kunden)resultList.get(0);
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

	public int insert(Kunden kunden) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, kunden);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Kunden kunden) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			kunden.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, kunden);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Kunden makeObject(ResultSet rs) throws SQLException {
		return new Kunden(rs.getInt("kundeid"), rs.getString("kunde"), rs.getString("strasse"), rs.getString("plz"), rs.getString("ort"), rs.getString("land"), rs.getString("telefon"), rs.getString("email"), rs.getString("fax"), rs.getString("notiz"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Kunden kunden) throws SQLException {
		ps.setInt(1, kunden.getKundeid());
		ps.setString(2, kunden.getKunde());
		ps.setString(3, kunden.getStrasse());
		ps.setString(4, kunden.getPlz());
		ps.setString(5, kunden.getOrt());
		ps.setString(6, kunden.getLand());
		ps.setString(7, kunden.getTelefon());
		ps.setString(8, kunden.getEmail());
		ps.setString(9, kunden.getFax());
		ps.setString(10, kunden.getNotiz());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Kunden kunden) throws SQLException {
		ps.setString(1, kunden.getKunde());
		ps.setString(2, kunden.getStrasse());
		ps.setString(3, kunden.getPlz());
		ps.setString(4, kunden.getOrt());
		ps.setString(5, kunden.getLand());
		ps.setString(6, kunden.getTelefon());
		ps.setString(7, kunden.getEmail());
		ps.setString(8, kunden.getFax());
		ps.setString(9, kunden.getNotiz());
		ps.setInt(10, kunden.getKundeid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Kunden kunden) throws SQLException {
		ps.setInt(1, kunden.getKundeid());
	}

}
