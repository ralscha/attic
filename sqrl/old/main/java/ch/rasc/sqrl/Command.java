package ch.rasc.sqrl;

public enum Command {

	QUERY, IDENT, ENABlE, DISABlE, REMOVE;

	public static Command fromExternalValue(String externalValue) {
		switch (externalValue) {
		case "query":
			return Command.QUERY;
		case "ident":
			return Command.IDENT;
		case "enable":
			return Command.ENABlE;
		case "disable":
			return Command.DISABlE;
		case "remove":
			return Command.REMOVE;
		default:
			return null;
		}
	}

}
