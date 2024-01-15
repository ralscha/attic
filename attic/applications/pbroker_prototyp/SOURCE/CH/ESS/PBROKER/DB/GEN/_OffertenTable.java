package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _OffertenTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Offerten";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT OfferteId,KandidatId,AnfrageId,Arbeitsbeginn,Weiterverpflichtung,Stundenzahl,Tage,Stundensatz,Tagessatz,Besonderes FROM dbo.Offerten";
	private final static String countSQL = "SELECT count(*) FROM dbo.Offerten";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Offerten(OfferteId,KandidatId,AnfrageId,Arbeitsbeginn,Weiterverpflichtung,Stundenzahl,Tage,Stundensatz,Tagessatz,Besonderes) VALUES(?,?,?,?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Offerten SET KandidatId=?, AnfrageId=?, Arbeitsbeginn=?, Weiterverpflichtung=?, Stundenzahl=?, Tage=?, Stundensatz=?, Tagessatz=?, Besonderes=? WHERE OfferteId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE OfferteId=?";

	public _OffertenTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Offerten offerten) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			offerten.invalidateBackRelations();

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, offerten);
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
			Offerten offerten = (Offerten)resultList.get(i);
			offerten.invalidateBackRelations();
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

	public Offerten selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Offerten)resultList.get(0);
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

	public int insert(Offerten offerten) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, offerten);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Offerten offerten) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			offerten.invalidateBackRelations();

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, offerten);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Offerten makeObject(ResultSet rs) throws SQLException {
		return new Offerten(rs.getInt("offerteid"), rs.getInt("kandidatid"), rs.getInt("anfrageid"), rs.getString("arbeitsbeginn"), rs.getBoolean("weiterverpflichtung"), rs.getInt("stundenzahl"), rs.getInt("tage"), rs.getDouble("stundensatz"), rs.getDouble("tagessatz"), rs.getString("besonderes"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Offerten offerten) throws SQLException {
		ps.setInt(1, offerten.getOfferteid());
		ps.setInt(2, offerten.getKandidatid());
		ps.setInt(3, offerten.getAnfrageid());
		ps.setString(4, offerten.getArbeitsbeginn());
		ps.setBoolean(5, offerten.getWeiterverpflichtung());
		ps.setInt(6, offerten.getStundenzahl());
		ps.setInt(7, offerten.getTage());
		ps.setDouble(8, offerten.getStundensatz());
		ps.setDouble(9, offerten.getTagessatz());
		ps.setString(10, offerten.getBesonderes());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Offerten offerten) throws SQLException {
		ps.setInt(1, offerten.getKandidatid());
		ps.setInt(2, offerten.getAnfrageid());
		ps.setString(3, offerten.getArbeitsbeginn());
		ps.setBoolean(4, offerten.getWeiterverpflichtung());
		ps.setInt(5, offerten.getStundenzahl());
		ps.setInt(6, offerten.getTage());
		ps.setDouble(7, offerten.getStundensatz());
		ps.setDouble(8, offerten.getTagessatz());
		ps.setString(9, offerten.getBesonderes());
		ps.setInt(10, offerten.getOfferteid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Offerten offerten) throws SQLException {
		ps.setInt(1, offerten.getOfferteid());
	}

}
