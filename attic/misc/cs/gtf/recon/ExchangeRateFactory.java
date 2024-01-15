package gtf.recon;

import gtf.db.EXCHANGE_RATE;
import java.text.*;

public class ExchangeRateFactory {
	private final static COM.stevesoft.pat.Regex fxRx = new COM.stevesoft.pat.Regex("^\"([A-Z]{3})\",([\\d]{8}),\"([^\"]*)\",[+]*([-\\d.]+),[+]*([-\\d.]+),[+]*([-\\d.]+),[+]*([-\\d.]+),[+]*([-\\d.]+)");
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public static EXCHANGE_RATE createObject(String row) {

		fxRx.search(row);
		if (fxRx.didMatch()) {

			EXCHANGE_RATE fx = new EXCHANGE_RATE();

			fx.setISO_CODE_ALPHA(fxRx.stringMatched(1));

			ParsePosition pos = new ParsePosition(0);
			java.util.Date fixingDate = dateFormat.parse(fxRx.stringMatched(2), pos);
			fx.setRATE_FIXING_DATE(new java.sql.Date(fixingDate.getTime()));

			fx.setRATE_FIXING_NUMBER(fxRx.stringMatched(3));
			fx.setBUY_RATE_A(new java.math.BigDecimal(fxRx.stringMatched(4)));
			fx.setBUY_RATE_B(new java.math.BigDecimal(fxRx.stringMatched(5)));
			fx.setMIDDLE_RATE(new java.math.BigDecimal(fxRx.stringMatched(6)));
			fx.setSELL_RATE_B(new java.math.BigDecimal(fxRx.stringMatched(7)));
			fx.setSELL_RATE_A(new java.math.BigDecimal(fxRx.stringMatched(8)));

			return fx;
		} else {
			return null;
		}


	}
}