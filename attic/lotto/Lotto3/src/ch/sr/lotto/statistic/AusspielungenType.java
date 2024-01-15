package ch.sr.lotto.statistic;

public final class AusspielungenType {
	
	public final static AusspielungenType ALLE = new AusspielungenType("AT_ALLE");
	public final static AusspielungenType AUS42 = new AusspielungenType("AT_AUS42");
	public final static AusspielungenType AUS45 = new AusspielungenType("AT_AUS45");
	public final static AusspielungenType AB1997 = new AusspielungenType("AT_AB1997");
	public final static AusspielungenType CURRENT_YEAR = new AusspielungenType("AT_CURRENT_YEAR");
		
	private AusspielungenType(String type) {
		this.type = type;
	}		
	
	public static AusspielungenType[] getTypesAsArray() {
		AusspielungenType[] types = new AusspielungenType[5];
		types[0] = ALLE;
		types[1] = AUS42;
		types[2] = AUS45;
		types[3] = AB1997;
		types[4] = CURRENT_YEAR;
		return types;
	}
	
	public boolean equals(Object obj) {
		try {
			return (type.equals(((AusspielungenType)obj).type));
		} catch (ClassCastException e) { 
			return false;
		}
	}

	public String getExternal() {
		return type;
	}

	private String type;	

}