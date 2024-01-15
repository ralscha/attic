package gtf.ttf;

import java.sql.*;

public class TtfUtil {

	private TtfUtil() {}

	public static java.math.BigDecimal makeBigDecimal(String amount, String sign) {
		StringBuffer sb = new StringBuffer();

		if ("-".equals(sign)) {
			sb.append(sign);
		} 

		for (int i = 0; i < amount.length(); i++) {
			char tmp = amount.charAt(i);

			if (tmp != ',') {
				sb.append(tmp);
			} 
		} 

		return (new java.math.BigDecimal(sb.toString()));
	} 

}