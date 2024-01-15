package gtf.ss.common;

import java.sql.*;
import java.math.*;
import java.util.Iterator;

public class SS_BANK_REFTable {

	private Connection conn;
	private PreparedStatement insertPS;
	private PreparedStatement selectAmountPS = null;
	private PreparedStatement selectAmountBetweenPS = null;
	
	private final static String deleteSQL = "DELETE FROM SS_BANK_REF";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT SERVICECENTER,DOSSIER_NUMBER,BRANCH,BRANCH_GROUP,BANK_REF_NO,BANK_REF_NO_S,BANK_NAME,BANK_NAME_S,BANK_LOCATION,ISO_CODE,AMOUNT FROM SS_BANK_REF";
	private final static String amountWhereSQL = "AMOUNT = ?";
	private final static String amountBetweenWhereSQL = "AMOUNT >= ? AND AMOUNT <= ?";
	
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO SS_BANK_REF(SERVICECENTER,DOSSIER_NUMBER,BRANCH,BRANCH_GROUP,BANK_REF_NO,BANK_REF_NO_S,BANK_NAME,BANK_NAME_S,BANK_LOCATION,ISO_CODE,AMOUNT) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		
	public SS_BANK_REFTable(Connection conn) {
		this.conn = conn;
		insertPS = null;
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

	public Iterator selectAmount(BigDecimal amount) throws SQLException {
		return selectAmount(amount, null);
	}

	
	public Iterator selectAmount(BigDecimal fromAmount, BigDecimal toAmount) throws SQLException {
		
		final ResultSet rs;
		
		if (toAmount == null) {
			if (selectAmountPS == null) 
				selectAmountPS = conn.prepareStatement(selectSQL+whereSQL+amountWhereSQL);
			
			selectAmountPS.setBigDecimal(1, fromAmount);	
			
			rs = selectAmountPS.executeQuery();
		} else {
			if (selectAmountBetweenPS == null) 
				selectAmountBetweenPS = conn.prepareStatement(selectSQL+whereSQL+amountBetweenWhereSQL);
			
			selectAmountBetweenPS.setBigDecimal(1, fromAmount);	
			selectAmountBetweenPS.setBigDecimal(2, toAmount);	

			
			rs = selectAmountBetweenPS.executeQuery();		
		}


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
	
	public int insert(SS_BANK_REF ss_bank_ref) throws SQLException {
		if (insertPS == null)
			insertPS = conn.prepareStatement(insertSQL);

		prepareInsertStatement(insertPS, ss_bank_ref);
		return insertPS.executeUpdate();
	}

	public static SS_BANK_REF makeObject(ResultSet rs) throws SQLException {
		return new SS_BANK_REF(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getBigDecimal(11,3));
	}

	private void prepareInsertStatement(PreparedStatement ps, SS_BANK_REF ss_bank_ref) throws SQLException {
		ps.setString(1, ss_bank_ref.getSERVICECENTER());
		ps.setInt(2, ss_bank_ref.getDOSSIER_NUMBER());
		ps.setString(3, ss_bank_ref.getBRANCH());
		ps.setString(4, ss_bank_ref.getBRANCH_GROUP());
		ps.setString(5, ss_bank_ref.getBANK_REF_NO());
		ps.setString(6, ss_bank_ref.getBANK_REF_NO_S());
		ps.setString(7, ss_bank_ref.getBANK_NAME());
		ps.setString(8, ss_bank_ref.getBANK_NAME_S());
		ps.setString(9, ss_bank_ref.getBANK_LOCATION());
		ps.setString(10, ss_bank_ref.getISO_CODE());
		ps.setBigDecimal(11, ss_bank_ref.getAMOUNT());
	}
}
