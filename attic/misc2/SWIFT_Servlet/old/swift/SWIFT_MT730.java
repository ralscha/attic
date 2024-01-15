package swift;

public final class SWIFT_MT730 extends SWIFT_MT7 {

    public SWIFT_MT730() {
        super();        
        
        tags.put("20","Sender's Reference");
        tags.put("30","Date of Message Being Acknowledged");
        tags.put("32B","Amount of Charges");
        tags.put("57A","Account With Bank");
        tags.put("57D","Account With Bank");
    }
    
    public String getMTName() {
        return("Acknowledgement");
    }
        
}
