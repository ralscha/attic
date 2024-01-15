
package gtf.ttf;

import java.sql.*;
import common.util.AppProperties11;

public class TtfUtil {
   
	private TtfUtil() { }
	
	public static Connection connect(String serviceCenter) throws SQLException, ClassNotFoundException {	
		String user = AppProperties11.getStringProperty(serviceCenter+".db.user").trim();
		String pw = AppProperties11.getStringProperty(serviceCenter+".db.password").trim();
		String dbURL = AppProperties11.getStringProperty(serviceCenter+".db.url").trim();
		String dbDriver = AppProperties11.getStringProperty(serviceCenter+".db.driver").trim();
		
		Class.forName(dbDriver);
		return(DriverManager.getConnection(dbURL, user, pw));
	}

   
	public static java.math.BigDecimal makeBigDecimal(String amount, String sign) {
		StringBuffer sb = new StringBuffer();
	
		if ("-".equals(sign))
			sb.append(sign);
	
		for (int i = 0; i < amount.length(); i++) {
			char tmp = amount.charAt(i);
			if (tmp != ',') {
				sb.append(tmp);
			}			
		}			
		return (new java.math.BigDecimal(sb.toString()));					
	}
	
}