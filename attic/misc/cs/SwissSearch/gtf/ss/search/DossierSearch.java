package gtf.ss.search;

import java.sql.*;
import java.util.*;
import java.math.*;
import common.util.*;
import gtf.ss.common.*;
import gtf.ss.util.StringUtils;

public class DossierSearch {

	private Connection conn;
	private SS_BANK_REFTable table;
	private int maxResult;
	private static final String orderClause = "BRANCH, BRANCH_GROUP, DOSSIER_NUMBER";
	
	public DossierSearch(Connection conn) {
		this.maxResult = AppProperties.getIntegerProperty("max.result", 1000);
		this.conn = conn;
		this.table = new SS_BANK_REFTable(conn);
	}
	
	public List searchWithAmount(String amount) throws SQLException {
		return searchWithAmount(amount, null);
	}
	
	public List searchWithAmount(String fromAmount, String toAmount) throws SQLException {
		List resultList = new ArrayList();
		try {
			Iterator it;
			BigDecimal amountBD = null;
			if (toAmount == null) {			
				amountBD = new BigDecimal(fromAmount);			
				it = table.selectAmount(amountBD);
			} else {
				BigDecimal amountFrom = new BigDecimal(fromAmount);
				BigDecimal amountTo   = new BigDecimal(toAmount);
				it = table.selectAmount(amountFrom, amountTo);
			}
			int count = 0;
			while(it.hasNext() && (count <= maxResult)) {			
				resultList.add((SS_BANK_REF)it.next());
				count++;
			}
			
			if (toAmount == null) {
				if (amountBD.doubleValue() != amountBD.intValue()) {			
					amountBD = new BigDecimal(amountBD.intValue());
					it = table.selectAmount(amountBD);
					while(it.hasNext() && (count <= maxResult)) {			
						resultList.add((SS_BANK_REF)it.next());
						count++;
					}
				}
			}
			return resultList;
			
		} catch (NumberFormatException nfe) {
			return resultList;
		}
	}
	
	
	public List searchWithRef(String ref) throws SQLException {
		String searchRef = StringUtils.createSearchString(ref.trim());
		List resultList = new ArrayList();
		
		Iterator it = table.select("BANK_REF_NO_S LIKE '%"+searchRef+"%'", orderClause );
		int count = 0;
		while(it.hasNext() && (count <= maxResult)) {			
			resultList.add((SS_BANK_REF)it.next());
			count++;
		}		
		return resultList;
	}
	
	public List searchWithName(String name) throws SQLException {
		String searchName = StringUtils.createSearchString(name.trim());
		List resultList = new ArrayList();
		
		Iterator it = table.select("BANK_NAME_S LIKE '%"+searchName+"%'", orderClause);
		int count = 0;
		while(it.hasNext() && (count <= maxResult)) {
			resultList.add((SS_BANK_REF)it.next());
			count++;
		}		
		return resultList;

	}

}