package ch.rasc.sqrl;

public class SqrlCommandNotSupportedException extends SqrlException {

	private static final long serialVersionUID = 1L;

	public SqrlCommandNotSupportedException(String errorMessage) {
		super(errorMessage);
	}

}
