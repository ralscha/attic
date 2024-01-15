package gtf.recon;

import gtf.db.CURRENCY;
import java.text.*;

public class CurrencyFactory {
	private final static COM.stevesoft.pat.Regex currRx = new COM.stevesoft.pat.Regex("^(\\d{3}),\"([^\"]*)\",\"([^\"]*)\",([^,]+),(\\d),(\\d),(\\d),[+]*([-\\d.]+),([\\d]{8}),[+]*([-\\d.]+),[+]*([-\\d.]+),[+]*([-\\d.]+),[+]*([-\\d.]+),[+]*([-\\d.]+)");
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public static CURRENCY createObject(String row) {

		currRx.search(row);
		if (currRx.didMatch()) {

			CURRENCY curr = new CURRENCY();

			curr.setCS_CURR_NUMBER(Short.parseShort(currRx.stringMatched(1)));
			curr.setCS_CURR_NAME(currRx.stringMatched(2));
			curr.setISO_CODE_ALPHA(currRx.stringMatched(3));
			curr.setROUND_POSITION(Short.parseShort(currRx.stringMatched(4)));
			curr.setDIGITS_AFTER(Short.parseShort(currRx.stringMatched(5)));
			curr.setROUND_TYPE(Short.parseShort(currRx.stringMatched(6)));
			curr.setCURRENCY_UNIT(Short.parseShort(currRx.stringMatched(7)));
			curr.setCURRENCY_TOLERANCE(new java.math.BigDecimal(currRx.stringMatched(8)));

			ParsePosition pos = new ParsePosition(0);
			java.util.Date validDate = dateFormat.parse(currRx.stringMatched(9), pos);
			curr.setVALID_FROM(new java.sql.Date(validDate.getTime()));

			curr.setINTERNAL_RATE(new java.math.BigDecimal(currRx.stringMatched(10)));
			curr.setINTERNAL_RATE_OLD(new java.math.BigDecimal(currRx.stringMatched(11)));
			curr.setTABLEAU_TIME(Short.parseShort(currRx.stringMatched(12)));
			curr.setPOSITION_LIMIT(new java.math.BigDecimal(currRx.stringMatched(13)));
			curr.setSTAND_LIMIT(new java.math.BigDecimal(currRx.stringMatched(14)));

			return curr;
		} else {
			return null;
		}
	}
}