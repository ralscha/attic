package gtf.swift.mt;

public final class SWIFT_MT750 extends SWIFT_MT7 {

	public SWIFT_MT750() {
		super();    
		
		tags.put("20","Presenting Bank's Reference");
		tags.put("21","Related Reference");
		tags.put("32B","Principal Amount");
		tags.put("33B","Additional Amount");
		tags.put("34B","Total Amount to be Paid");
		tags.put("57A","Account With Bank");
		tags.put("57B","Account With Bank");
		tags.put("57D","Account With Bank");
		tags.put("71B","Charges to be Deducted");
		tags.put("73","Charges to be Added");
		tags.put("77A","Discrepancies");
	}
	public String getMTName() {
		return("Advice of Discrepancy");
	}
}