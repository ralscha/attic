package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _KategorienTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Kategorien";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT KategorieId,Kategorie,hasTopics,isOnQueryForm,isHorizontal,showAND,showOR FROM dbo.Kategorien";
	private final static String countSQL = "SELECT count(*) FROM dbo.Kategorien";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Kategorien(KategorieId,Kategorie,hasTopics,isOnQueryForm,isHorizontal,showAND,showOR) VALUES(?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Kategorien SET Kategorie=?, hasTopics=?, isOnQueryForm=?, isHorizontal=?, showAND=?, showOR=? WHERE KategorieId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE KategorieId=?";

	public _KategorienTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Kategorien kategorien) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			kategorien.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, kategorien);
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
			Kategorien kategorien = (Kategorien)resultList.get(i);
			kategorien.invalidateBackRelations();
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

	public Kategorien selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Kategorien)resultList.get(0);
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

	public int insert(Kategorien kategorien) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, kategorien);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Kategorien kategorien) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			kategorien.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, kategorien);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Kategorien makeObject(ResultSet rs) throws SQLException {
		return new Kategorien(rs.getInt("kategorieid"), rs.getString("kategorie"), rs.getBoolean("hastopics"), rs.getBoolean("isonqueryform"), rs.getBoolean("ishorizontal"), rs.getBoolean("showand"), rs.getBoolean("showor"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Kategorien kategorien) throws SQLException {
		ps.setInt(1, kategorien.getKategorieid());
		ps.setString(2, kategorien.getKategorie());
		ps.setBoolean(3, kategorien.getHastopics());
		ps.setBoolean(4, kategorien.getIsonqueryform());
		ps.setBoolean(5, kategorien.getIshorizontal());
		ps.setBoolean(6, kategorien.getShowand());
		ps.setBoolean(7, kategorien.getShowor());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Kategorien kategorien) throws SQLException {
		ps.setString(1, kategorien.getKategorie());
		ps.setBoolean(2, kategorien.getHastopics());
		ps.setBoolean(3, kategorien.getIsonqueryform());
		ps.setBoolean(4, kategorien.getIshorizontal());
		ps.setBoolean(5, kategorien.getShowand());
		ps.setBoolean(6, kategorien.getShowor());
		ps.setInt(7, kategorien.getKategorieid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Kategorien kategorien) throws SQLException {
		ps.setInt(1, kategorien.getKategorieid());
	}

}
