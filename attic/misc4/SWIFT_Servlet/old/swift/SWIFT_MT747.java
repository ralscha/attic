package swift;

public final class SWIFT_MT747 extends SWIFT_MT7 {

    public SWIFT_MT747() {
        super();      
        tags.put("21","Reimbursing Bank's Reference");
        tags.put("30","Date of the Original Authorisation to Reimburse");
        tags.put("32B","Increase of Documentary Credit Amount");
    }
    
    public String getMTName() {
        return("Amendment to an Authorisation to Reimburse");
    }
        
}
