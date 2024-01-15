package swift;

public final class SWIFT_MT790 extends SWIFT_MT7 {

    public SWIFT_MT790() {
        super();   
        tags.put("20", "Transaction Reference Number");
        tags.put("21", "Related Reference");
        tags.put("32C", "Value Date, Currency Code, Amount");
        tags.put("32D", "Value Date, Currency Code, Amount");
        tags.put("52A", "Ordering Institution");
        tags.put("52D", "Ordering Institution");
        tags.put("71B", "Details of Charges");
    }
    
    public String getMTName() {
        return("Advice of Charges, Interest and Other Adjustments");
    }
        
}
