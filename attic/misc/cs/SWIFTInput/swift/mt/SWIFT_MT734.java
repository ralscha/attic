package gtf.swift.mt;

public final class SWIFT_MT734 extends SWIFT_MT7 {

	public SWIFT_MT734() {
		super();     
		
		tags.put("20","Sender's TRN");
		tags.put("21","Presenting Bank's Reference");
		tags.put("33B","Total Amount Claimed");
		tags.put("57A","Account With Bank");
		tags.put("57B","Account With Bank");
		tags.put("57D","Account With Bank");
	}
	public String getMTName() {
		return("Advice of Refusal");
	}
}