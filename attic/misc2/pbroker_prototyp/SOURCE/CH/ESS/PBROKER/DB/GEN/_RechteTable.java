package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _RechteTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Rechte";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT RechtId,Recht FROM dbo.Rechte";
	private final static String countSQL = "SELECT count(*) FROM dbo.Rechte";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Rechte(RechtId,Recht) VALUES(?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Rechte SET Recht=? WHERE RechtId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE RechtId=?";

	public _RechteTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Rechte rechte) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			rechte.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, rechte);
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
			Rechte rechte = (Rechte)resultList.get(i);
			rechte.invalidateBackRelations();
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

	public Rechte selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Rechte)resultList.get(0);
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

	public int insert(Rechte rechte) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, rechte);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Rechte rechte) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			rechte.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, rechte);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Rechte makeObject(ResultSet rs) throws SQLException {
		return new Rechte(rs.getInt("rechtid"), rs.getString("recht"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Rechte rechte) throws SQLException {
		ps.setInt(1, rechte.getRechtid());
		ps.setString(2, rechte.getRecht());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Rechte rechte) throws SQLException {
		ps.setString(1, rechte.getRecht());
		ps.setInt(2, rechte.getRechtid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Rechte rechte) throws SQLException {
		ps.setInt(1, rechte.getRechtid());
	}

}
