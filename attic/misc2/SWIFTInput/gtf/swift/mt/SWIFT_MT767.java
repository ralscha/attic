package gtf.swift.mt;

public final class SWIFT_MT767 extends SWIFT_MT7 {

	public SWIFT_MT767() {
		super(); 
		tags.put("20","Transaction Reference Number");
		tags.put("21","Related Reference");
		tags.put("23","Further Identification");
		tags.put("30","Date");
		tags.put("31C","Date of Issue or Request to Issue");
	}
	public String getMTName() {
		return("Guarantee Amendment");
	}
}