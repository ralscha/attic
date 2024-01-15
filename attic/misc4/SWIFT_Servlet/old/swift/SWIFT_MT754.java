package swift;

public final class SWIFT_MT754 extends SWIFT_MT7 {

    public SWIFT_MT754() {
        super();        
        tags.put("20","Sender's Reference");
        tags.put("21","Related Reference");
        tags.put("32A","Principal Amount Paid/Accepted/Negotiated");
        tags.put("32B","Principal Amount Paid/Accepted/Negotiated");
        tags.put("33B","Additional Amounts");
        tags.put("34B","Total Amount Claimed");
        tags.put("53A","Reimbursing Bank");
        tags.put("53B","Reimbursing Bank");
        tags.put("53D","Reimbursing Bank");
        tags.put("57A","Account With Bank");
        tags.put("57B","Account With Bank");
        tags.put("57D","Account With Bank");
        tags.put("58A","Beneficiary Bank");
        tags.put("58D","Beneficiary Bank");
        tags.put("71B","Charges Deducted");
        tags.put("73","Chareges Added");  
    }
    
    public String getMTName() {
        return("Advice of Payment / Acceptance / Negotiation");
    }
        
}
