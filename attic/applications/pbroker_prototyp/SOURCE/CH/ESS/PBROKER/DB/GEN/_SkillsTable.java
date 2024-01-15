package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _SkillsTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Skills";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT Skill,TopicId,SkillId FROM dbo.Skills";
	private final static String countSQL = "SELECT count(*) FROM dbo.Skills";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Skills(Skill,TopicId,SkillId) VALUES(?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Skills SET Skill=?, TopicId=? WHERE SkillId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE SkillId=?";

	public _SkillsTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Skills skills) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			skills.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, skills);
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
			Skills skills = (Skills)resultList.get(i);
			skills.invalidateBackRelations();
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

	public Skills selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Skills)resultList.get(0);
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

	public int insert(Skills skills) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, skills);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Skills skills) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			skills.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, skills);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Skills makeObject(ResultSet rs) throws SQLException {
		return new Skills(rs.getString("skill"), rs.getInt("topicid"), rs.getInt("skillid"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Skills skills) throws SQLException {
		ps.setString(1, skills.getSkill());
		ps.setInt(2, skills.getTopicid());
		ps.setInt(3, skills.getSkillid());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Skills skills) throws SQLException {
		ps.setString(1, skills.getSkill());
		ps.setInt(2, skills.getTopicid());
		ps.setInt(3, skills.getSkillid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Skills skills) throws SQLException {
		ps.setInt(1, skills.getSkillid());
	}

}
