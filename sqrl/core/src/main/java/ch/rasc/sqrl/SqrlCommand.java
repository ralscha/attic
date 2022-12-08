package ch.rasc.sqrl;

public enum SqrlCommand {
	QUERY, IDENT, DISABLE, ENABLE, REMOVE;

	public static SqrlCommand fromExternalValue(String value) {
		switch (value) {
		case "query":
			return QUERY;
		case "ident":
			return IDENT;
		case "disable":
			return DISABLE;
		case "enable":
			return ENABLE;
		case "remove":
			return REMOVE;
		default:
			return null;
		}
	}

}
