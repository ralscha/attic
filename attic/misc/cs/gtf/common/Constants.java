package gtf.common;

public interface Constants {

	public static final boolean DEBUG = common.util.AppProperties.getBooleanProperty("debug", false);
	public static final boolean TEST = common.util.AppProperties.getBooleanProperty("test", false);
	
	
	public final static java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd.MM.yyyy");
	public final static java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HHmm");
	public final static java.text.SimpleDateFormat fileDateFormat = new java.text.SimpleDateFormat(".yyyyMMdd_HHmmssSSS");
	
	////////////
	//Properties
	////////////

	public final static String P_SMTP_HOST = "smtp.host";

	//ExchangeRate Check
	public final static String P_FX_CHECK_MAIL_SENDER = "exchangerate.check.mail.sender";
	public final static String P_FX_CHECK_MAIL_RECEIVER = "exchangerate.check.mail.receiver";
	
	//Polling Service
	public final static String P_POLLING_WAIT_SECONDS = "polling.wait.seconds";
	public final static String P_POLLING_TRANSFER_OK_FILE = "transfer.ok.file";
	
	//Servlets
	public final static String P_PROPS_FILE = "props.file";
	
	//LiabCheck
	public final static String P_LIAB_CHECK_TOTAL_KAS = "liab.total.accounttype";
	
	public final static String P_LIAB_CHECK_K477_RZ2_INPUT_FILE = "kas477.check.rz2.input.file";
	public final static String P_LIAB_CHECK_K477_RZ2_OUTPUT_FILE = "kas477.check.rz2.output.file";
	public final static String P_LIAB_CHECK_K477_RZ2_TITLE = "kas477.check.rz2.title";

	public final static String P_LIAB_CHECK_K477_RZ3_INPUT_FILE = "kas477.check.rz3.input.file";
	public final static String P_LIAB_CHECK_K477_RZ3_OUTPUT_FILE = "kas477.check.rz3.output.file";
	public final static String P_LIAB_CHECK_K477_RZ3_TITLE = "kas477.check.rz3.title";
	
	public final static String P_LIAB_CHECK_K407_RZ2_INPUT_FILE = "kas407.check.rz2.input.file";
	public final static String P_LIAB_CHECK_K407_RZ2_OUTPUT_FILE = "kas407.check.rz2.output.file";
	public final static String P_LIAB_CHECK_K407_RZ2_TITLE = "kas407.check.rz2.title";
	
	public final static String P_LIAB_CHECK_K407_RZ3_INPUT_FILE = "kas407.check.rz3.input.file";
	public final static String P_LIAB_CHECK_K407_RZ3_OUTPUT_FILE = "kas407.check.rz3.output.file";
	public final static String P_LIAB_CHECK_K407_RZ3_TITLE = "kas407.check.rz3.title";
	
	public final static String P_LIAB_CHECK_K406_RZ2_INPUT_FILE = "kas406.check.rz2.input.file";
	public final static String P_LIAB_CHECK_K406_RZ2_OUTPUT_FILE = "kas406.check.rz2.output.file";
	public final static String P_LIAB_CHECK_K406_RZ2_TITLE = "kas406.check.rz2.title";

	public final static String P_LIAB_CHECK_K406_RZ3_INPUT_FILE = "kas406.check.rz3.input.file";
	public final static String P_LIAB_CHECK_K406_RZ3_OUTPUT_FILE = "kas406.check.rz3.output.file";
	public final static String P_LIAB_CHECK_K406_RZ3_TITLE = "kas406.check.rz3.title";

	public final static String P_LIAB_CHECK_K405_RZ2_INPUT_FILE = "kas405.check.rz2.input.file";
	public final static String P_LIAB_CHECK_K405_RZ2_OUTPUT_FILE = "kas405.check.rz2.output.file";
	public final static String P_LIAB_CHECK_K405_RZ2_TITLE = "kas405.check.rz2.title";
		
	public final static String P_LIAB_CHECK_K405_RZ3_INPUT_FILE = "kas405.check.rz3.input.file";
	public final static String P_LIAB_CHECK_K405_RZ3_OUTPUT_FILE = "kas405.check.rz3.output.file";
	public final static String P_LIAB_CHECK_K405_RZ3_TITLE = "kas405.check.rz3.title";
		
	//AWZACheck
	public final static String P_AWZA_CHECK_OK_FILE = "awza.check.ok.file";
	public final static String P_AWZA_CHECK_BOOKINGS_FILE = "awza.check.bookings.file";
	public final static String P_AWZA_CHECK_OVERVIEW_URL = "awza.check.overview.url";
	
	public final static String P_AZWA_CHECK_BALANCE_FILE = "awza.check.balance.file";
	public final static String P_AWZA_CHECK_DIFF_HTML_PREFIX = "awza.check.diff.html.prefix";			
	public final static String P_AWZA_CHECK_DIFF_HTML_PATH = "awza.check.diff.html.path";
	public final static String P_AWZA_CHECK_OVERVIEW_HTMLFILE = "awza.check.overview.html.file";
	public final static String P_AWZA_CHECK_OK_DATE = "awza.check.ok.date";	
	public final static String P_AWZA_CHECK_SERVLET = "awza.check.servlet";
	
	//ServiceCenter
	public final static String P_SERVICE_CENTERS   = "service.centers";
	public final static String P_DB_DRIVER         = "db.driver";
	
	public final static String P_SC_NAME           = ".name";
	public final static String P_DB_URL            = ".db.url";
	public final static String P_DB_USER           = ".db.user";
	public final static String P_DB_PASSWORD       = ".db.password";
	
	//Reconciliation
	public final static String P_LU                 = "recon.lu";
	public final static String P_RECON_LOG_PATH     = "recon.log.path";
	public final static String P_CD_PATH            = "path.connectdirect";

	//Account
	public final static String P_ACCOUNT_DSN_TEST   = "dsn.account.test";
	public final static String P_ACCOUNT_DSN        = "dsn.account";
	public final static String P_ACCOUNT_IN_FILE    = ".infile.account";
	public final static String P_ACCOUNT_OUT_FILE   = ".outfile.account";
	public final static String P_ACCOUNT_UPDATE_CMD = ".update.batch";
	
	
	//Cif
	public final static String P_CIF_IN_FILE    = ".infile.cif";
	public final static String P_CIF_OUT_FILE   = ".outfile.cif";
	
	//Crapa
	public final static String P_CRAPA_FILE    = ".file.crapa";
	public final static String P_CRAPA_PATH    = ".path.crapa";

	//Crapa Statistic
	public final static String P_CRAPA_INPUT_FILE = "crapa.input.file";
	public final static String P_CRAPA_ARCHIVE_PATH = "crapa.archive.path";
	public final static String P_CRAPA_DB_DRIVER = "crapa.db.driver";
	public final static String 	P_CRAPA_DB_URL = "crapa.db.url";
	public final static String 	P_CRAPA_DB_USER = "crapa.db.user";
	public final static String 	P_CRAPA_DB_PASSWORD = "crapa.db.pw";
	
	//Currency
	public final static String P_CURRENCY_FILE = "file.currency";
	
	//ExchangeRate
	public final static String P_EX_BACKUP_PATH = "path.exchangerate.backup";
	public final static String P_EX_FILE = "file.exchangerate";
	
	//Liability
	public final static String P_LIAB_TRAN_FILE = "file.cd.liability";
	public final static String P_LIAB_FILE = ".file.liability";
	public final static String P_LIAB_PATH = ".path.liability";
	
	//Payment
	public final static String P_PAYMENT_FILE = ".file.payment";
	public final static String P_PAYMENT_PATH = ".path.payment";
	public final static String P_PAYMENT_TRAN_FILE = "file.cd.payment";
	
	//SWIFT/Telex
	public final static String P_SWIFTTELEX_FILE = ".st.file";
	public final static String P_SWIFTTELEX_PATH = ".st.path";
	public final static String P_SWIFTTELEX_TRAN_FILE = "file.cd.st";
	
	//TTF
	public final static String P_TTF_LOG_PATH = "ttf.log.path";
	public final static String P_TTF_COLLATERAL_FILE = ".collateral.file";
	public final static String P_TTF_EXPOSURE_FILE = ".exposure.file";
	public final static String P_TTF_TRADER_CIF = ".tradercif.file";
	public final static String P_TTF_TRADER_TRANSFER_BATCH = ".tradercif.transfer.batch";
	public final static String P_TTF_TRADER_HOST_FILE = ".tradercif.host.file";
	
	public final static String P_TTF_LOG_FILE = "ttf.log.file";
	public final static String P_TTF_LOG_CENTER = "ttf.log.center";

	public final static String P_TTF_LOG_MAIL_SENDER = "ttf.mail.sender";
	public final static String P_TTF_LOG_MAIL_RECEIVER = "ttf.mail.receiver";

	public final static String P_TTF_LOG_ERROR_STR = "error.string";
	public final static String P_TTF_LOG_OK_START_STR = "ok.start.string";
	public final static String P_TTF_LOG_OK_END_STR = "ok.end.string";
	
	//MBooker
	public final static String P_BU_CODES = "bu.code";
	
	//MP
	public final static String P_MP_LOG_PATH = "mp.log.path";
	public final static String P_MP_TRAN_FILE = "mp.tran.file";
	
}