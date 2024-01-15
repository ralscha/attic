package gtf.swift.mt;

public final class SWIFT_MT792 extends SWIFT_MT7 {

	public SWIFT_MT792() {
		super();
		tags.put("20", "Transaction Reference Number");
		tags.put("21", "Related Reference");
		tags.put("11S", "MT and Date of Original Message");
		tags.put("79", "Narrative description of the original message");
	}
	public String getMTName() {
		return("Request of Cancellation");
	}
}