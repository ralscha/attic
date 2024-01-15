package gtf.swift.mt;

public final class SWIFT_MT799 extends SWIFT_MT7 {

	public SWIFT_MT799() {
		super();   
		tags.put("20", "Transaction Reference Number");
		tags.put("21", "Related Reference");
	}
	public String getMTName() {
		return("Free Format Message");
	}
}