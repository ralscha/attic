package gtf.swift.mt;

public final class SWIFT_MT720 extends SWIFT_MT7 {

	public SWIFT_MT720() {
		super();    
		tags.put("20","Transferring Bank's Reference");
		tags.put("21","Documentary Credit Number");
		tags.put("50","First Beneficiary");
		tags.put("52A","Issuing Bank of the Original D/C");
		tags.put("52D","Issuing Bank of the Original D/C");
		tags.put("59","Second Beneficiary");
	}
	public String getMTName() {
		return("Transfer of a Documentary Credit");
	}
}