package gtf.ss.common;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.math.*;

import gtf.common.*;
import common.util.*;

public class Currency {
		
	private final static BigDecimal ZERO = new BigDecimal(0);
	
	private Map currencyMap;
	private Map isoMap;
	
	public Currency() {
		try {
			ServiceCenter zrhS = new ServiceCenter("ZRH");
			
			currencyMap = new HashMap();
			isoMap = new HashMap();
			
			Connection conn = zrhS.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQLStatements.SELECT_DIGITS_AFTER);
			
			while(rs.next()) {
				String isoAlpha = rs.getString(1);
				int iso = rs.getInt(2);
				int digits = rs.getInt(3);
				isoMap.put(String.valueOf(iso), isoAlpha);
				currencyMap.put(String.valueOf(iso), new Integer(digits));
			}
			
			rs.close();
			stmt.close();
			zrhS.closeConnection();
		} catch (Exception sqle) {
			System.err.println(sqle);
		}
		
	}
	
	public String getIsoCodeAlpha(String amountStr) {
			int pos = amountStr.indexOf(".");
			
			if (pos == -1) 
				return("   ");
			
			String isoCodeNum = amountStr.substring(pos+1);
			String isoCodeAlpha = (String)isoMap.get(isoCodeNum);
			if (isoCodeAlpha != null) {
				return isoCodeAlpha;
			} else {
				return isoCodeNum;
			}
	}
	
	public BigDecimal getDecimal(String amountStr) {
				
			int pos = amountStr.indexOf(".");
			
			if (pos == 0) {
				System.out.println(amountStr);
				amountStr = "000" + amountStr;
			} else if (pos == -1) {
				System.out.println(amountStr);
				return ZERO;
			}
			
			String isoCodeNum = amountStr.substring(pos+1);
			String amount = amountStr.substring(0, pos);
								
			Integer digitsafter = (Integer)currencyMap.get(isoCodeNum);
			
			String r1, r2;
			
			try {
			
				if (digitsafter != null) {
					int digits = digitsafter.intValue();
					switch(digits) {
						case 0 : return new BigDecimal(amount); 
						case 2 :
							r1 = amount.substring(0, amount.length()-2);
							r2 = amount.substring(amount.length()-2);
							return new BigDecimal(r1 + "." + r2);
						case 3 : 
							r1 = amount.substring(0, amount.length()-3);
							r2 = amount.substring(amount.length()-3);
							return new BigDecimal(r1 + "." + r2);
						default : return new BigDecimal(amount); 
					}
					
				} else {
					return new BigDecimal(amount);
				}
			} catch (StringIndexOutOfBoundsException e) {
				return ZERO;
			}
		
	}
	
	
}