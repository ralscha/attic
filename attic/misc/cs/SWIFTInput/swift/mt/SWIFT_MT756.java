package gtf.swift.mt;

public final class SWIFT_MT756 extends SWIFT_MT7 {

	public SWIFT_MT756() {
		super();      
		
		tags.put("20","Sender's TRN");
		tags.put("21","Presenting Bank's Reference");
		tags.put("32B","Total Amount Claimed");
		tags.put("33A","Amount Reimbursed or Paid");
		tags.put("53A","Sender's Correspondent");
		tags.put("53B","Sender's Correspondent");
		tags.put("53D","Sender's Correspondent");
		
	}
	public String getMTName() {
		return("Advice of Reimbursement or Payment");
	}
}