package ch.sr.lotto.statistic;

public final class VerbundType {
	public final static VerbundType NICHTS = new VerbundType("V_NICHTS");
	public final static VerbundType ZWILLING = new VerbundType("V_ZWILLING");
	public final static VerbundType DRILLING = new VerbundType("V_DRILLING");
	public final static VerbundType VIERLING = new VerbundType("V_VIERLING");
	public final static VerbundType FUENFLING = new VerbundType("V_FUENFLING");
	public final static VerbundType SECHSLING = new VerbundType("V_SECHSLING");
	public final static VerbundType DOPPELZWILLING = new VerbundType("V_DOPPELZWILLING");
	public final static VerbundType DOPPELDRILLING = new VerbundType("V_DOPPELDRILLING");
	public final static VerbundType DRILLINGZWILLING = new VerbundType("V_DRILLINGZWILLING");
	public final static VerbundType DREIFACHZWILLING = new VerbundType("V_DREIFACHZWILLING");
	public final static VerbundType VIERLINGZWILLING = new VerbundType("V_VIERLINGZWILLING");
		
	private VerbundType(String type) {
		this.type = type;
	}		
	
	public static VerbundType[] getTypesAsArray() {
		VerbundType[] types = new VerbundType[11];
		types[ 0] = NICHTS;
		types[ 1] = ZWILLING;
		types[ 2] = DRILLING;
		types[ 3] = VIERLING;
		types[ 4] = FUENFLING;
		types[ 5] = SECHSLING;
		types[ 6] = DOPPELZWILLING;
		types[ 7] = DOPPELDRILLING;
		types[ 8] = DRILLINGZWILLING;
		types[ 9] = DREIFACHZWILLING;
		types[10] = VIERLINGZWILLING;
		
		return types;
	}
	
	public boolean equals(Object obj) {
		try {
			return (type.equals(((VerbundType)obj).type));
		} catch (ClassCastException e) { 
			return false;
		}
	}

	public String getExternal() {
		return type;
	}
	
	private String type;	

}