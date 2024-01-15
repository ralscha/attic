package swift;

public final class SWIFT_MT769 extends SWIFT_MT7 {

    public SWIFT_MT769() {
        super();        
        
        tags.put("20","Transaction Reference Number");
        tags.put("21","Related Reference");
        tags.put("30","Date of Reduction or Release");
        tags.put("32B","Amount of Charges");
        tags.put("34B","Amount Outstanding");
        tags.put("39C","Amount Specification");
        tags.put("57A","Account With Bank");
        tags.put("57B","Account With Bank");
        tags.put("57D","Account With Bank");
        tags.put("71B","Details of Charges");
        
    }
    
    public String getMTName() {
        return("Advice of Reduction or Release");
    }
        
}
