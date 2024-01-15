package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _SkilllevelsTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.SkillLevels";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT SkillLevelId,SkillLevel,SkillLevelGroupId FROM dbo.SkillLevels";
	private final static String countSQL = "SELECT count(*) FROM dbo.SkillLevels";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.SkillLevels(SkillLevelId,SkillLevel,SkillLevelGroupId) VALUES(?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.SkillLevels SET SkillLevel=?, SkillLevelGroupId=? WHERE SkillLevelId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE SkillLevelId=?";

	public _SkilllevelsTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Skilllevels skilllevels) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			skilllevels.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, skilllevels);
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
			Skilllevels skilllevels = (Skilllevels)resultList.get(i);
			skilllevels.invalidateBackRelations();
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

	public Skilllevels selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Skilllevels)resultList.get(0);
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

	public int insert(Skilllevels skilllevels) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, skilllevels);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Skilllevels skilllevels) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			skilllevels.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, skilllevels);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Skilllevels makeObject(ResultSet rs) throws SQLException {
		return new Skilllevels(rs.getInt("skilllevelid"), rs.getString("skilllevel"), rs.getInt("skilllevelgroupid"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Skilllevels skilllevels) throws SQLException {
		ps.setInt(1, skilllevels.getSkilllevelid());
		ps.setString(2, skilllevels.getSkilllevel());
		ps.setInt(3, skilllevels.getSkilllevelgroupid());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Skilllevels skilllevels) throws SQLException {
		ps.setString(1, skilllevels.getSkilllevel());
		ps.setInt(2, skilllevels.getSkilllevelgroupid());
		ps.setInt(3, skilllevels.getSkilllevelid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Skilllevels skilllevels) throws SQLException {
		ps.setInt(1, skilllevels.getSkilllevelid());
	}

}
