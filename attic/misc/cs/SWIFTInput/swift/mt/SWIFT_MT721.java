package gtf.swift.mt;

public final class SWIFT_MT721 extends SWIFT_MT7 {

	public SWIFT_MT721() {
		super();       
		tags.put("20","Transferring Bank's Reference");
		tags.put("21","Documentary Credit Number");
	}
	public String getMTName() {
		return("Transfer of a Documentary Credit");
	}
}