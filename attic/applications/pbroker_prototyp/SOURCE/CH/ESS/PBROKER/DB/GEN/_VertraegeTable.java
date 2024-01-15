package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.codestudio.util.SQLManager;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.support.*;
import ch.ess.pbroker.db.*;

public class _VertraegeTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM dbo.Vertraege";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT VertragId,KandidatId,AnfrageId,OfferteId,Arbeitsbeginn,Arbeitsende,Weiterverpflichtung,Stundenzahl,Tage,Stundensatz,Tagessatz,Besonderes,Arbeitsort,VertragsNummer,MA_Anrede,MA_Vorname,MA_Name,MA_Geburtsdatum,MA_Strasse,MA_Land,MA_PLZ,MA_Ort,K1_Anrede,K1_Vorname,K1_Name,K1_Titel,K1_GezeichnetAm,K1_GezeichnetOrt,K2_Anrede,K2_Vorname,K2_Name,K2_Titel,K2_GezeichnetAm,K2_GezeichnetOrt,L1_Anrede,L1_Vorname,L1_Name,L1_Titel,L1_GezeichnetAm,L1_GezeichnetOrt,L2_Anrede,L2_Vorname,L2_Name,L2_Titel,L2_GezeichnetAm,L2_GezeichnetOrt FROM dbo.Vertraege";
	private final static String countSQL = "SELECT count(*) FROM dbo.Vertraege";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO dbo.Vertraege(VertragId,KandidatId,AnfrageId,OfferteId,Arbeitsbeginn,Arbeitsende,Weiterverpflichtung,Stundenzahl,Tage,Stundensatz,Tagessatz,Besonderes,Arbeitsort,VertragsNummer,MA_Anrede,MA_Vorname,MA_Name,MA_Geburtsdatum,MA_Strasse,MA_Land,MA_PLZ,MA_Ort,K1_Anrede,K1_Vorname,K1_Name,K1_Titel,K1_GezeichnetAm,K1_GezeichnetOrt,K2_Anrede,K2_Vorname,K2_Name,K2_Titel,K2_GezeichnetAm,K2_GezeichnetOrt,L1_Anrede,L1_Vorname,L1_Name,L1_Titel,L1_GezeichnetAm,L1_GezeichnetOrt,L2_Anrede,L2_Vorname,L2_Name,L2_Titel,L2_GezeichnetAm,L2_GezeichnetOrt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE dbo.Vertraege SET KandidatId=?, AnfrageId=?, OfferteId=?, Arbeitsbeginn=?, Arbeitsende=?, Weiterverpflichtung=?, Stundenzahl=?, Tage=?, Stundensatz=?, Tagessatz=?, Besonderes=?, Arbeitsort=?, VertragsNummer=?, MA_Anrede=?, MA_Vorname=?, MA_Name=?, MA_Geburtsdatum=?, MA_Strasse=?, MA_Land=?, MA_PLZ=?, MA_Ort=?, K1_Anrede=?, K1_Vorname=?, K1_Name=?, K1_Titel=?, K1_GezeichnetAm=?, K1_GezeichnetOrt=?, K2_Anrede=?, K2_Vorname=?, K2_Name=?, K2_Titel=?, K2_GezeichnetAm=?, K2_GezeichnetOrt=?, L1_Anrede=?, L1_Vorname=?, L1_Name=?, L1_Titel=?, L1_GezeichnetAm=?, L1_GezeichnetOrt=?, L2_Anrede=?, L2_Vorname=?, L2_Name=?, L2_Titel=?, L2_GezeichnetAm=?, L2_GezeichnetOrt=? WHERE VertragId=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE VertragId=?";

	public _VertraegeTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Vertraege vertraege) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, vertraege);
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

	public Vertraege selectOne(String whereClause) throws SQLException, PoolPropsException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Vertraege)resultList.get(0);
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

	public int insert(Vertraege vertraege) throws SQLException, PoolPropsException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = SQLManager.getInstance().requestConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, vertraege);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public int update(Vertraege vertraege) throws SQLException, PoolPropsException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = SQLManager.getInstance().requestConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, vertraege);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				SQLManager.getInstance().returnConnection(conn);
		}
	}

	public static Vertraege makeObject(ResultSet rs) throws SQLException {
		return new Vertraege(rs.getInt("vertragid"), rs.getInt("kandidatid"), rs.getInt("anfrageid"), rs.getInt("offerteid"), rs.getTimestamp("arbeitsbeginn"), rs.getTimestamp("arbeitsende"), rs.getBoolean("weiterverpflichtung"), rs.getInt("stundenzahl"), rs.getInt("tage"), rs.getDouble("stundensatz"), rs.getDouble("tagessatz"), rs.getString("besonderes"), rs.getString("arbeitsort"), rs.getString("vertragsnummer"), rs.getString("ma_anrede"), rs.getString("ma_vorname"), rs.getString("ma_name"), rs.getTimestamp("ma_geburtsdatum"), rs.getString("ma_strasse"), rs.getString("ma_land"), rs.getString("ma_plz"), rs.getString("ma_ort"), rs.getString("k1_anrede"), rs.getString("k1_vorname"), rs.getString("k1_name"), rs.getString("k1_titel"), rs.getString("k1_gezeichnetam"), rs.getString("k1_gezeichnetort"), rs.getString("k2_anrede"), rs.getString("k2_vorname"), rs.getString("k2_name"), rs.getString("k2_titel"), rs.getString("k2_gezeichnetam"), rs.getString("k2_gezeichnetort"), rs.getString("l1_anrede"), rs.getString("l1_vorname"), rs.getString("l1_name"), rs.getString("l1_titel"), rs.getString("l1_gezeichnetam"), rs.getString("l1_gezeichnetort"), rs.getString("l2_anrede"), rs.getString("l2_vorname"), rs.getString("l2_name"), rs.getString("l2_titel"), rs.getString("l2_gezeichnetam"), rs.getString("l2_gezeichnetort"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Vertraege vertraege) throws SQLException {
		ps.setInt(1, vertraege.getVertragid());
		ps.setInt(2, vertraege.getKandidatid());
		ps.setInt(3, vertraege.getAnfrageid());
		ps.setInt(4, vertraege.getOfferteid());
		ps.setTimestamp(5, vertraege.getArbeitsbeginn());
		ps.setTimestamp(6, vertraege.getArbeitsende());
		ps.setBoolean(7, vertraege.getWeiterverpflichtung());
		ps.setInt(8, vertraege.getStundenzahl());
		ps.setInt(9, vertraege.getTage());
		ps.setDouble(10, vertraege.getStundensatz());
		ps.setDouble(11, vertraege.getTagessatz());
		ps.setString(12, vertraege.getBesonderes());
		ps.setString(13, vertraege.getArbeitsort());
		ps.setString(14, vertraege.getVertragsnummer());
		ps.setString(15, vertraege.getMa_anrede());
		ps.setString(16, vertraege.getMa_vorname());
		ps.setString(17, vertraege.getMa_name());
		ps.setTimestamp(18, vertraege.getMa_geburtsdatum());
		ps.setString(19, vertraege.getMa_strasse());
		ps.setString(20, vertraege.getMa_land());
		ps.setString(21, vertraege.getMa_plz());
		ps.setString(22, vertraege.getMa_ort());
		ps.setString(23, vertraege.getK1_anrede());
		ps.setString(24, vertraege.getK1_vorname());
		ps.setString(25, vertraege.getK1_name());
		ps.setString(26, vertraege.getK1_titel());
		ps.setString(27, vertraege.getK1_gezeichnetam());
		ps.setString(28, vertraege.getK1_gezeichnetort());
		ps.setString(29, vertraege.getK2_anrede());
		ps.setString(30, vertraege.getK2_vorname());
		ps.setString(31, vertraege.getK2_name());
		ps.setString(32, vertraege.getK2_titel());
		ps.setString(33, vertraege.getK2_gezeichnetam());
		ps.setString(34, vertraege.getK2_gezeichnetort());
		ps.setString(35, vertraege.getL1_anrede());
		ps.setString(36, vertraege.getL1_vorname());
		ps.setString(37, vertraege.getL1_name());
		ps.setString(38, vertraege.getL1_titel());
		ps.setString(39, vertraege.getL1_gezeichnetam());
		ps.setString(40, vertraege.getL1_gezeichnetort());
		ps.setString(41, vertraege.getL2_anrede());
		ps.setString(42, vertraege.getL2_vorname());
		ps.setString(43, vertraege.getL2_name());
		ps.setString(44, vertraege.getL2_titel());
		ps.setString(45, vertraege.getL2_gezeichnetam());
		ps.setString(46, vertraege.getL2_gezeichnetort());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Vertraege vertraege) throws SQLException {
		ps.setInt(1, vertraege.getKandidatid());
		ps.setInt(2, vertraege.getAnfrageid());
		ps.setInt(3, vertraege.getOfferteid());
		ps.setTimestamp(4, vertraege.getArbeitsbeginn());
		ps.setTimestamp(5, vertraege.getArbeitsende());
		ps.setBoolean(6, vertraege.getWeiterverpflichtung());
		ps.setInt(7, vertraege.getStundenzahl());
		ps.setInt(8, vertraege.getTage());
		ps.setDouble(9, vertraege.getStundensatz());
		ps.setDouble(10, vertraege.getTagessatz());
		ps.setString(11, vertraege.getBesonderes());
		ps.setString(12, vertraege.getArbeitsort());
		ps.setString(13, vertraege.getVertragsnummer());
		ps.setString(14, vertraege.getMa_anrede());
		ps.setString(15, vertraege.getMa_vorname());
		ps.setString(16, vertraege.getMa_name());
		ps.setTimestamp(17, vertraege.getMa_geburtsdatum());
		ps.setString(18, vertraege.getMa_strasse());
		ps.setString(19, vertraege.getMa_land());
		ps.setString(20, vertraege.getMa_plz());
		ps.setString(21, vertraege.getMa_ort());
		ps.setString(22, vertraege.getK1_anrede());
		ps.setString(23, vertraege.getK1_vorname());
		ps.setString(24, vertraege.getK1_name());
		ps.setString(25, vertraege.getK1_titel());
		ps.setString(26, vertraege.getK1_gezeichnetam());
		ps.setString(27, vertraege.getK1_gezeichnetort());
		ps.setString(28, vertraege.getK2_anrede());
		ps.setString(29, vertraege.getK2_vorname());
		ps.setString(30, vertraege.getK2_name());
		ps.setString(31, vertraege.getK2_titel());
		ps.setString(32, vertraege.getK2_gezeichnetam());
		ps.setString(33, vertraege.getK2_gezeichnetort());
		ps.setString(34, vertraege.getL1_anrede());
		ps.setString(35, vertraege.getL1_vorname());
		ps.setString(36, vertraege.getL1_name());
		ps.setString(37, vertraege.getL1_titel());
		ps.setString(38, vertraege.getL1_gezeichnetam());
		ps.setString(39, vertraege.getL1_gezeichnetort());
		ps.setString(40, vertraege.getL2_anrede());
		ps.setString(41, vertraege.getL2_vorname());
		ps.setString(42, vertraege.getL2_name());
		ps.setString(43, vertraege.getL2_titel());
		ps.setString(44, vertraege.getL2_gezeichnetam());
		ps.setString(45, vertraege.getL2_gezeichnetort());
		ps.setInt(46, vertraege.getVertragid());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Vertraege vertraege) throws SQLException {
		ps.setInt(1, vertraege.getVertragid());
	}

}
