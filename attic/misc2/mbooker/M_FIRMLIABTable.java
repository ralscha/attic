package gtf.mbooker;

import java.sql.*;
import java.util.Iterator;
import java.math.BigDecimal;

public class M_FIRMLIABTable {

	private Connection conn;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private final static String deleteSQL = "DELETE FROM GTFLC.M_FIRMLIAB";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT SEQUENCENUMBER,GTFNUMBER,DEBACCT_NUMBER,CREACCT_NUMBER,VALUEDATE,CURRENCY,AMOUNT,EXCHANGERATE,GTFPROCCENTER,CUSTOMERREF,GTFTYPE,DOSSIERITEM,BUCODE,CREATOR,TS FROM GTFLC.M_FIRMLIAB";
	private final static String orderSQL  = " ORDER BY ";
	private final static String countSQL = "SELECT COUNT(*) FROM GTFLC.M_CONTLIAB";
	
	private final static String insertSQL = 
		"INSERT INTO GTFLC.M_FIRMLIAB(SEQUENCENUMBER,GTFNUMBER,DEBACCT_NUMBER,CREACCT_NUMBER,VALUEDATE,CURRENCY,AMOUNT,EXCHANGERATE,GTFPROCCENTER,CUSTOMERREF,GTFTYPE,DOSSIERITEM,BUCODE,CREATOR,TS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public M_FIRMLIABTable(Connection conn) {
		this.conn = conn;
		insertPS = null;
	}

	public int count() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(countSQL);
		
		int count = 0;
		if (rs.next())
			count = rs.getInt(1);
			
		rs.close();
		stmt.close();
		return count;
	}

	public int delete() throws SQLException {
		return (delete(null));
	}

	public int delete(String whereClause) throws SQLException {
		Statement stmt = conn.createStatement();
		if (whereClause == null)
			return (stmt.executeUpdate(deleteSQL));
		else
			return (stmt.executeUpdate(deleteSQL+whereSQL+whereClause));
	}

	public Iterator select() throws SQLException {
		return select(null, null);
	}

	public Iterator select(String whereClause) throws SQLException {
		return select(whereClause, null);
	}

	public Iterator select(String whereClause, String orderClause) throws SQLException {
		StringBuffer sb = new StringBuffer(selectSQL);

		if (whereClause != null)
			sb.append(whereSQL).append(whereClause);
		if (orderClause != null)
			sb.append(orderSQL).append(orderClause);

		Statement stmt = conn.createStatement();
		final ResultSet rs = stmt.executeQuery(sb.toString());

		return new Iterator() {
			public boolean hasNext() {
				try {
					return rs.next();
				} catch (SQLException sqle) {
					System.err.println(sqle);
					return false;
				}
			}
			public Object next() {
				try {
					return makeObject(rs);

				} catch (SQLException sqle) {
					System.err.println(sqle);
					return null;
				}
			} 
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public int insert(FirmLiability m_firmliab) throws SQLException {
		if (insertPS == null)
			insertPS = conn.prepareStatement(insertSQL);

		prepareInsertStatement(insertPS, m_firmliab);
		return insertPS.executeUpdate();
	}

	public static FirmLiability makeObject(ResultSet rs) throws SQLException {
		return new FirmLiability(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
			rs.getString(5), rs.getString(6), rs.getBigDecimal(7,3).doubleValue(), rs.getBigDecimal(8,8), 
			rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), 
			rs.getString(13), rs.getString(14), rs.getTimestamp(15));
	}

	private void prepareInsertStatement(PreparedStatement ps, FirmLiability m_firmliab) throws SQLException {
		ps.setInt(1, m_firmliab.getSequence_number());
		ps.setString(2, m_firmliab.getGtf_number());
		ps.setString(3, m_firmliab.getDeb_acct_number());
		ps.setString(4, m_firmliab.getCre_acct_number());
		ps.setString(5, m_firmliab.getValue_date());
		ps.setString(6, m_firmliab.getCurrency());
		ps.setBigDecimal(7, new BigDecimal(m_firmliab.getAmount()));
		ps.setBigDecimal(8, m_firmliab.getExchange_rate());
		ps.setString(9, m_firmliab.getGtf_proc_center());
		ps.setString(10, m_firmliab.getCustomer_ref());
		ps.setString(11, m_firmliab.getGtf_type());
		ps.setString(12, m_firmliab.getDossier_item());
		ps.setString(13, m_firmliab.getBu_code());
		ps.setString(14, m_firmliab.getCreator());
		ps.setTimestamp(15, new java.sql.Timestamp(m_firmliab.getTimestamp().getTime()));
	}
}