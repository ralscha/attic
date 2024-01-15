package swift;

public final class SWIFT_MT791 extends SWIFT_MT7 {

    public SWIFT_MT791() {
        super();   
        tags.put("20", "Transaction Reference Number");
        tags.put("21", "Related Reference");
        tags.put("32B", "Currency Code, Amount");
        tags.put("52A", "Ordering Institution");
        tags.put("52D", "Ordering Institution");
        tags.put("57A", "Account With Institution");
        tags.put("57B", "Account With Institution");
        tags.put("57D", "Account With Institution");
        tags.put("71B", "Details of Charges");
    }
    
    public String getMTName() {
        return("Request for Payment of Charges, Interest and other Expenses");
    }
        
}
