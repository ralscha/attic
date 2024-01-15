package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _RekrutierungsangabenTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Rekrutierungsangaben";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT RekId,AnfrageId,Pensum,Projekt,Taetigkeitsgebiet,Skills,Von,Bis,Bemerkung,Ansprechspartner,AnsprechspartnerTel,AnsprechspartnerEmail,Aufgaben,Offertebis,OE FROM dbo.Rekrutierungsangaben";
	private final static String countSQL = "SELECT count(*) FROM dbo.Rekrutierungsangaben";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Rekrutierungsangaben(AnfrageId,Pensum,Projekt,Taetigkeitsgebiet,Skills,Von,Bis,Bemerkung,Ansprechspartner,AnsprechspartnerTel,AnsprechspartnerEmail,Aufgaben,Offertebis,OE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Rekrutierungsangaben SET AnfrageId=?, Pensum=?, Projekt=?, Taetigkeitsgebiet=?, Skills=?, Von=?, Bis=?, Bemerkung=?, Ansprechspartner=?, AnsprechspartnerTel=?, AnsprechspartnerEmail=?, Aufgaben=?, Offertebis=?, OE=? WHERE RekId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE RekId=?";

	public _RekrutierungsangabenTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Rekrutierungsangaben rekrutierungsangaben) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, rekrutierungsangaben);
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

	public Rekrutierungsangaben selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Rekrutierungsangaben)resultList.get(0);
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

	public int insert(Rekrutierungsangaben rekrutierungsangaben) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, rekrutierungsangaben);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Rekrutierungsangaben rekrutierungsangaben) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, rekrutierungsangaben);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Rekrutierungsangaben makeObject(ResultSet rs) throws SQLException {
		return new Rekrutierungsangaben(rs.getInt("rekid"), rs.getInt("anfrageid"), rs.getString("pensum"), rs.getString("projekt"), rs.getString("taetigkeitsgebiet"), rs.getString("skills"), rs.getString("von"), rs.getString("bis"), rs.getString("bemerkung"), rs.getString("ansprechspartner"), rs.getString("ansprechspartnertel"), rs.getString("ansprechspartneremail"), rs.getString("aufgaben"), rs.getString("offertebis"), rs.getString("oe"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Rekrutierungsangaben rekrutierungsangaben) throws SQLException {
		ps.setInt(1, rekrutierungsangaben.getAnfrageid());
		ps.setString(2, rekrutierungsangaben.getPensum());
		ps.setString(3, rekrutierungsangaben.getProjekt());
		ps.setString(4, rekrutierungsangaben.getTaetigkeitsgebiet());
		ps.setString(5, rekrutierungsangaben.getSkills());
		ps.setString(6, rekrutierungsangaben.getVon());
		ps.setString(7, rekrutierungsangaben.getBis());
		ps.setString(8, rekrutierungsangaben.getBemerkung());
		ps.setString(9, rekrutierungsangaben.getAnsprechspartner());
		ps.setString(10, rekrutierungsangaben.getAnsprechspartnertel());
		ps.setString(11, rekrutierungsangaben.getAnsprechspartneremail());
		ps.setString(12, rekrutierungsangaben.getAufgaben());
		ps.setString(13, rekrutierungsangaben.getOffertebis());
		ps.setString(14, rekrutierungsangaben.getOe());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Rekrutierungsangaben rekrutierungsangaben) throws SQLException {
		ps.setInt(1, rekrutierungsangaben.getAnfrageid());
		ps.setString(2, rekrutierungsangaben.getPensum());
		ps.setString(3, rekrutierungsangaben.getProjekt());
		ps.setString(4, rekrutierungsangaben.getTaetigkeitsgebiet());
		ps.setString(5, rekrutierungsangaben.getSkills());
		ps.setString(6, rekrutierungsangaben.getVon());
		ps.setString(7, rekrutierungsangaben.getBis());
		ps.setString(8, rekrutierungsangaben.getBemerkung());
		ps.setString(9, rekrutierungsangaben.getAnsprechspartner());
		ps.setString(10, rekrutierungsangaben.getAnsprechspartnertel());
		ps.setString(11, rekrutierungsangaben.getAnsprechspartneremail());
		ps.setString(12, rekrutierungsangaben.getAufgaben());
		ps.setString(13, rekrutierungsangaben.getOffertebis());
		ps.setString(14, rekrutierungsangaben.getOe());
		ps.setInt(15, rekrutierungsangaben.getRekid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Rekrutierungsangaben rekrutierungsangaben) throws SQLException {
		ps.setInt(1, rekrutierungsangaben.getRekid());
	}

}
