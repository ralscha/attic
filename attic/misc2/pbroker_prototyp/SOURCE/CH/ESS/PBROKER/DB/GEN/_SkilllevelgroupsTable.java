package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _SkilllevelgroupsTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.SkillLevelGroups";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT SkillLevelGroupId,SkillLevelGroup FROM dbo.SkillLevelGroups";
	private final static String countSQL = "SELECT count(*) FROM dbo.SkillLevelGroups";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.SkillLevelGroups(SkillLevelGroupId,SkillLevelGroup) VALUES(?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.SkillLevelGroups SET SkillLevelGroup=? WHERE SkillLevelGroupId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE SkillLevelGroupId=?";

	public _SkilllevelgroupsTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Skilllevelgroups skilllevelgroups) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			skilllevelgroups.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, skilllevelgroups);
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
			Skilllevelgroups skilllevelgroups = (Skilllevelgroups)resultList.get(i);
			skilllevelgroups.invalidateBackRelations();
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

	public Skilllevelgroups selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Skilllevelgroups)resultList.get(0);
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

	public int insert(Skilllevelgroups skilllevelgroups) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, skilllevelgroups);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Skilllevelgroups skilllevelgroups) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			skilllevelgroups.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, skilllevelgroups);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Skilllevelgroups makeObject(ResultSet rs) throws SQLException {
		return new Skilllevelgroups(rs.getInt("skilllevelgroupid"), rs.getString("skilllevelgroup"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Skilllevelgroups skilllevelgroups) throws SQLException {
		ps.setInt(1, skilllevelgroups.getSkilllevelgroupid());
		ps.setString(2, skilllevelgroups.getSkilllevelgroup());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Skilllevelgroups skilllevelgroups) throws SQLException {
		ps.setString(1, skilllevelgroups.getSkilllevelgroup());
		ps.setInt(2, skilllevelgroups.getSkilllevelgroupid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Skilllevelgroups skilllevelgroups) throws SQLException {
		ps.setInt(1, skilllevelgroups.getSkilllevelgroupid());
	}

}
