package gtf.ss.update;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.math.*;

import common.util.*;
import gtf.common.*;
import gtf.ss.common.*;
import gtf.ss.util.StringUtils;

public class UpdateSSDb {

	private final static BigDecimal ZERO = new BigDecimal(0);
	private final static BigDecimal TEN_PERCENT = new BigDecimal(1.1);

	
	private SS_BANK_REFTable bankRefTable;	
	
	public void update(String args, int hours) {
		
		SwissSearchDB ssDb = null;
		
		try {
						
			ssDb = new SwissSearchDB();
			bankRefTable = new SS_BANK_REFTable(ssDb.getConnection());
			
			ServiceCenters scs = new ServiceCenters();		
			StringTokenizer st = new StringTokenizer(args, ",");
					
			while(st.hasMoreTokens()) {
				String center = st.nextToken();
				if (scs.exists(center)) {
					ServiceCenter sc = scs.getServiceCenter(center);
					System.out.println(sc.getName());
					update(sc.getShortName(), sc.getConnection(), hours);
					sc.closeConnection();
				} else {
					System.out.println(center + " not found");
				}
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		} finally {
			try {
				if (ssDb != null) {
					ssDb.closeConnection();
				}
			} catch (SQLException sqle) {
				System.err.println(sqle);
			}
		}
	}
	
	public void update(String sc, Connection conn, int hours) {
		
		Calendar today = Calendar.getInstance();
		today.add(Calendar.HOUR, -hours);

		java.sql.Timestamp timestmp = new java.sql.Timestamp(today.getTime().getTime());
		
		PreparedStatement selectPS = null;
		PreparedStatement amountSelectPS = null;
		PreparedStatement amountSelectPS2 = null;
		PreparedStatement amountSelectPS3 = null;

		/* LC */
		try {
			amountSelectPS = conn.prepareStatement(SQLStatements.SELECT_LC_AMOUNT_STR1);
			amountSelectPS2 = conn.prepareStatement(SQLStatements.SELECT_LC_AMOUNT_STR2);
			amountSelectPS3 = conn.prepareStatement(SQLStatements.SELECT_LC_AMOUNT_STR3);
			
			selectPS = conn.prepareStatement(SQLStatements.LC_SELECT_SQL_STR);
			amountSelectPS.setTimestamp(1, timestmp);
			amountSelectPS.setTimestamp(2, timestmp);
			amountSelectPS2.setTimestamp(1, timestmp);
			amountSelectPS3.setTimestamp(1, timestmp);
			selectPS.setTimestamp(1, timestmp);
			
			ResultSet rs = amountSelectPS.executeQuery();
			Map amounts1 = createAmountMap(rs);
			rs.close();
			
			rs = amountSelectPS2.executeQuery();
			Map amounts2 = createAmount10PercentMap(rs);
			rs.close();
			
			rs = amountSelectPS3.executeQuery();
			Map amounts3 = createDCAmountMap(rs);
			rs.close();
			
			amounts1.putAll(amounts2);
			amounts1.putAll(amounts3);
			
			rs = selectPS.executeQuery();
			insertData(sc, rs, amounts1);
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		} finally {
			if (amountSelectPS != null)
				try { amountSelectPS.close(); } catch (SQLException sqle) { }
			
			if (amountSelectPS2 != null)	
				try { amountSelectPS2.close(); } catch (SQLException sqle) { }
			
			if (amountSelectPS3 != null)	
				try { amountSelectPS3.close(); } catch (SQLException sqle) { }
				
			if (selectPS != null) 	
				try { selectPS.close(); } catch (SQLException sqle) { }
		}

		selectPS = null;
		amountSelectPS = null;
						
		/* DC */
		try {
			amountSelectPS = conn.prepareStatement(SQLStatements.SELECT_DC_AMOUNT_STR);
			selectPS = conn.prepareStatement(SQLStatements.DC_SELECT_SQL_STR);
			amountSelectPS.setTimestamp(1, timestmp);
			selectPS.setTimestamp(1, timestmp);
			
			ResultSet rs = amountSelectPS.executeQuery();
			Map amounts = createAmountMap(rs);
			rs.close();
			
			rs = selectPS.executeQuery();
			insertData(sc, rs, amounts);
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		} finally {
			if (amountSelectPS != null)
				try { amountSelectPS.close(); } catch (SQLException sqle) { }
			
			if (selectPS != null) 	
				try { selectPS.close(); } catch (SQLException sqle) { }
		}
		
		
		selectPS = null;
		amountSelectPS = null;
		
		/* RC */
		try {
			amountSelectPS = conn.prepareStatement(SQLStatements.SELECT_RC_AMOUNT_STR);			
			selectPS = conn.prepareStatement(SQLStatements.RC_SELECT_SQL_STR);
			amountSelectPS.setTimestamp(1, timestmp);			
			selectPS.setTimestamp(1, timestmp);

			ResultSet rs = amountSelectPS.executeQuery();
			Map amounts = createDCAmountMap(rs);
			rs.close();
				
			rs = selectPS.executeQuery();			
			insertData(sc, rs, amounts);
			rs.close();
			
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			if (amountSelectPS != null)
				try { amountSelectPS.close(); } catch (SQLException sqle) { }
			
			if (selectPS != null) 	
				try { selectPS.close(); } catch (SQLException sqle) { }
		}

		selectPS = null;
		amountSelectPS = null;
				
		/* LOI */
		try {
			amountSelectPS = conn.prepareStatement(SQLStatements.SELECT_LOI_AMOUNT_STR);			
			selectPS = conn.prepareStatement(SQLStatements.LOI_SELECT_SQL_STR);
			amountSelectPS.setTimestamp(1, timestmp);			
			selectPS.setTimestamp(1, timestmp);
			
			
			ResultSet rs = amountSelectPS.executeQuery();
			Map amounts = createAmountMap(rs);
			rs.close();
			
			rs = selectPS.executeQuery();
			insertData(sc, rs, amounts);
			rs.close();
					
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			if (amountSelectPS != null)
				try { amountSelectPS.close(); } catch (SQLException sqle) { }
			
			if (selectPS != null) 	
				try { selectPS.close(); } catch (SQLException sqle) { }
		}
		
		AppProperties.putLongProperty("last.update", new java.util.Date().getTime());
		AppProperties.store();
			
	}

	
	void insertData(String sc, ResultSet rs, Map amounts) throws SQLException {
				
		while(rs.next()) {
			
			SS_BANK_REF ref = new SS_BANK_REF();
			ref.setSERVICECENTER(sc);
			ref.setDOSSIER_NUMBER(rs.getInt(1));
			ref.setBRANCH(rs.getString(2));
			ref.setBRANCH_GROUP(rs.getString(3));
									
			String refno = rs.getString(6);
			ref.setBANK_REF_NO(refno);
			ref.setBANK_REF_NO_S(StringUtils.createSearchString(refno));
			
			String name = rs.getString(4);
			ref.setBANK_NAME(name);
			ref.setBANK_NAME_S(StringUtils.createSearchString(name));
			ref.setBANK_LOCATION(rs.getString(5));			
			
			Object tmp = amounts.get(new Integer(ref.getDOSSIER_NUMBER()));
			if (tmp != null) {
				if (tmp instanceof Amount) {
					Amount atmp = (Amount)tmp;
					ref.setAMOUNT(atmp.getAmount());
					ref.setISO_CODE(atmp.getIsoCode());
					try {
						bankRefTable.insert(ref);
					} catch (SQLException sqle) {}
				} else {
					List l = (List)tmp;
					Iterator it = l.iterator();
					while(it.hasNext()) {
						Amount atmp = (Amount)it.next();						
						ref.setAMOUNT(atmp.getAmount());
						ref.setISO_CODE(atmp.getIsoCode());
						
						try {
							bankRefTable.insert(ref);
						} catch (SQLException sqle) {}						
					}
				}
				
			} else {
				ref.setAMOUNT(ZERO);
				ref.setISO_CODE("");
				try {
					bankRefTable.insert(ref);
				} catch (SQLException sqle) {}
			}
			
		}		
	}
	
	
	
	public Map createAmountMap(ResultSet rs) throws SQLException {
		Map amounts = new HashMap();
		
		while(rs.next()) {
			int dossierNo = rs.getInt(1);
			Amount amount = new Amount(rs.getString(2));			
			Integer dossierNoInteger = new Integer(dossierNo);
			
			Object tmp = amounts.get(dossierNoInteger);
			if (tmp != null) {
				if (tmp instanceof Amount) {
					Amount eamount = (Amount)tmp;
					List l = new ArrayList();
					l.add(eamount);
					l.add(amount);
					amounts.put(dossierNoInteger, l);
				} else {
					List l = (List)tmp;
					l.add(amount);
					//amounts.put(dossierNoInteger, l);
				}					
			} else {			
				amounts.put(dossierNoInteger, amount);
			}
		}
		return amounts;
	}

	public Map createAmount10PercentMap(ResultSet rs) throws SQLException {
		Map amounts = new HashMap();
		
		while(rs.next()) {
			int dossierNo = rs.getInt(1);
			Amount amount = new Amount(rs.getString(2));			
			Integer dossierNoInteger = new Integer(dossierNo);
			
			BigDecimal oldAmount = amount.getAmount();
			BigDecimal newAmount = oldAmount.multiply(TEN_PERCENT);
			amount.setAmount(newAmount);
			
			Object tmp = amounts.get(dossierNoInteger);
			if (tmp != null) {
				if (tmp instanceof Amount) {
					Amount eamount = (Amount)tmp;
					List l = new ArrayList();
					l.add(eamount);
					l.add(amount);
					amounts.put(dossierNoInteger, l);
				} else {
					List l = (List)tmp;
					l.add(amount);
					//amounts.put(dossierNoInteger, l);
				}					
			} else {			
				amounts.put(dossierNoInteger, amount);
			}
		}
		return amounts;
	}
	
	
	public Map createDCAmountMap(ResultSet rs) throws SQLException {
		Map amounts = new HashMap();
		
		while(rs.next()) {
			int dossierNo = rs.getInt(1);
			Amount amount = new Amount(rs.getString(2));
			int posPercent = rs.getInt(3);
			
			if (posPercent > 0) {
				
				double dval = amount.getAmount().doubleValue();
				dval += dval * posPercent / 100.0;
				amount.setAmount(new BigDecimal(dval));
			}
			
			
			Integer dossierNoInteger = new Integer(dossierNo);
			
			Object tmp = amounts.get(dossierNoInteger);
			if (tmp != null) {
				if (tmp instanceof Amount) {
					Amount eamount = (Amount)tmp;
					List l = new ArrayList();
					l.add(eamount);
					l.add(amount);
					amounts.put(dossierNoInteger, l);
				} else {
					List l = (List)tmp;
					l.add(amount);
					//amounts.put(dossierNoInteger, l);
				}					
			} else {			
				amounts.put(dossierNoInteger, amount);
			}
		}
		return amounts;
	}
	
	
	
	public static void main(String[] args) {
		if (args.length == 2) 
			new UpdateSSDb().update(args[0], Integer.parseInt(args[1]));
	}
}