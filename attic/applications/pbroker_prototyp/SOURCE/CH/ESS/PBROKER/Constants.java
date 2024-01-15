
package ch.ess.pbroker;

import java.text.*;

public class Constants {
	
	public static final String ERROR_KEY = "pbroker.error";
	public static final String BASKET_KEY = "pbroker.basket";
	public static final String OFFERTANFRAGE_KEY = "pbroker.offertanfrage";
  public static final String KANDIDATEN_KEY = "pbroker.kandidaten";

	public static final String REKRUTIERUNGS_KEY = "pbroker.rekrutierungs";
  public static final String REQUEST_URI_KEY = "pbroker.requesturi";
  public static final String SEARCH_CRITERION_KEY = "pbroker.searchcriterion";
  public static final String NOTFOUND_KEY = "pbroker.notfound";

  public static final String REKRUTIERUNG_KEY = "rekrutierung";

	public static final String USER_KEY = "pbroker.user";

	public final static DecimalFormat decimalFormat = new DecimalFormat("#0.00");
  public final static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
  public final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
  public final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
  public final static SimpleDateFormat odateFormat = new SimpleDateFormat("dd.MM.yyyy");

}