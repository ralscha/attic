package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _AnfragekandidatenTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.AnfrageKandidaten";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT KandidatId,AnfrageId,HardCopy,Vorstellung,Ablehung FROM dbo.AnfrageKandidaten";
	private final static String countSQL = "SELECT count(*) FROM dbo.AnfrageKandidaten";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.AnfrageKandidaten(KandidatId,AnfrageId,HardCopy,Vorstellung,Ablehung) VALUES(?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.AnfrageKandidaten SET HardCopy=?, Vorstellung=?, Ablehung=? WHERE KandidatId=? AND AnfrageId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE KandidatId=? AND AnfrageId=?";

	public _AnfragekandidatenTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Anfragekandidaten anfragekandidaten) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, anfragekandidaten);
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

	public Anfragekandidaten selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Anfragekandidaten)resultList.get(0);
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

	public int insert(Anfragekandidaten anfragekandidaten) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, anfragekandidaten);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Anfragekandidaten anfragekandidaten) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, anfragekandidaten);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Anfragekandidaten makeObject(ResultSet rs) throws SQLException {
		return new Anfragekandidaten(rs.getInt("kandidatid"), rs.getInt("anfrageid"), rs.getTimestamp("hardcopy"), rs.getTimestamp("vorstellung"), rs.getBoolean("ablehung"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Anfragekandidaten anfragekandidaten) throws SQLException {
		ps.setInt(1, anfragekandidaten.getKandidatid());
		ps.setInt(2, anfragekandidaten.getAnfrageid());
		ps.setTimestamp(3, anfragekandidaten.getHardcopy());
		ps.setTimestamp(4, anfragekandidaten.getVorstellung());
		ps.setBoolean(5, anfragekandidaten.getAblehung());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Anfragekandidaten anfragekandidaten) throws SQLException {
		ps.setTimestamp(1, anfragekandidaten.getHardcopy());
		ps.setTimestamp(2, anfragekandidaten.getVorstellung());
		ps.setBoolean(3, anfragekandidaten.getAblehung());
		ps.setInt(4, anfragekandidaten.getKandidatid());
		ps.setInt(5, anfragekandidaten.getAnfrageid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Anfragekandidaten anfragekandidaten) throws SQLException {
		ps.setInt(1, anfragekandidaten.getKandidatid());
		ps.setInt(2, anfragekandidaten.getAnfrageid());
	}

}
