package gtf.swift.mt;

public final class SWIFT_MT711 extends SWIFT_MT7 {

	public SWIFT_MT711() {
		super();   
		tags.put("20", "Sender's Reference");
		tags.put("21", "Documentary Credit Number");
	}
	public String getMTName() {
		return("Advice of a Third Bank’s Documentary Credit");
	}
}