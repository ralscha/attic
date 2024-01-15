package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _LieferantenTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Lieferanten";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT LieferantId,Lieferant,Strasse,PLZ,Ort,Land,Telefon,EMail,Fax,Notiz FROM dbo.Lieferanten";
	private final static String countSQL = "SELECT count(*) FROM dbo.Lieferanten";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Lieferanten(LieferantId,Lieferant,Strasse,PLZ,Ort,Land,Telefon,EMail,Fax,Notiz) VALUES(?,?,?,?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Lieferanten SET Lieferant=?, Strasse=?, PLZ=?, Ort=?, Land=?, Telefon=?, EMail=?, Fax=?, Notiz=? WHERE LieferantId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE LieferantId=?";

	public _LieferantenTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Lieferanten lieferanten) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			lieferanten.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, lieferanten);
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
			Lieferanten lieferanten = (Lieferanten)resultList.get(i);
			lieferanten.invalidateBackRelations();
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

	public Lieferanten selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Lieferanten)resultList.get(0);
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

	public int insert(Lieferanten lieferanten) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, lieferanten);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Lieferanten lieferanten) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			lieferanten.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, lieferanten);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Lieferanten makeObject(ResultSet rs) throws SQLException {
		return new Lieferanten(rs.getInt("lieferantid"), rs.getString("lieferant"), rs.getString("strasse"), rs.getString("plz"), rs.getString("ort"), rs.getString("land"), rs.getString("telefon"), rs.getString("email"), rs.getString("fax"), rs.getString("notiz"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Lieferanten lieferanten) throws SQLException {
		ps.setInt(1, lieferanten.getLieferantid());
		ps.setString(2, lieferanten.getLieferant());
		ps.setString(3, lieferanten.getStrasse());
		ps.setString(4, lieferanten.getPlz());
		ps.setString(5, lieferanten.getOrt());
		ps.setString(6, lieferanten.getLand());
		ps.setString(7, lieferanten.getTelefon());
		ps.setString(8, lieferanten.getEmail());
		ps.setString(9, lieferanten.getFax());
		ps.setString(10, lieferanten.getNotiz());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Lieferanten lieferanten) throws SQLException {
		ps.setString(1, lieferanten.getLieferant());
		ps.setString(2, lieferanten.getStrasse());
		ps.setString(3, lieferanten.getPlz());
		ps.setString(4, lieferanten.getOrt());
		ps.setString(5, lieferanten.getLand());
		ps.setString(6, lieferanten.getTelefon());
		ps.setString(7, lieferanten.getEmail());
		ps.setString(8, lieferanten.getFax());
		ps.setString(9, lieferanten.getNotiz());
		ps.setInt(10, lieferanten.getLieferantid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Lieferanten lieferanten) throws SQLException {
		ps.setInt(1, lieferanten.getLieferantid());
	}

}
