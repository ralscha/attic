package swift;

import java.util.*;
import COM.odi.util.*;

abstract public class SWIFT_MT7 {
    protected Map tags;
    private static Map mt;
    
    static {
        mt = new OSHashMap();
        mt.put("700", new SWIFT_MT700());
        mt.put("701", new SWIFT_MT701());
        mt.put("705", new SWIFT_MT705());        
        mt.put("707", new SWIFT_MT707());
        mt.put("710", new SWIFT_MT710());
        mt.put("711", new SWIFT_MT711());        
        mt.put("720", new SWIFT_MT720());
        mt.put("721", new SWIFT_MT721());
        mt.put("730", new SWIFT_MT730());        
        mt.put("732", new SWIFT_MT732());
        mt.put("734", new SWIFT_MT734());
        mt.put("740", new SWIFT_MT740());        
        mt.put("742", new SWIFT_MT742());
        mt.put("747", new SWIFT_MT747());
        mt.put("750", new SWIFT_MT750());        
        mt.put("752", new SWIFT_MT752());
        mt.put("754", new SWIFT_MT754());
        mt.put("756", new SWIFT_MT756());        
        mt.put("760", new SWIFT_MT760());                
        mt.put("767", new SWIFT_MT767());                
        mt.put("768", new SWIFT_MT768());                
        mt.put("769", new SWIFT_MT769());      
        mt.put("790", new SWIFT_MT790());
        mt.put("791", new SWIFT_MT791());
        mt.put("792", new SWIFT_MT792());
        mt.put("795", new SWIFT_MT795());
        mt.put("796", new SWIFT_MT796());
        mt.put("798", new SWIFT_MT798());
        mt.put("799", new SWIFT_MT799());
    }
    
    public static SWIFT_MT7 getSWIFTMT(String m) {
        return (SWIFT_MT7)mt.get(m);
    }
    
    protected SWIFT_MT7() {
        tags = new OSHashMap();
        tags.put("20","Documentary Credit Number");
        tags.put("21","Receiver's Reference");
        tags.put("23","Reference to Pre-Advice");
        tags.put("25","Account Identification");
        tags.put("26E","Number of Amendment");
        tags.put("27","Sequence of Total");
        tags.put("30","Date of Amendment");
        tags.put("31C","Date of Issue");
        tags.put("31D","Date and Place of Expiry");
        tags.put("31E","New date of Expiry");
        tags.put("32A","Date and Amount of Utilisation");
        tags.put("32B","Currency Code, Amount");
        tags.put("32D","Amount of Charges");
        tags.put("33A","Total Amount Claimed");
        tags.put("33B","Decrease of Documentary Credit Amount");
        tags.put("34A","Total Amount Claimed");
        tags.put("34B","New Documentary Credit Amount After Amendment");
        tags.put("39A","Percentage Credit Amount Tolerance");
        tags.put("39B","Maximum Credit Amount");
        tags.put("39C","Additional Amounts Covered");
        tags.put("40A","Form of Documentary Credit"); 
        tags.put("40B","Form of Documentary Credit"); 
        tags.put("41A","Available With ... By ...");
        tags.put("41D","Available With ... By ...");
        tags.put("42A","Drawee");
        tags.put("42C","Drafts at ...");
        tags.put("42D","Drawee");
        tags.put("42M","Mixed Payment Details");
        tags.put("42P","Deferred Payment Details");
        tags.put("43P","Partial Shipments");
        tags.put("43T","Transshipment");
        tags.put("44A","Loading on Board/Dispatch/Taking in Charge at/from ...");
        tags.put("44B","For Transportation to ...");
        tags.put("44C","Latest Date of Shipment");
        tags.put("44D","Shipment Period");
        tags.put("45A","Description of Goods and/or Services");
        tags.put("45B","Description of Goods and/or Services");
        tags.put("46A","Documents Required");
        tags.put("46B","Documents Required");
        tags.put("47A","Additional Conditions");
        tags.put("47B","Additional Conditions");
        tags.put("48","Period for Presentation");
        tags.put("49","Confirmation Instructions");
        tags.put("50","Applicant");
        tags.put("51A","Applicant Bank");
        tags.put("51D","Applicant Bank");
        tags.put("52A","Issuing Bank");
        tags.put("52D","Issuing Bank");
        tags.put("53A","Reimbursing Bank");
        tags.put("53B","Sender's Correspondent");
        tags.put("53D","Reimbursing Bank");
        tags.put("54A","Receiver's Correspondent");
        tags.put("54B","Receiver's Correspondent");
        tags.put("54D","Receiver's Correspondent");
        tags.put("57A","Advise Through Bank");
        tags.put("57B","Advise Through Bank");
        tags.put("57D","Advise Through Bank");
        tags.put("58A","Negotiating Bank");
        tags.put("58D","Negotiating Bank");
        tags.put("59","Beneficiary");
        tags.put("71A","Reimbursing Bank's Charges");
        tags.put("71B","Charges");
        tags.put("72","Sender to Receiver Information");
        tags.put("73","Charges Claimed");
        tags.put("77A","Narrative");
        tags.put("77C","Details of Guarantee");
        tags.put("77J","Discrepancies");
        tags.put("77B","Disposal of Documents");
        tags.put("78","Instructions to the Paying/Accepting/Negotiating Bank");
        tags.put("79","Narrative");
    }
        
    public String getTagDescription(String tag) {
        return (String)tags.get(tag);        
    }
    
    public abstract String getMTName();
}
