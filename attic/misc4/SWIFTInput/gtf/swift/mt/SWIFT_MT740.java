package gtf.swift.mt;

public final class SWIFT_MT740 extends SWIFT_MT7 {

	public SWIFT_MT740() {
		super();        
		tags.put("32B","Credit Amount");
		tags.put("71B","Other Charges");
	}
	public String getMTName() {
		return("Authorisation to Reimburse");
	}
}