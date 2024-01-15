package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _KandidatenskillsTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.KandidatenSkills";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT KandidatId,SkillId,SkillLevelId,Experience FROM dbo.KandidatenSkills";
	private final static String countSQL = "SELECT count(*) FROM dbo.KandidatenSkills";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.KandidatenSkills(KandidatId,SkillId,SkillLevelId,Experience) VALUES(?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.KandidatenSkills SET SkillLevelId=?, Experience=? WHERE KandidatId=? AND SkillId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE KandidatId=? AND SkillId=?";

	public _KandidatenskillsTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Kandidatenskills kandidatenskills) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, kandidatenskills);
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

	public Kandidatenskills selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Kandidatenskills)resultList.get(0);
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

	public int insert(Kandidatenskills kandidatenskills) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, kandidatenskills);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Kandidatenskills kandidatenskills) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, kandidatenskills);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Kandidatenskills makeObject(ResultSet rs) throws SQLException {
		return new Kandidatenskills(rs.getInt("kandidatid"), rs.getInt("skillid"), rs.getInt("skilllevelid"), rs.getDouble("experience"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Kandidatenskills kandidatenskills) throws SQLException {
		ps.setInt(1, kandidatenskills.getKandidatid());
		ps.setInt(2, kandidatenskills.getSkillid());
		ps.setInt(3, kandidatenskills.getSkilllevelid());
		ps.setDouble(4, kandidatenskills.getExperience());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Kandidatenskills kandidatenskills) throws SQLException {
		ps.setInt(1, kandidatenskills.getSkilllevelid());
		ps.setDouble(2, kandidatenskills.getExperience());
		ps.setInt(3, kandidatenskills.getKandidatid());
		ps.setInt(4, kandidatenskills.getSkillid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Kandidatenskills kandidatenskills) throws SQLException {
		ps.setInt(1, kandidatenskills.getKandidatid());
		ps.setInt(2, kandidatenskills.getSkillid());
	}

}
