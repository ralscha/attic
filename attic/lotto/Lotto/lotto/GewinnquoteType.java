package lotto;

public final class GewinnquoteType {
	
	public final static GewinnquoteType JACKPOT = new GewinnquoteType("GQ_JACKPOT");
	public final static GewinnquoteType RICHTIGE_6  = new GewinnquoteType("GQ_RICHTIGE_6");
	public final static GewinnquoteType RICHTIGE_5_PLUS = new GewinnquoteType("GQ_RICHTIGE_5_PLUS");
	public final static GewinnquoteType RICHTIGE_5 = new GewinnquoteType("GQ_RICHTIGE_5");
	public final static GewinnquoteType RICHTIGE_4 = new GewinnquoteType("GQ_RICHTIGE_4");
	public final static GewinnquoteType RICHTIGE_3 = new GewinnquoteType("GQ_RICHTIGE_3");
	public final static GewinnquoteType RICHTIGE_2 = new GewinnquoteType("GQ_RICHTIGE_2");

	private GewinnquoteType(String type) {
		this.type = type;
	}	
	
	public boolean equals(Object obj) {
		try {
			return (type.equals(((GewinnquoteType)obj).type));
		} catch (ClassCastException e) { 
			return false;
		}
	}

	private String type;	
}