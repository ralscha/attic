package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _TopicsTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Topics";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT TopicId,Topic,KategorieId,SkillLevelGroupId,hasSkillLevelField,hasExperienceField,allowNewSkills FROM dbo.Topics";
	private final static String countSQL = "SELECT count(*) FROM dbo.Topics";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Topics(TopicId,Topic,KategorieId,SkillLevelGroupId,hasSkillLevelField,hasExperienceField,allowNewSkills) VALUES(?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Topics SET Topic=?, KategorieId=?, SkillLevelGroupId=?, hasSkillLevelField=?, hasExperienceField=?, allowNewSkills=? WHERE TopicId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE TopicId=?";

	public _TopicsTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Topics topics) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			topics.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, topics);
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
			Topics topics = (Topics)resultList.get(i);
			topics.invalidateBackRelations();
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

	public Topics selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Topics)resultList.get(0);
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

	public int insert(Topics topics) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, topics);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Topics topics) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			topics.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, topics);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Topics makeObject(ResultSet rs) throws SQLException {
		return new Topics(rs.getInt("topicid"), rs.getString("topic"), rs.getInt("kategorieid"), rs.getInt("skilllevelgroupid"), rs.getBoolean("hasskilllevelfield"), rs.getBoolean("hasexperiencefield"), rs.getBoolean("allownewskills"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Topics topics) throws SQLException {
		ps.setInt(1, topics.getTopicid());
		ps.setString(2, topics.getTopic());
		ps.setInt(3, topics.getKategorieid());
		ps.setInt(4, topics.getSkilllevelgroupid());
		ps.setBoolean(5, topics.getHasskilllevelfield());
		ps.setBoolean(6, topics.getHasexperiencefield());
		ps.setBoolean(7, topics.getAllownewskills());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Topics topics) throws SQLException {
		ps.setString(1, topics.getTopic());
		ps.setInt(2, topics.getKategorieid());
		ps.setInt(3, topics.getSkilllevelgroupid());
		ps.setBoolean(4, topics.getHasskilllevelfield());
		ps.setBoolean(5, topics.getHasexperiencefield());
		ps.setBoolean(6, topics.getAllownewskills());
		ps.setInt(7, topics.getTopicid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Topics topics) throws SQLException {
		ps.setInt(1, topics.getTopicid());
	}

}
