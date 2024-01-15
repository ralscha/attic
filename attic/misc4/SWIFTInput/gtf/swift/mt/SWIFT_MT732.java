package gtf.swift.mt;

public final class SWIFT_MT732 extends SWIFT_MT7 {

	public SWIFT_MT732() {
		super();   
		
		tags.put("20","Sender's TRN");
		tags.put("21","Presenting Bank's Reference");
		tags.put("30","Date of Advice of Payment/Acceptance/Negotiation");
		tags.put("32B","Amount of Utilisation");
	}
	public String getMTName() {
		return("Advice of Discharge");
	}
}