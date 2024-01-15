package gtf.swift.mt;

public final class SWIFT_MT752 extends SWIFT_MT7 {

	public SWIFT_MT752() {
		super();   
		
		tags.put("21","Presenting Bank's Reference");
		tags.put("23","Further Identification");
		tags.put("30","Date of Advice of Discrepancy or Mailing");
		tags.put("32B","Total Amound Advised");
		tags.put("33A","Net Amount");
		tags.put("33B","Net Amount");
		tags.put("53A","Sender's Correspondent");
		tags.put("53D","Sender's Correspondent");
		tags.put("71B","Charges Deducted");
	}
	public String getMTName() {
		return("Authorisation to Pay, Accept or Negotiate");
	}
}