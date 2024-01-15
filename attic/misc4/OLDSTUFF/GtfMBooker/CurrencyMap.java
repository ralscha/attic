
import java.util.*;
import java.sql.*;
import java.io.*;

import common.util.*;

public final class CurrencyMap {
	
	private final static String selectSQL = "SELECT CS_CURR_NUMBER, ISO_CODE_ALPHA, INTERNAL_RATE FROM GTFLC.CURRENCY ORDER BY ISO_CODE_ALPHA";
	private static CurrencyMap instance;
	
	private Map currencyMap;
	
	static {
		try {
			instance = new CurrencyMap();
		} catch(Exception e) {
			System.err.println(e);
		}
	}
	
	private CurrencyMap() throws ClassNotFoundException, SQLException  {
		currencyMap = new HashMap();
		
		
		if (System.getProperty("FILE") != null) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(AppProperties.getStringProperty("currency.file")));
				String line;
				while ((line = br.readLine()) != null) {
					try {
						StringTokenizer st = new StringTokenizer(line, ",");
					
						Currency curr = new Currency(Integer.parseInt(st.nextToken()), st.nextToken(), new java.math.BigDecimal(st.nextToken()));	
						currencyMap.put(curr.getISOCode(), curr);
					} catch (NumberFormatException nfe) {
						System.err.println(nfe);
					}
				}
				br.close();
			} catch (Exception e) {
				System.err.println(e);
			}
			
		} else {
			Connection conn = connect();
			Statement selectStmt = conn.createStatement();
			ResultSet rs = selectStmt.executeQuery(selectSQL);
			while(rs.next())  {
				Currency curr = new Currency(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3));
				currencyMap.put(curr.getISOCode(), curr);
			}
			rs.close();
			selectStmt.close();
			conn.close();
		}
	}
	
	public static String[] getISOCodes() {
		if (!instance.currencyMap.isEmpty()) {
			String[] ret = (String[])instance.currencyMap.keySet().toArray(new String[instance.currencyMap.size()]);		
			Arrays.sort(ret);
			return ret;
		} else
			return null;
	}
	
	public static Currency getCurrency(String iso) {
		return (Currency)instance.currencyMap.get(iso);
	}
	
	private Connection connect() throws ClassNotFoundException, SQLException {
		String dbDriver = AppProperties.getStringProperty("db.driver").trim();
		Class.forName(dbDriver);
			
		String user = AppProperties.getStringProperty("db.user").trim();
		if (user == null) return null;
		
		String pw = AppProperties.getStringProperty("db.password").trim();
		String dbURL = AppProperties.getStringProperty("db.url").trim();		
		
		return(DriverManager.getConnection(dbURL, user, pw));
	}


}