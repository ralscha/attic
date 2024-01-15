package swift;

public final class SWIFT_MT760 extends SWIFT_MT7 {

    public SWIFT_MT760() {
        super();        
        
        tags.put("20","Transaction Reference Number");
        tags.put("23","Further Identification");
        tags.put("30","Date");
    }
    
    public String getMTName() {
        return("Guarantee");
    }
        
}
