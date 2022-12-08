package ch.rasc.sqrl;

public enum SqrlTransactionOption {
	NO_IP_TEST, SQRL_ONLY, HARDLOCK, CPS, SUK;

	public static SqrlTransactionOption fromExternalValue(String value) {
		switch (value) {
		case "noiptest":
			return NO_IP_TEST;
		case "sqrlonly":
			return SQRL_ONLY;
		case "hardlock":
			return HARDLOCK;
		case "cps":
			return CPS;
		case "suk":
			return SUK;
		default:
			return null;
		}
	}
}
