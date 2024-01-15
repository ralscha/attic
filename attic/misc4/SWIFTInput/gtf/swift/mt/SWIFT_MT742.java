package gtf.swift.mt;

public final class SWIFT_MT742 extends SWIFT_MT7 {

	public SWIFT_MT742() {
		super();     
		tags.put("20","Claiming Bank's Reference");
		tags.put("21","Documentary Credit Number");
		tags.put("32B","Principal Amount Claimed");
		tags.put("33B","Additional Amount Claimed as Allowed for in Excess of Principal Amount");
		tags.put("34B","Total Amount Claimed");
		tags.put("57A","Account With Bank");
		tags.put("57B","Account With Bank");
		tags.put("57D","Account With Bank");
		tags.put("58A","Beneficiary Bank");
		tags.put("58D","Beneficiary Bank");
	}
	public String getMTName() {
		return("Reimbursement Claim");
	}
}