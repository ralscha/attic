package swift;

public final class SWIFT_MT768 extends SWIFT_MT7 {

    public SWIFT_MT768() {
        super();        
        
        tags.put("20","Transaction Reference Number");
        tags.put("21","Related Reference");
        tags.put("30","Date of Message Being Acknowledged");
        tags.put("32B","Amount of Charges");
        tags.put("32D","Amount of Charges");
        tags.put("57A","Account With Bank");
        tags.put("57B","Account With Bank");
        tags.put("57D","Account With Bank");
        tags.put("71B","Details of Charges");

    }
    
    public String getMTName() {
        return("Acknowledgement of a Guarantee Message");
    }
        
}
